package by.grigoryev.cryptocurrency.repository;

import by.grigoryev.cryptocurrency.model.Coin;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CoinRepository extends ReactiveCrudRepository<Coin, Long> {

    Mono<Coin> findFirstBySymbolOrderByTimeOfReceivingDesc(String symbol);

}
