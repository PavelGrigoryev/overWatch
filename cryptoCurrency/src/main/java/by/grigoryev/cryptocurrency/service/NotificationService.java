package by.grigoryev.cryptocurrency.service;

import reactor.core.publisher.Mono;

public interface NotificationService {

    Mono<String> notifyTelegramUser(String message, Long telegramId);

}
