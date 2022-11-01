package by.grigoryev.overwatch.service;

import by.grigoryev.overwatch.dto.UserDto;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<UserDto> notify(String userName, String symbol);

}
