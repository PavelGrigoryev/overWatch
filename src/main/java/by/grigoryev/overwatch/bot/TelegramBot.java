package by.grigoryev.overwatch.bot;

import by.grigoryev.overwatch.mapper.UserMapper;
import by.grigoryev.overwatch.model.User;
import by.grigoryev.overwatch.repository.CoinRepository;
import by.grigoryev.overwatch.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;

    private final CoinRepository coinRepository;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        org.telegram.telegrambots.meta.api.objects.User user = message.getFrom();

        log.warn(user.getUserName() + " wrote: " + message.getText());

        if (message.hasText()) {
            sendText(user.getId(), "Dear " + user.getUserName() + " !");
            String text = message.getText();
            if ("BTC".equals(text) || "ETH".equals(text) || "SOL".equals(text)) {
                saveTelegramUser(message, user);
            } else {
                sendText(user.getId(), "Available only: BTC, ETH, SOL!");
            }
        } else {
            sendText(user.getId(), "Available only text messages!");
        }
    }

    private void saveTelegramUser(Message message, org.telegram.telegrambots.meta.api.objects.User user) {
        coinRepository.findFirstBySymbolOrderByTimeOfReceivingDesc(message.getText())
                .flatMap(coin -> {
                    User userFromTelegram = User.builder()
                            .userName(user.getUserName())
                            .coinSymbol(coin.getSymbol())
                            .coinPrice(coin.getPriceUsd())
                            .timeOfRegistration(LocalDateTime.now())
                            .telegramUserId(user.getId())
                            .build();
                    sendText(user.getId(), "You will be notified when the price of " + message.getText() +
                            " changes by more than 1%");
                    return userRepository.save(userFromTelegram)
                            .map(userMapper::toUserDto);
                })
                .log("notify " + user.getUserName() + " for " + message.getText())
                .subscribe();
    }

    public void sendText(Long who, String what) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(who.toString())
                .text(what).build();
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
