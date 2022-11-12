package by.grigoryev.telegrambot.service.impl;

import by.grigoryev.telegrambot.dto.TelegramCoinDto;
import by.grigoryev.telegrambot.dto.TelegramUserDto;
import by.grigoryev.telegrambot.mapper.TelegramUserMapper;
import by.grigoryev.telegrambot.model.TelegramUser;
import by.grigoryev.telegrambot.repository.TelegramUserRepository;
import by.grigoryev.telegrambot.service.TelegramUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TelegramUserServiceImpl implements TelegramUserService {

    private final TelegramUserRepository telegramUserRepository;

    private final TelegramUserMapper telegramUserMapper;

    private final WebClient webClient;

    @Override
    public Mono<TelegramUserDto> notify(String symbol, Update update) {
        User user = update.getMessage().getFrom();

        return webClient.post()
                .uri("/users?userName=" + user.getUserName() + "&symbol=" + symbol)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(TelegramUserDto.class)
                .flatMap(telegramUserDto -> createTelegramUserMono(user, telegramUserDto))
                .log("notify " + user.getUserName() + " for " + symbol);
    }

    @Override
    public Flux<TelegramCoinDto> viewListOfAvailable() {
        return webClient.get()
                .uri("/coins")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(TelegramCoinDto.class)
                .log("viewListOfAvailable");
    }

    @Override
    public Mono<TelegramCoinDto> findFirstBySymbol(String symbol) {
        return webClient.get()
                .uri("/coins/" + symbol)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(TelegramCoinDto.class)
                .log("findBySymbol " + symbol);
    }

    private Mono<TelegramUserDto> createTelegramUserMono(User user, TelegramUserDto telegramUserDto) {
        TelegramUser telegramUser = TelegramUser.builder()
                .userName(user.getUserName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .coinSymbol(telegramUserDto.getCoinSymbol())
                .coinPrice(telegramUserDto.getCoinPrice())
                .timeWhenTheMessageWasSent(LocalDateTime.now())
                .telegramUserId(user.getId())
                .build();
        return telegramUserRepository.save(telegramUser)
                .map(telegramUserMapper::toTelegramUserDto);
    }

}
