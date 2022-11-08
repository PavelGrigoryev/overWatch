package by.grigoryev.overwatch.service.impl;

import by.grigoryev.overwatch.dto.UserDto;
import by.grigoryev.overwatch.mapper.UserMapper;
import by.grigoryev.overwatch.model.User;
import by.grigoryev.overwatch.repository.CoinRepository;
import by.grigoryev.overwatch.repository.UserRepository;
import by.grigoryev.overwatch.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final CoinRepository coinRepository;

    private final UserMapper userMapper;

    @Override
    public Mono<UserDto> notify(String userName, String symbol) {
        return coinRepository.findFirstBySymbolOrderByTimeOfReceivingDesc(symbol)
                .flatMap(coinDto -> {
                    User user = User.builder()
                            .userName(userName)
                            .coinSymbol(coinDto.getSymbol())
                            .coinPrice(coinDto.getPriceUsd())
                            .timeOfRegistration(LocalDateTime.now())
                            .build();
                    return userRepository.save(user)
                            .map(userMapper::toUserDto);
                })
                .log("notify " + userName + " for " + symbol);
    }

    @Scheduled(initialDelay = 10000, fixedRate = 60000)
    private void trackPrice() {
        userRepository.findAll()
                .toStream()
                .forEach(user -> userRepository.findById(user.getId())
                        .zipWith(coinRepository.findFirstBySymbolOrderByTimeOfReceivingDesc(user.getCoinSymbol()))
                        .map(userPrice -> {
                            BigDecimal oldPrice = userPrice.getT1().getCoinPrice();
                            BigDecimal newPrice = userPrice.getT2().getPriceUsd();
                            BigDecimal percentage = ((oldPrice.subtract(newPrice))
                                    .divide(((oldPrice.add(newPrice))
                                            .divide(BigDecimal.valueOf(2), 4, RoundingMode.HALF_DOWN)), 4, RoundingMode.HALF_UP))
                                    .multiply(BigDecimal.valueOf(100));
                            DecimalFormat decimalFormat = new DecimalFormat("#.##");
                            if (Math.abs(percentage.doubleValue()) >= 1) {
                                log.warn("Price for user #{} {} with {} changed {} %", userPrice.getT1().getId(),
                                        userPrice.getT1().getUserName(), userPrice.getT2().getName(), decimalFormat.format(percentage));
                            }
                            return percentage;
                        })
                        .subscribe()
                );
    }

}
