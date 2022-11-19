package by.grigoryev.numbers.service;

import reactor.core.publisher.Mono;

public interface DateService {

    Mono<String> findAFactAboutDateToday();

}
