package by.grigoryev.overwatch.service.impl;

import by.grigoryev.overwatch.dto.UserDto;
import by.grigoryev.overwatch.mapper.UserMapper;
import by.grigoryev.overwatch.model.User;
import by.grigoryev.overwatch.repository.UserRepository;
import by.grigoryev.overwatch.service.CoinService;
import by.grigoryev.overwatch.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final CoinService coinService;

    private final UserMapper userMapper;

    private final List<User> userList = new ArrayList<>();

    @Override
    public Mono<UserDto> notify(String userName, String symbol) {
        return coinService.findFirstBySymbolOrderByTimeOfReceivingDesc(symbol)
                .flatMap(coinDto -> {
                    User user = User.builder()
                            .userName(userName)
                            .coinSymbol(coinDto.getSymbol())
                            .coinPrice(coinDto.getPriceUsd())
                            .timeOfRegistration(LocalDateTime.now())
                            .build();
                    userList.add(user);
                    return userRepository.save(user)
                            .map(userMapper::toUserDto);
                })
                .log();
    }

    @Scheduled(fixedRate = 60000)
    private void trackPrice() {
        if (!userList.isEmpty()) {
            userList.forEach(user -> userRepository.findFirstByCoinSymbolOrderByTimeOfRegistrationDesc(user.getCoinSymbol())
                    .zipWith(coinService.findFirstBySymbolOrderByTimeOfReceivingDesc(user.getCoinSymbol()))
                    .map(userPrice -> {
                        BigDecimal oldPrice = userPrice.getT1().getCoinPrice();
                        BigDecimal newPrice = userPrice.getT2().getPriceUsd();
                        BigDecimal percentage = ((oldPrice.subtract(newPrice))
                                .divide(newPrice, 4, RoundingMode.HALF_UP))
                                .multiply(BigDecimal.valueOf(100));
                        if (Math.abs(percentage.doubleValue()) >= 1) {
                            log.warn("Price for user {} with {} changed {} %",
                                    userPrice.getT1().getUserName(), userPrice.getT2().getName(), percentage);
                        }
                        return percentage;
                    })
                    .subscribe());
        }
    }

}
