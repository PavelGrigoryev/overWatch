package by.grigoryev.telegrambot.service;

import by.grigoryev.telegrambot.dto.TelegramNotification;
import reactor.core.publisher.Mono;

public interface TelegramNotificationService {

    Mono<TelegramNotification> notifyTelegramUser(String message);

}
