package by.grigoryev.telegrambot.repository;

import by.grigoryev.telegrambot.model.TelegramUser;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelegramUserRepository extends ReactiveCrudRepository<TelegramUser, Long> {

}
