package by.grigoryev.cryptocurrency.service.impl;

import by.grigoryev.cryptocurrency.dto.UserDto;
import by.grigoryev.cryptocurrency.mapper.UserMapper;
import by.grigoryev.cryptocurrency.model.Coin;
import by.grigoryev.cryptocurrency.model.User;
import by.grigoryev.cryptocurrency.repository.CoinRepository;
import by.grigoryev.cryptocurrency.repository.UserRepository;
import by.grigoryev.cryptocurrency.service.NotificationService;
import by.grigoryev.cryptocurrency.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
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

    private final NotificationService notificationService;

    @Override
    public Mono<UserDto> notify(String userName, String symbol, Long id) {
        return coinRepository.findFirstBySymbolOrderByTimeOfReceivingDesc(symbol)
                .flatMap(coin -> createUserMono(userName, coin, id))
                .log("notify " + userName + " for " + symbol);
    }

    @Override
    public Flux<UserDto> findAllByTelegramUserId(Long telegramId) {
        return userRepository.findAllByTelegramUserId(telegramId)
                .map(userMapper::toUserDto)
                .log("findAllByTelegramUserId#" + telegramId);
    }

    @Override
    public Flux<UserDto> deleteAllByTelegramUserId(Long telegramId) {
        return userRepository.findAllByTelegramUserId(telegramId)
                .flatMap(user -> userRepository.deleteById(user.getId())
                        .thenReturn(user))
                .map(userMapper::toUserDto)
                .log("deleteAllByTelegramId#" + telegramId);
    }

    @Override
    public Mono<UserDto> deleteByIdAndTelegramUserId(Long id, Long telegramId) {
        return userRepository.findByIdAndTelegramUserId(id, telegramId)
                .flatMap(user -> userRepository.deleteByIdAndTelegramUserId(user.getId(), user.getTelegramUserId())
                        .thenReturn(user))
                .map(userMapper::toUserDto)
                .log("deleteById#" + id);
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
            notificationService.notifyTelegramUser("Price for cryptocurrency " + userPrice.getT2().getName()
                            + " changed " + percentage + "%\n" + "Old was: " + userPrice.getT1().getCoinPrice()
                            + "\nNew is: " + userPrice.getT2().getPriceUsd(), userPrice.getT1().getTelegramUserId())
                    .subscribe();
        }
    }

    private Mono<UserDto> createUserMono(String userName, Coin coin, Long id) {
        User user = User.builder()
                .userName(userName)
                .coinSymbol(coin.getSymbol())
                .coinPrice(coin.getPriceUsd())
                .timeOfRegistration(LocalDateTime.now())
                .telegramUserId(id)
                .build();
        return userRepository.save(user)
                .map(userMapper::toUserDto);
    }

}
