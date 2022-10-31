package by.grigoryev.overwatch.service;

import by.grigoryev.overwatch.dto.CoinDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CoinService {

    Flux<CoinDto> viewListOfAvailable();

    Flux<CoinDto> findAll();

    Mono<CoinDto> findDistinctFirstBySymbol(String symbol);

    Flux<CoinDto> getPriceFromCoinLore(String id);

}
