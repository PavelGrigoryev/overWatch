package by.grigoryev.telegrambot.service.impl;

import by.grigoryev.telegrambot.dto.TelegramCoinDto;
import by.grigoryev.telegrambot.service.TelegramCoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TelegramCoinServiceImpl implements TelegramCoinService {

    private final WebClient webClient;

    @Override
    public Flux<TelegramCoinDto> viewListOfAvailable() {
        return webClient.get()
                .uri("/coins")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(TelegramCoinDto.class)
                .log("viewListOfAvailable");
    }

    @Override
    public Mono<TelegramCoinDto> findFirstBySymbol(String symbol) {
        return webClient.get()
                .uri("/coins/" + symbol)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(TelegramCoinDto.class)
                .log("findBySymbol " + symbol);
    }

}
