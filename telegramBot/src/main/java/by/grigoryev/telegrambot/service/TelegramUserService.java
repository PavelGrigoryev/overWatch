package by.grigoryev.telegrambot.service;

import by.grigoryev.telegrambot.dto.TelegramUserDto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TelegramUserService {

    Mono<TelegramUserDto> register(String symbol, CallbackQuery callbackQuery);

    Flux<TelegramUserDto> findAllByTelegramUserId(Long telegramId);

    Flux<TelegramUserDto> deleteAllByTelegramUserId(Long telegramId);

    Mono<String> deleteById(Long id, Long telegramId);

}
