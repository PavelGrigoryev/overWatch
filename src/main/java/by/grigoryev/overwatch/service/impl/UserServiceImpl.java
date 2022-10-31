package by.grigoryev.overwatch.service.impl;

import by.grigoryev.overwatch.dto.UserDto;
import by.grigoryev.overwatch.mapper.UserMapper;
import by.grigoryev.overwatch.model.User;
import by.grigoryev.overwatch.repository.UserRepository;
import by.grigoryev.overwatch.service.CoinService;
import by.grigoryev.overwatch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final CoinService coinService;

    private final UserMapper userMapper;

    @Override
    public Mono<UserDto> save(String userName, String symbol) {
        return coinService.findDistinctFirstBySymbol(symbol)
                .flatMap(coinDto -> {
                    User user = User.builder()
                            .userName(userName)
                            .coinSymbol(coinDto.getSymbol())
                            .coinPrice(coinDto.getPriceUsd())
                            .localDateTime(LocalDateTime.now())
                            .build();
                    return userRepository.save(user)
                            .map(userMapper::toUserDto);
                })
                .log();
    }
}
