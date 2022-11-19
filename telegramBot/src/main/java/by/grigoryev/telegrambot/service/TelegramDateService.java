package by.grigoryev.telegrambot.service;

import reactor.core.publisher.Mono;

public interface TelegramDateService {

    Mono<String> findAFactAboutDateToday();

}
