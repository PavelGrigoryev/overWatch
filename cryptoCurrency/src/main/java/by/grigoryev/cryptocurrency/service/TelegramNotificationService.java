package by.grigoryev.cryptocurrency.service;

import reactor.core.publisher.Mono;

public interface TelegramNotificationService {

    Mono<String> notifyTelegramUser(String message);

}
