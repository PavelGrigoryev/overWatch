package by.grigoryev.overwatch.dto;

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
        "symbol",
        "name",
        "price_usd",
        "time_of_receiving"
})
public class CoinDto {

    private Long id;

    private String symbol;

    private String name;

    @JsonProperty("price_usd")
    private BigDecimal priceUsd;

    @JsonProperty("time_of_receiving")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timeOfReceiving;

}
