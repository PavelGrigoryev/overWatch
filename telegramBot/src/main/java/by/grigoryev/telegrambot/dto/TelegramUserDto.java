package by.grigoryev.telegrambot.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "user_name",
        "first_name",
        "last_name",
        "coin_symbol",
        "coin_price",
        "time_when_the_message_was_sent",
        "telegram_id",
        "language_code"
})
public class TelegramUserDto {

    private Long id;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("coin_symbol")
    private String coinSymbol;

    @JsonProperty("coin_price")
    private BigDecimal coinPrice;

    @JsonProperty("time_when_the_message_was_sent")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timeWhenTheMessageWasSent;

    @JsonProperty("telegram_id")
    private Long telegramUserId;

    @JsonProperty("language_code")
    private String languageCode;

}
