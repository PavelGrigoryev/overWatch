package by.grigoryev.cryptocurrency.controller;

import by.grigoryev.cryptocurrency.dto.UserDto;
import by.grigoryev.cryptocurrency.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "User", description = "The User API")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "You will be notified when the cryptocurrency price changes.", tags = "User",
            description = "Enter your login and cryptocurrency symbol: BTC, ETH, SOL",
            parameters = {
                    @Parameter(name = "userName", description = "Enter userName here", example = "Philip"),
                    @Parameter(name = "symbol", description = "Enter symbol here", example = "BTC"),
                    @Parameter(name = "id", description = "Enter telegramId here", example = "2556487665")
            }
    )
    @PostMapping
    public Mono<ResponseEntity<UserDto>> notify(@RequestParam String userName, String symbol, Long id) {
        return userService.notify(userName, symbol, id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Find all users", tags = "User", description = "Find all users"
    )
    @GetMapping
    public Flux<UserDto> findAll() {
        return userService.findAll();
    }

}
