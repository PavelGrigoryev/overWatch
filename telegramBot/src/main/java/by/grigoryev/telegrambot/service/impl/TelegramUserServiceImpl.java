package by.grigoryev.telegrambot.service.impl;

import by.grigoryev.telegrambot.dto.TelegramUserDto;
import by.grigoryev.telegrambot.mapper.TelegramUserMapper;
import by.grigoryev.telegrambot.model.TelegramUser;
import by.grigoryev.telegrambot.repository.TelegramUserRepository;
import by.grigoryev.telegrambot.service.TelegramUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
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
    public Mono<TelegramUserDto> register(String symbol, CallbackQuery callbackQuery) {
        User user = callbackQuery.getFrom();
        return webClient.post()
                .uri("/users?userName=" + user.getUserName() + "&symbol=" + symbol + "&id=" + user.getId())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(TelegramUserDto.class)
                .flatMap(telegramUserDto -> createTelegramUserMono(user, telegramUserDto))
                .log("register " + user.getUserName() + " " + user.getFirstName() + " with " + symbol);
    }

    @Override
    public Flux<TelegramUserDto> findAllByTelegramUserId(Long telegramId) {
        return webClient.get()
                .uri("/users?telegramId=" + telegramId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(TelegramUserDto.class)
                .log("findAllByTelegramUserId#" + telegramId);
    }

    @Override
    public Flux<TelegramUserDto> deleteAllByTelegramUserId(Long telegramId) {
        return webClient.delete()
                .uri("/users?telegramId=" + telegramId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(TelegramUserDto.class)
                .log("deleteAllByTelegramUserId#" + telegramId);
    }

    @Override
    public Mono<String> deleteById(Long id, Long telegramId) {
        return webClient.delete()
                .uri("/users/delete?id=" + id + "&telegramId=" + telegramId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorReturn("Your notification #" + id + " not found")
                .log("deleteById#" + id);
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
                .languageCode(user.getLanguageCode())
                .build();
        return telegramUserRepository.save(telegramUser)
                .map(telegramUserMapper::toTelegramUserDto);
    }

}
