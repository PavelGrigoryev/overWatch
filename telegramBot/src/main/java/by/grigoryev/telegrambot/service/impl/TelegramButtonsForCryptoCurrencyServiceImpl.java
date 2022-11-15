package by.grigoryev.telegrambot.service.impl;

import by.grigoryev.telegrambot.service.TelegramButtonsForCryptoCurrencyService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Service
public class TelegramButtonsForCryptoCurrencyServiceImpl implements TelegramButtonsForCryptoCurrencyService {

    @Override
    public InlineKeyboardMarkup addMainButtons() {
        InlineKeyboardButton notifyMe = InlineKeyboardButton.builder()
                .text("Notify Me")
                .callbackData("notify")
                .build();

        InlineKeyboardButton showCoin = InlineKeyboardButton.builder()
                .text("Show Me a coin")
                .callbackData("showCoin")
                .build();

        InlineKeyboardButton showAllCoins = InlineKeyboardButton.builder()
                .text("Show All coins")
                .callbackData("showAllCoins")
                .build();

        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(notifyMe, showCoin, showAllCoins))
                .build();
    }

    @Override
    public InlineKeyboardMarkup addNotifyButtons() {

        InlineKeyboardButton btc = InlineKeyboardButton.builder()
                .text("for Bitcoin")
                .callbackData("btc")
                .build();

        InlineKeyboardButton eth = InlineKeyboardButton.builder()
                .text("for Ethereum")
                .callbackData("eth")
                .build();

        InlineKeyboardButton sol = InlineKeyboardButton.builder()
                .text("for Solana")
                .callbackData("sol")
                .build();

        InlineKeyboardButton back = InlineKeyboardButton.builder()
                .text("Back to Main Menu")
                .callbackData("backFromNotify")
                .build();

        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(btc, eth, sol))
                .keyboardRow(List.of(back))
                .build();
    }

    @Override
    public InlineKeyboardMarkup addShowCoinButtons() {
        InlineKeyboardButton btc = InlineKeyboardButton.builder()
                .text("Bitcoin")
                .callbackData("btc!")
                .build();

        InlineKeyboardButton eth = InlineKeyboardButton.builder()
                .text("Ethereum")
                .callbackData("eth!")
                .build();

        InlineKeyboardButton sol = InlineKeyboardButton.builder()
                .text("Solana")
                .callbackData("sol!")
                .build();

        InlineKeyboardButton back = InlineKeyboardButton.builder()
                .text("Back to Main Menu")
                .callbackData("backFromShowCoin")
                .build();

        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(btc, eth, sol))
                .keyboardRow(List.of(back))
                .build();
    }

}
