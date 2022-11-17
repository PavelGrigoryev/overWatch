package by.grigoryev.telegrambot.controller;

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
public class TelegramNotificationController {

    private final TelegramNotificationService telegramNotificationService;

    @PostMapping
    public Mono<ResponseEntity<String>> notifyTelegramUser(@RequestParam String message, Long telegramId) {
        return telegramNotificationService.notifyTelegramUser(message, telegramId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
