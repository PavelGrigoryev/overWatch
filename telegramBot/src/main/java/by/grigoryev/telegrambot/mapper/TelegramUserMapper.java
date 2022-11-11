package by.grigoryev.telegrambot.mapper;

import by.grigoryev.telegrambot.dto.TelegramUserDto;
import by.grigoryev.telegrambot.model.TelegramUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TelegramUserMapper {

    TelegramUserDto toTelegramUserDto(TelegramUser telegramUser);

}
