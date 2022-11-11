package by.grigoryev.cryptocurrency.service;

import by.grigoryev.cryptocurrency.dto.UserDto;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<UserDto> notify(String userName, String symbol);

}
