package by.grigoryev.overwatch.service.impl;

import by.grigoryev.overwatch.dto.UserDto;
import by.grigoryev.overwatch.mapper.UserMapper;
import by.grigoryev.overwatch.model.Coin;
import by.grigoryev.overwatch.model.User;
import by.grigoryev.overwatch.repository.CoinRepository;
import by.grigoryev.overwatch.repository.UserRepository;
import by.grigoryev.overwatch.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
                .flatMap(coin -> createUserMono(userName, coin))
                .log("notify " + userName + " for " + symbol);
    }

    /**
     * Percentage calculation formula: ((a - b) / a) * 100
     */
    @Scheduled(initialDelay = 10000, fixedRate = 60000)
    private void trackPrice() {
        userRepository.findAll()
                .subscribe(user -> userRepository.findById(user.getId())
                        .zipWith(coinRepository.findFirstBySymbolOrderByTimeOfReceivingDesc(user.getCoinSymbol()))
                        .map(userPrice -> {
                            BigDecimal oldPrice = userPrice.getT1().getCoinPrice();
                            BigDecimal newPrice = userPrice.getT2().getPriceUsd();
                            BigDecimal percentage = (((newPrice.subtract(oldPrice)).divide(newPrice, 4, RoundingMode.DOWN))
                                    .multiply(BigDecimal.valueOf(100))).setScale(2, RoundingMode.DOWN);
                            checkPercentage(userPrice, percentage);
                            return percentage;
                        })
                        .subscribe()
                );
    }

    private void checkPercentage(Tuple2<User, Coin> userPrice, BigDecimal percentage) {
        if (Math.abs(percentage.doubleValue()) >= 0.01) {
            log.warn("Price for user #{} {} with {} changed {} %", userPrice.getT1().getId(),
                    userPrice.getT1().getUserName(), userPrice.getT2().getName(), percentage);
        }
    }

    private Mono<UserDto> createUserMono(String userName, Coin coin) {
        User user = User.builder()
                .userName(userName)
                .coinSymbol(coin.getSymbol())
                .coinPrice(coin.getPriceUsd())
                .timeOfRegistration(LocalDateTime.now())
                .build();
        return userRepository.save(user)
                .map(userMapper::toUserDto);
    }

}
