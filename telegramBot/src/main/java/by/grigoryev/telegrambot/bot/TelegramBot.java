package by.grigoryev.telegrambot.bot;

import by.grigoryev.telegrambot.dto.TelegramCoinDto;
import by.grigoryev.telegrambot.service.TelegramButtonsForCryptoCurrencyService;
import by.grigoryev.telegrambot.service.TelegramUserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

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

    private final TelegramButtonsForCryptoCurrencyService telegramButtonsForCryptoCurrencyService;

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
                sendMenu(user.getId(), "<b>Main Menu for " + user.getFirstName()
                        + " \uD83C\uDF1E\uD83C\uDF1E\uD83C\uDF1E\uD83C\uDF08\uD83C\uDF08" +
                        "\uD83C\uDF08\uD83C\uDF1A\uD83C\uDF1A\uD83C\uDF1A</b>");
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
        log.warn("{} action: {}", user.getFirstName(), action);
        switch (action) {
            case "notify" -> addEditMessage(callbackQuery, telegramButtonsForCryptoCurrencyService.addNotifyButtons());
            case "btc", "eth", "sol" -> telegramUserService.register(action.toUpperCase(), callbackQuery)
                    .subscribe(telegramUserDto -> sendText(user.getId(),
                            "You will be notified if the price of cryptocurrency "
                                    + action.toUpperCase() + " changes!"));
            case "showCoin" ->
                    addEditMessage(callbackQuery, telegramButtonsForCryptoCurrencyService.addShowCoinButtons());
            case "btc!", "eth!", "sol!" -> telegramUserService.findFirstBySymbol(action.substring(0, 3).toUpperCase())
                    .subscribe(showTelegramCoinDtoToUser(user, formatter));
            case "showAllCoins" -> telegramUserService.viewListOfAvailable()
                    .subscribe(showTelegramCoinDtoToUser(user, formatter));
            case "findAll" -> telegramUserService.findAllByTelegramUserId(user.getId())
                    .switchIfEmpty(subscriber -> sendText(user.getId(), "You don't have notifications"))
                    .subscribe(telegramUserDto -> sendText(user.getId(), "notification: " + telegramUserDto.getId()
                            + "\ncoin: " + telegramUserDto.getCoinSymbol()
                            + "\nprice: " + telegramUserDto.getCoinPrice()));
            case "delete" -> telegramUserService.deleteAllByTelegramUserId(user.getId())
                    .switchIfEmpty(subscriber -> sendText(user.getId(), "You don't have notifications"))
                    .subscribe(telegramUserDto -> sendText(user.getId(), "Your notifications: "
                            + telegramUserDto.getId() + " was successfully deleted"));
            case "back" -> addEditMessage(callbackQuery, telegramButtonsForCryptoCurrencyService.addMainButtons());
            default -> sendText(user.getId(), """
                    Available command :
                    /menu""");
        }

        AnswerCallbackQuery close = AnswerCallbackQuery.builder()
                .callbackQueryId(callbackQuery.getId())
                .build();

        execute(close);
    }

    @SneakyThrows
    private void addEditMessage(CallbackQuery callbackQuery, InlineKeyboardMarkup inlineKeyboardMarkup) {
        execute(EditMessageReplyMarkup.builder()
                .chatId(callbackQuery.getFrom().getId())
                .messageId(callbackQuery.getMessage().getMessageId())
                .replyMarkup(inlineKeyboardMarkup)
                .build());
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
        SendMessage sendMessage = SendMessage.builder()
                .chatId(who.toString())
                .parseMode("HTML")
                .text(what)
                .replyMarkup(telegramButtonsForCryptoCurrencyService.addMainButtons())
                .build();

        execute(sendMessage);
    }

}
