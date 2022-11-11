package by.grigoryev.cryptocurrency.service.impl;

import by.grigoryev.cryptocurrency.dto.CoinDto;
import by.grigoryev.cryptocurrency.mapper.CoinMapper;
import by.grigoryev.cryptocurrency.model.Coin;
import by.grigoryev.cryptocurrency.repository.CoinRepository;
import by.grigoryev.cryptocurrency.service.CoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CoinServiceImpl implements CoinService {

    @Value("${available.coins}")
    private String availableCoins;

    private final CoinRepository coinRepository;

    private final CoinMapper coinMapper;

    private final WebClient webClient;

    @Override
    public Flux<CoinDto> viewListOfAvailable() {
        return webClient.get()
                .uri(availableCoins)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(CoinDto.class)
                .log("viewListOfAvailable");
    }

    @Override
    public Mono<CoinDto> findFirstBySymbolOrderByTimeOfReceivingDesc(String symbol) {
        return coinRepository.findFirstBySymbolOrderByTimeOfReceivingDesc(symbol)
                .map(coinMapper::toCoinDto)
                .log("findBySymbol " + symbol);
    }

    @Scheduled(fixedRate = 60000)
    private void savePricesForAvailable() {
        webClient.get()
                .uri(availableCoins)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Coin.class)
                .flatMap(this::createCoinMono)
                .log("savePricesForAvailable")
                .subscribe();
    }

    private Mono<CoinDto> createCoinMono(Coin coin) {
        Coin mapCoin = Coin.builder()
                .id(coin.getId())
                .name(coin.getName())
                .symbol(coin.getSymbol())
                .priceUsd(coin.getPriceUsd())
                .timeOfReceiving(LocalDateTime.now())
                .build();
        return coinRepository.save(mapCoin)
                .map(coinMapper::toCoinDto);
    }
}
