package by.grigoryev.telegrambot.service.impl;

import by.grigoryev.telegrambot.bot.TelegramBot;
import by.grigoryev.telegrambot.dto.TelegramNotification;
import by.grigoryev.telegrambot.repository.TelegramUserRepository;
import by.grigoryev.telegrambot.service.TelegramNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TelegramNotificationServiceImpl implements TelegramNotificationService {

    private final TelegramBot telegramBot;

    private final TelegramUserRepository telegramUserRepository;

    @Override
    public Mono<TelegramNotification> notifyTelegramUser(String message) {
        return Mono.from(telegramUserRepository.findAll())
                .map(telegramUser -> {
                    TelegramNotification telegramNotification = TelegramNotification.builder()
                            .message(message)
                            .build();
                    telegramBot.sendText(telegramUser.getTelegramUserId(), message);
                    return telegramNotification;
                })
                .log();
    }

}
