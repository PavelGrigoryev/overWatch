package by.grigoryev.numbers.service.impl;

import by.grigoryev.numbers.service.DateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DateServiceImpl implements DateService {

    private final WebClient webClient;

    @Override
    public Mono<String> findAFactAboutDateToday() {
        return webClient.get()
                .uri("/" + LocalDateTime.now().getMonthValue() + "/" + LocalDateTime.now().getDayOfMonth())
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .bodyToMono(String.class)
                .log("findAFactAboutDateToday");
    }
}
