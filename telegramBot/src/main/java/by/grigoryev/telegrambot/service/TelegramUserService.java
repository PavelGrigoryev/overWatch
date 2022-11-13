package by.grigoryev.telegrambot.service;

import by.grigoryev.telegrambot.dto.TelegramCoinDto;
import by.grigoryev.telegrambot.dto.TelegramUserDto;
import org.telegram.telegrambots.meta.api.objects.Update;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TelegramUserService {

    Mono<TelegramUserDto> register(String symbol, Update update);

    Flux<TelegramCoinDto> viewListOfAvailable();

    Mono<TelegramCoinDto> findFirstBySymbol(String symbol);

}
