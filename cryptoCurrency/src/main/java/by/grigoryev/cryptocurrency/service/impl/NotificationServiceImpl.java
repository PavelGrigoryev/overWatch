package by.grigoryev.cryptocurrency.service.impl;

import by.grigoryev.cryptocurrency.dto.NotificationDto;
import by.grigoryev.cryptocurrency.service.NotificationService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Override
    public Mono<NotificationDto> notifyTelegramUser(String message) {
        WebClient webClient = WebClient.create();
        return webClient.post()
                .uri("http://localhost:8084/telegrams?message=" + message)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(NotificationDto.class)
                .log("notifyTelegramUser");
    }

}
