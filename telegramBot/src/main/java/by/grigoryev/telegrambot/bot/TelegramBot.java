package by.grigoryev.telegrambot.bot;

import by.grigoryev.telegrambot.dto.TelegramCoinDto;
import by.grigoryev.telegrambot.service.TelegramUserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;

    private final TelegramUserService telegramUserService;

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
        User user = message.getFrom();

        log.warn(user.getUserName() + " wrote: " + message.getText());

        if (message.hasText()) {
            sendText(user.getId(), "Dear " + user.getUserName() + " !");
            String text = message.getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 'T' HH:mm:ss");

            switch (text) {
                case "BTC", "ETH", "SOL" -> telegramUserService.register(text, update).
                        subscribe(telegramUserDto -> sendText(user.getId(),
                                "You will be notified if the price of cryptocurrency " + text + " changes"));
                case "ViewAll" -> telegramUserService.viewListOfAvailable()
                        .subscribe(showTelegramCoinDtoToUser(user, formatter));
                case "Symbol:BTC", "Symbol:ETH", "Symbol:SOL" ->
                        telegramUserService.findFirstBySymbol(text.substring(7, 10))
                                .subscribe(showTelegramCoinDtoToUser(user, formatter));
                default -> sendText(user.getId(), """
                        Available commands :
                        BTC
                        ETH
                        SOL
                        ViewAll
                        Symbol:BTC
                        Symbol:ETH
                        Symbol:SOL""");
            }
        } else {
            sendText(user.getId(), "Available only text messages!");
        }
    }

    private Consumer<TelegramCoinDto> showTelegramCoinDtoToUser(User user, DateTimeFormatter formatter) {
        return telegramCoinDto -> sendText(user.getId(), "id: " + telegramCoinDto.getId() +
                "\nsymbol: " + telegramCoinDto.getSymbol() + "\nname: " + telegramCoinDto.getName() +
                "\nprice_usd: " + telegramCoinDto.getPriceUsd() + "\ndate: " +
                telegramCoinDto.getTimeOfReceiving().format(formatter));
    }

    @SneakyThrows
    public void sendText(Long who, String what) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(who.toString())
                .text(what).build();

        execute(sendMessage);
    }

}
