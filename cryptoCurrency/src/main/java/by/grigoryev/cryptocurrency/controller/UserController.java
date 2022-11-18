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
            summary = "Find all users by your TelegramID", tags = "User", description = "Enter your TelegramID",
            parameters = {
                    @Parameter(name = "telegramId", description = "Enter your TelegramID here", example = "2556487665")
            }
    )
    @GetMapping
    public Flux<UserDto> findAllByTelegramUserId(@RequestParam Long telegramId) {
        return userService.findAllByTelegramUserId(telegramId);
    }

    @Operation(
            summary = "Delete your all users by TelegramID", tags = "User", description = "Enter your TelegramID",
            parameters = {
                    @Parameter(name = "telegramId", description = "Enter your TelegramID here", example = "2556487665")
            }
    )
    @DeleteMapping
    public Flux<UserDto> deleteAllByTelegramUserId(@RequestParam Long telegramId) {
        return userService.deleteAllByTelegramUserId(telegramId);
    }

    @Operation(
            summary = "Delete your one user by id and telegramID", tags = "User", description = "Enter your id and telegramID",
            parameters = {
                    @Parameter(name = "id", description = "Enter your id here", example = "3"),
                    @Parameter(name = "telegramId", description = "Enter your telegramID here", example = "2556487665")
            }
    )
    @DeleteMapping("/delete")
    public Mono<ResponseEntity<String>> deleteById(@RequestParam Long id, Long telegramId) {
        return userService.deleteByIdAndTelegramUserId(id, telegramId)
                .flatMap(userDto -> Mono.just(ResponseEntity
                        .ok("Your notification # " + id + " was successfully deleted")))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

}
