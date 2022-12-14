package by.grigoryev.cryptocurrency.service;

import by.grigoryev.cryptocurrency.dto.UserDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<UserDto> notify(String userName, String symbol, Long id);

    Flux<UserDto> findAllByTelegramUserId(Long telegramId);

    Flux<UserDto> deleteAllByTelegramUserId(Long telegramId);

    Mono<UserDto> deleteByIdAndTelegramUserId(Long id, Long telegramId);

}
