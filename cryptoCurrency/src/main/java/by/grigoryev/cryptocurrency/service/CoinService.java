package by.grigoryev.cryptocurrency.service;

import by.grigoryev.cryptocurrency.dto.CoinDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CoinService {

    Flux<CoinDto> viewListOfAvailable();

    Mono<CoinDto> findFirstBySymbolOrderByTimeOfReceivingDesc(String symbol);

}
