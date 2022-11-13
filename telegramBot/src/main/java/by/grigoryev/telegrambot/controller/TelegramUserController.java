package by.grigoryev.telegrambot.controller;

import by.grigoryev.telegrambot.dto.TelegramNotification;
import by.grigoryev.telegrambot.service.TelegramNotificationService;
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

    private final TelegramNotificationService telegramNotificationService;

    @PostMapping
    public Mono<ResponseEntity<TelegramNotification>> notifyTelegramUser(@RequestParam String message) {
        return telegramNotificationService.notifyTelegramUser(message)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
