package by.grigoryev.overwatch.controller;

import by.grigoryev.overwatch.dto.CoinDto;
import by.grigoryev.overwatch.service.CoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coins")
public class CoinController {

    private final CoinService coinService;

    @GetMapping
    public Flux<CoinDto> viewListOfAvailable() {
        return coinService.viewListOfAvailable();
    }

    @GetMapping("/{symbol}")
    public Mono<ResponseEntity<CoinDto>> findFirstBySymbolOrderByTimeOfReceivingDesc(@PathVariable String symbol) {
        return coinService.findFirstBySymbolOrderByTimeOfReceivingDesc(symbol)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
