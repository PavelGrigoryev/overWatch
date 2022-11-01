package by.grigoryev.overwatch.repository;

import by.grigoryev.overwatch.model.Coin;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CoinRepository extends ReactiveCrudRepository<Coin, Long> {

    Mono<Coin> findFirstBySymbolOrderByLocalDateTimeDesc(String symbol);

}
