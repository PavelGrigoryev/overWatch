package by.grigoryev.overwatch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("coins")
public class Coin {

    private Long id;

    private String symbol;

    private String name;

    @Column("price_usd")
    @JsonProperty("price_usd")
    private BigDecimal priceUsd;

    @Column("time_of_creation")
    private LocalDateTime localDateTime;

}