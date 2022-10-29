package by.grigoryev.overwatch.service.impl;

import by.grigoryev.overwatch.model.Coin;
import by.grigoryev.overwatch.repository.CoinRepository;
import by.grigoryev.overwatch.service.CoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoinServiceImpl implements CoinService {

    private final CoinRepository coinRepository;

    @Override
    public Flux<Coin> findAll() {
        return coinRepository.findAll().log();
    }

    @Override
    public Mono<Coin> findDistinctFirstBySymbol(String symbol) {
        return coinRepository.findDistinctFirstBySymbol(symbol).log();
    }

    @Override
    public Flux<Coin> getPriceFromCoinLore(String id) {
        WebClient webClient = WebClient.create("https://api.coinlore.net/api/ticker");
        return webClient.get()
                .uri("/?id=" + id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Coin.class)
                .flatMap(this::createCoinMono)
                .log();
    }

    private Mono<Coin> createCoinMono(Coin coin) {
        Coin mapCoin = new Coin();
        mapCoin.setId(coin.getId());
        mapCoin.setName(coin.getName());
        mapCoin.setSymbol(coin.getSymbol());
        mapCoin.setPriceUsd(coin.getPriceUsd());
        return coinRepository.save(mapCoin);
    }
}
