package by.grigoryev.telegrambot.service;

import by.grigoryev.telegrambot.dto.TelegramNotificationDto;
import reactor.core.publisher.Mono;

public interface TelegramNotificationService {

    Mono<TelegramNotificationDto> notifyTelegramUser(String userName, String message);

}
