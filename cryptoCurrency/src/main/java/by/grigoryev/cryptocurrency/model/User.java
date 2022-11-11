package by.grigoryev.cryptocurrency.model;

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
@Table("users")
public class User {

    @Id
    private Long id;

    @Column("user_name")
    private String userName;

    @Column("coin_symbol")
    private String coinSymbol;

    @Column("coin_price")
    private BigDecimal coinPrice;

    @Column("time_of_registration")
    private LocalDateTime timeOfRegistration;

}
