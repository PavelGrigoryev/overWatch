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
        String text = message.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 'T' HH:mm:ss");

        log.warn(user.getUserName() + " " + user.getFirstName() + " " + user.getLastName() + " wrote: " + text);

        if (message.hasText()) {
            if (user.getLanguageCode().equals("ru")) {
                sendText(user.getId(), "Дорогой " + user.getFirstName() +
                        " \uD83D\uDC49\uD83D\uDC4C\uD83D\uDCA6\uD83D\uDCA6\uD83D\uDCA6\uD83D\uDCA6\uD83D\uDCA6");

                switch (text) {
                    case "/notifybtc", "/notifyeth", "/notifysol" -> telegramUserService
                            .register(text.substring(7, 10).toUpperCase(), update)
                            .subscribe(telegramUserDto -> sendText(user.getId(),
                                    "Вы будете уведомлены если цена крипты "
                                            + text.substring(7, 10).toUpperCase() + " изменится"));
                    case "/viewall" -> telegramUserService.viewListOfAvailable()
                            .subscribe(showTelegramCoinDtoToUser(user, formatter));
                    case "/btc", "/eth", "/sol" ->
                            telegramUserService.findFirstBySymbol(text.substring(1, 4).toUpperCase())
                                    .subscribe(showTelegramCoinDtoToUser(user, formatter));
                    default -> sendText(user.getId(), """
                            Доступные команды :
                            /notifybtc - уведомляет юзера о изменении цены 0.01% на Биткоин
                            /notifyeth - уведомляет юзера о изменении цены 0.01% на Эфир
                            /notifysol - уведомляет юзера о изменении цены 0.01% на Солану
                            /viewall - показывает актуальную цену на все крипто валюты
                            /btc - показывает актуальную цену на Биткоин
                            /eth - показывает актуальную цену на Эфир
                            /sol - показывает актуальную цену на Солану""");
                }
            } else {
                sendText(user.getId(), "Dear " + user.getFirstName() +
                        " \uD83D\uDC49\uD83D\uDC4C\uD83D\uDCA6\uD83D\uDCA6\uD83D\uDCA6\uD83D\uDCA6\uD83D\uDCA6");

                switch (text) {
                    case "/notifybtc", "/notifyeth", "/notifysol" -> telegramUserService
                            .register(text.substring(7, 10).toUpperCase(), update)
                            .subscribe(telegramUserDto -> sendText(user.getId(),
                                    "You will be notified if the price of cryptocurrency "
                                            + text.substring(7, 10).toUpperCase() + " changes"));
                    case "/viewall" -> telegramUserService.viewListOfAvailable()
                            .subscribe(showTelegramCoinDtoToUser(user, formatter));
                    case "/btc", "/eth", "/sol" ->
                            telegramUserService.findFirstBySymbol(text.substring(1, 4).toUpperCase())
                                    .subscribe(showTelegramCoinDtoToUser(user, formatter));
                    default -> sendText(user.getId(), """
                            Available commands :
                            /notifybtc - notifies the user about a change 0.01% in Bitcoin
                            /notifyeth - notifies the user about a change 0.01% in Etherium
                            /notifysol - notifies the user about a change 0.01% in Solana
                            /viewall - shows the current price at the moment for all
                            /btc - shows the current price at the moment for Bitcoin
                            /eth - shows the current price at the moment for Etherium
                            /sol - shows the current price at the moment for Solana""");
                }
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
