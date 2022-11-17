package by.grigoryev.cryptocurrency.repository;

import by.grigoryev.cryptocurrency.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {

    Flux<User> findAllByTelegramUserId(Long telegramId);

}
