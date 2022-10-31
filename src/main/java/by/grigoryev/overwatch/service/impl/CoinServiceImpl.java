package by.grigoryev.overwatch.service.impl;

import by.grigoryev.overwatch.dto.CoinDto;
import by.grigoryev.overwatch.mapper.CoinMapper;
import by.grigoryev.overwatch.model.Coin;
import by.grigoryev.overwatch.repository.CoinRepository;
import by.grigoryev.overwatch.service.CoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CoinServiceImpl implements CoinService {

    @Value("${api.url}")
    private String url;

    private final CoinRepository coinRepository;

    private final CoinMapper coinMapper;

    @Override
    public Flux<CoinDto> findAll() {
        return coinRepository.findAll()
                .map(coinMapper::toCoinDto)
                .log();
    }

    @Override
    public Mono<CoinDto> findDistinctFirstBySymbol(String symbol) {
        return coinRepository.findDistinctFirstBySymbol(symbol)
                .map(coinMapper::toCoinDto)
                .log();
    }

    @Override
    public Flux<CoinDto> getPriceFromCoinLore(String id) {
        WebClient webClient = WebClient.create(url);
        return webClient.get()
                .uri("/?id=" + id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Coin.class)
                .flatMap(this::createCoinMono)
                .log();
    }

    private Mono<CoinDto> createCoinMono(Coin coin) {
        Coin mapCoin = Coin.builder()
                .id(coin.getId())
                .name(coin.getName())
                .symbol(coin.getSymbol())
                .priceUsd(coin.getPriceUsd())
                .localDateTime(LocalDateTime.now())
                .build();
        return coinRepository.save(mapCoin)
                .map(coinMapper::toCoinDto);
    }
}
