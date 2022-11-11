package by.grigoryev.cryptocurrency.dto;

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
        "coin_symbol",
        "coin_price",
        "time_of_registration"
})
public class UserDto {

    private Long id;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("coin_symbol")
    private String coinSymbol;

    @JsonProperty("coin_price")
    private BigDecimal coinPrice;

    @JsonProperty("time_of_registration")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timeOfRegistration;
}
