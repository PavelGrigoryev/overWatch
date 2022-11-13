package by.grigoryev.telegrambot.service.impl;

import by.grigoryev.telegrambot.bot.TelegramBot;
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
    public Mono<String> notifyTelegramUser(String userName, String message) {
        return Mono.from(telegramUserRepository.findByUserName(userName)
                .map(telegramUser -> {
                    telegramBot.sendText(telegramUser.getTelegramUserId(), message);
                    return userName + message;
                })
                .log());
    }

}
