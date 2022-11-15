package by.grigoryev.telegrambot.bot;

import by.grigoryev.telegrambot.dto.TelegramCoinDto;
import by.grigoryev.telegrambot.service.TelegramUserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    public static final String MENU_FOR = "Menu for ";
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
        if (update.hasCallbackQuery()) {
            handleCallback(update.getCallbackQuery());
        } else if (update.hasMessage()) {
            handleMessage(update.getMessage());
        }
    }

    private void handleMessage(Message message) {
        User user = message.getFrom();
        String text = message.getText();

        log.warn(user.getFirstName() + " wrote: " + text);

        if (message.hasText()) {
            sendText(user.getId(), "Dear " + user.getFirstName() +
                    " \uD83D\uDC49\uD83D\uDC4C\uD83D\uDCA6\uD83D\uDCA6\uD83D\uDCA6\uD83D\uDCA6\uD83D\uDCA6");

            if ("/menu".equals(text)) {
                sendMenu(user.getId(), MENU_FOR + user.getFirstName());
            } else {
                sendText(user.getId(), """
                        Available command :
                        /menu""");
            }
        } else {
            sendText(user.getId(), "Available only text messages!");
        }
    }

    @SneakyThrows
    private void handleCallback(CallbackQuery callbackQuery) {
        User user = callbackQuery.getFrom();
        String action = callbackQuery.getData();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 'T' HH:mm:ss");
        log.warn("action from user {} : {}", user.getFirstName(), action);
        switch (action) {
            case "notify:btc", "notify:eth", "notify:sol" -> telegramUserService
                    .register(action.substring(7, 10).toUpperCase(), callbackQuery)
                    .subscribe(telegramUserDto -> sendText(user.getId(),
                            "You will be notified if the price of cryptocurrency "
                                    + action.substring(7, 10).toUpperCase() + " changes"));
            case "viewAll" -> telegramUserService.viewListOfAvailable()
                    .subscribe(showTelegramCoinDtoToUser(user, formatter));
            case "btc", "eth", "sol" -> telegramUserService.findFirstBySymbol(action.toUpperCase())
                    .subscribe(showTelegramCoinDtoToUser(user, formatter));
            default -> sendText(user.getId(), """
                    Available command :
                    /menu""");
        }

        AnswerCallbackQuery close = AnswerCallbackQuery.builder()
                .callbackQueryId(callbackQuery.getId())
                .build();

        execute(close);
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
                .text(what)
                .build();

        execute(sendMessage);
    }

    @SneakyThrows
    public void sendMenu(Long who, String what) {
        InlineKeyboardButton notifyBTC = InlineKeyboardButton.builder()
                .text("Notify: BTC")
                .callbackData("notify:btc")
                .build();

        InlineKeyboardButton notifyETH = InlineKeyboardButton.builder()
                .text("Notify: ETH")
                .callbackData("notify:eth")
                .build();

        InlineKeyboardButton notifySOL = InlineKeyboardButton.builder()
                .text("Notify: SOL")
                .callbackData("notify:sol")
                .build();

        InlineKeyboardButton viewAll = InlineKeyboardButton.builder()
                .text("ViewAll cryptos")
                .callbackData("viewAll")
                .build();

        InlineKeyboardButton btc = InlineKeyboardButton.builder()
                .text("BTC price")
                .callbackData("btc")
                .build();

        InlineKeyboardButton eth = InlineKeyboardButton.builder()
                .text("ETH price")
                .callbackData("eth")
                .build();

        InlineKeyboardButton sol = InlineKeyboardButton.builder()
                .text("SOL price")
                .callbackData("sol")
                .build();

        InlineKeyboardMarkup inlineKeyboardMarkup = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(notifyBTC))
                .keyboardRow(List.of(notifyETH))
                .keyboardRow(List.of(notifySOL))
                .keyboardRow(List.of(viewAll))
                .keyboardRow(List.of(btc))
                .keyboardRow(List.of(eth))
                .keyboardRow(List.of(sol))
                .build();

        SendMessage sendMessage = SendMessage.builder()
                .chatId(who.toString())
                .text(what)
                .replyMarkup(inlineKeyboardMarkup)
                .build();

        execute(sendMessage);
    }

}
