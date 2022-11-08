package by.grigoryev.overwatch.controller;

import by.grigoryev.overwatch.dto.CoinDto;
import by.grigoryev.overwatch.service.CoinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Coin", description = "The Coin API")
public class CoinController {

    private final CoinService coinService;

    @Operation(
            summary = "View a list of available cryptocurrencies.", tags = "Coin",
            description = "View a list of available cryptocurrencies."
    )
    @GetMapping
    public Flux<CoinDto> viewListOfAvailable() {
        return coinService.viewListOfAvailable();
    }

    @Operation(
            summary = "View the current price for the specified cryptocurrency.", tags = "Coin",
            description = "Let's find our cryptocurrency by symbol: BTC, ETH, SOL",
            parameters = @Parameter(name = "symbol", description = "Enter symbol here", example = "BTC")
    )
    @GetMapping("/{symbol}")
    public Mono<ResponseEntity<CoinDto>> findFirstBySymbolOrderByTimeOfReceivingDesc(@PathVariable String symbol) {
        return coinService.findFirstBySymbolOrderByTimeOfReceivingDesc(symbol)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
