package by.grigoryev.telegrambot.service;

import by.grigoryev.telegrambot.dto.TelegramCoinDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TelegramCoinService {

    Flux<TelegramCoinDto> viewListOfAvailable();

    Mono<TelegramCoinDto> findFirstBySymbol(String symbol);


}
