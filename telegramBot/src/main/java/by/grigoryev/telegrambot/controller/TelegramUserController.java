package by.grigoryev.telegrambot.controller;

import by.grigoryev.telegrambot.service.TelegramUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/telegrams")
public class TelegramUserController {

    private final TelegramUserService telegramUserService;

    @PostMapping
    public Mono<ResponseEntity<String>> notifyTelegramUser(@RequestParam String message) {
        return telegramUserService.notifyTelegramUser(message)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
