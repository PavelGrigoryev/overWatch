package by.grigoryev.overwatch.service.impl;

import by.grigoryev.overwatch.dto.CoinDto;
import by.grigoryev.overwatch.model.Coin;
import by.grigoryev.overwatch.repository.CoinRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class CoinServiceImplTest {

    @InjectMocks
    private CoinServiceImpl coinService;

    @Mock
    private CoinRepository coinRepository;

    @Test
    void findFirstBySymbolOrderByTimeOfReceivingDesc() {
        Coin coin = Coin.builder()
                .id(90L)
                .symbol("BTC")
                .name("Bitcoin")
                .priceUsd(BigDecimal.valueOf(2456.23))
                .timeOfReceiving(LocalDateTime.now())
                .build();

        Mockito.when(coinRepository.findFirstBySymbolOrderByTimeOfReceivingDesc(coin.getSymbol()))
                .thenReturn(Mono.just(coin));

        Mono<CoinDto> coinDtoMono = coinService.findFirstBySymbolOrderByTimeOfReceivingDesc(coin.getSymbol());

        StepVerifier.create(coinDtoMono)
                .consumeNextWith(coinDto -> assertEquals(coinDto.getSymbol(), coin.getSymbol()))
                .verifyComplete();
    }
}