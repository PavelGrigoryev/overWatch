package by.grigoryev.telegrambot.service.impl;

import by.grigoryev.telegrambot.service.TelegramButtonsService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Service
public class TelegramButtonsServiceImpl implements TelegramButtonsService {

    @Override
    public InlineKeyboardMarkup addButtonsToCryptoCurrency() {
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

        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(notifyBTC, notifyETH, notifySOL))
                .keyboardRow(List.of(btc, eth, sol))
                .keyboardRow(List.of(viewAll))
                .build();
    }
}
