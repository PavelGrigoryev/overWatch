package by.grigoryev.cryptocurrency.service;

import by.grigoryev.cryptocurrency.dto.NotificationDto;
import reactor.core.publisher.Mono;

public interface NotificationService {

    Mono<NotificationDto> notifyTelegramUser(String message);

}
