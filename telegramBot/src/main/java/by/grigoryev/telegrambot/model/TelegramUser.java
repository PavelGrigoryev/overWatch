package by.grigoryev.telegrambot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("telegram_users")
public class TelegramUser {

    @Id
    private Long id;

    @Column("user_name")
    private String userName;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @Column("coin_symbol")
    private String coinSymbol;

    @Column("coin_price")
    private BigDecimal coinPrice;

    @Column("time_when_the_message_was_sent")
    private LocalDateTime timeWhenTheMessageWasSent;

    @Column("telegram_id")
    private Long telegramUserId;

    @Column("language_code")
    private String languageCode;

}
