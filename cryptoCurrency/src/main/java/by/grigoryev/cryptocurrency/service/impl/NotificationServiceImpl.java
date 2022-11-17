package by.grigoryev.cryptocurrency.service.impl;

import by.grigoryev.cryptocurrency.service.NotificationService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Override
    public Mono<String> notifyTelegramUser(String message, Long telegramId) {
        WebClient webClient = WebClient.create();
        return webClient.post()
                .uri("http://localhost:8084/telegrams?message=" + message + "&telegramId=" + telegramId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorReturn("No user found with telegramId#" + telegramId)
                .log("notifyTelegramUser telegramId#" + telegramId);
    }

}
