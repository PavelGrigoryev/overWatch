package by.grigoryev.telegrambot.service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface TelegramButtonsForCryptoCurrencyService {

    InlineKeyboardMarkup addMainButtons();

    InlineKeyboardMarkup addNotifyButtons();

    InlineKeyboardMarkup addShowCoinButtons();

}
