package by.grigoryev.telegrambot.service;

import reactor.core.publisher.Mono;

public interface TelegramNotificationService {

    Mono<String> notifyTelegramUser(String userName, String message);

}
