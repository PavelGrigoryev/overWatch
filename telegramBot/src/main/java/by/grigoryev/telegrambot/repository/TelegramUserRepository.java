package by.grigoryev.telegrambot.repository;

import by.grigoryev.telegrambot.model.TelegramUser;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TelegramUserRepository extends ReactiveCrudRepository<TelegramUser, Long> {

    Flux<TelegramUser> findByTelegramUserId(Long telegramId);

}
