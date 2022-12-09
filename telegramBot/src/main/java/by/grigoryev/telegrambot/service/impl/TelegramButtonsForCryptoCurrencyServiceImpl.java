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

        InlineKeyboardButton findAFactAboutDateToday = InlineKeyboardButton.builder()
                .text("Get a fact about today's date")
                .callbackData("dateToday")
                .build();

        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(notifyMe, showCoin, showAllCoins))
                .keyboardRow(List.of(findAFactAboutDateToday))
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

        InlineKeyboardButton bnb = InlineKeyboardButton.builder()
                .text("for Binance Coin")
                .callbackData("bnb")
                .build();

        InlineKeyboardButton dot = InlineKeyboardButton.builder()
                .text("for Polkadot")
                .callbackData("dot")
                .build();

        InlineKeyboardButton ltc = InlineKeyboardButton.builder()
                .text("for Litecoin")
                .callbackData("ltc")
                .build();

        InlineKeyboardButton avax = InlineKeyboardButton.builder()
                .text("for Avalanche")
                .callbackData("avax")
                .build();

        InlineKeyboardButton atom = InlineKeyboardButton.builder()
                .text("for Cosmos")
                .callbackData("atom")
                .build();

        InlineKeyboardButton near = InlineKeyboardButton.builder()
                .text("for NEAR Protocol")
                .callbackData("near")
                .build();

        InlineKeyboardButton findAllYourNotifications = InlineKeyboardButton.builder()
                .text("Find all your notifications")
                .callbackData("findAll")
                .build();

        InlineKeyboardButton deleteAllYourNotifications = InlineKeyboardButton.builder()
                .text("Delete all your notifications")
                .callbackData("delete")
                .build();

        InlineKeyboardButton back = InlineKeyboardButton.builder()
                .text("Back to Main Menu")
                .callbackData("back")
                .build();

        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(btc, eth, sol))
                .keyboardRow(List.of(bnb, dot, ltc))
                .keyboardRow(List.of(avax, atom, near))
                .keyboardRow(List.of(findAllYourNotifications))
                .keyboardRow(List.of(deleteAllYourNotifications))
                .keyboardRow(List.of(back))
                .build();
    }

    @Override
    public InlineKeyboardMarkup addShowCoinButtons() {

        InlineKeyboardButton btc = InlineKeyboardButton.builder()
                .text("Bitcoin")
                .callbackData("!btc")
                .build();

        InlineKeyboardButton eth = InlineKeyboardButton.builder()
                .text("Ethereum")
                .callbackData("!eth")
                .build();

        InlineKeyboardButton sol = InlineKeyboardButton.builder()
                .text("Solana")
                .callbackData("!sol")
                .build();

        InlineKeyboardButton bnb = InlineKeyboardButton.builder()
                .text("Binance Coin")
                .callbackData("!bnb")
                .build();

        InlineKeyboardButton dot = InlineKeyboardButton.builder()
                .text("Polkadot")
                .callbackData("!dot")
                .build();

        InlineKeyboardButton ltc = InlineKeyboardButton.builder()
                .text("Litecoin")
                .callbackData("!ltc")
                .build();

        InlineKeyboardButton avax = InlineKeyboardButton.builder()
                .text("Avalanche")
                .callbackData("!avax")
                .build();

        InlineKeyboardButton atom = InlineKeyboardButton.builder()
                .text("Cosmos")
                .callbackData("!atom")
                .build();

        InlineKeyboardButton near = InlineKeyboardButton.builder()
                .text("NEAR Protocol")
                .callbackData("!near")
                .build();

        InlineKeyboardButton back = InlineKeyboardButton.builder()
                .text("Back to Main Menu")
                .callbackData("back")
                .build();

        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(btc, eth, sol))
                .keyboardRow(List.of(bnb, dot, ltc))
                .keyboardRow(List.of(avax, atom, near))
                .keyboardRow(List.of(back))
                .build();
    }

}
