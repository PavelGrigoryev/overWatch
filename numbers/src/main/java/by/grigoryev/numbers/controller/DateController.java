package by.grigoryev.numbers.controller;

import by.grigoryev.numbers.service.DateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dates")
@Tag(name = "Date", description = "The Date API")
public class DateController {

    private final DateService dateService;

    @Operation(
            summary = "View a fact about a day today", tags = "Date", description = "View a fact about a day today"
    )
    @GetMapping
    public Mono<ResponseEntity<String>> findAFactAboutDateToday() {
        return dateService.findAFactAboutDateToday()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
