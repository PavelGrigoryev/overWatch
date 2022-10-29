package by.grigoryev.overwatch.service;

import by.grigoryev.overwatch.model.Coin;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CoinService {

    Flux<Coin> findAll();

    Mono<Coin> findDistinctFirstBySymbol(String symbol);

    Flux<Coin> getPriceFromCoinLore(String id);

}
