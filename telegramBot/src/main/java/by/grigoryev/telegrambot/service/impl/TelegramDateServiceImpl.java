package by.grigoryev.telegrambot.service.impl;

import by.grigoryev.telegrambot.service.TelegramDateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TelegramDateServiceImpl implements TelegramDateService {

    @Override
    public Mono<String> findAFactAboutDateToday() {
        WebClient webClient = WebClient.create();
        return webClient.get()
                .uri("http://localhost:8086/dates")
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .bodyToMono(String.class)
                .log("findAFactAboutDateToday");
    }

}
