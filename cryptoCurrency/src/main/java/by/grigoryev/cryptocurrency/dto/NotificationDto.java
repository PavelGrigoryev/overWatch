package by.grigoryev.cryptocurrency.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "user_name",
        "message"
})
public class NotificationDto {

    @JsonProperty("user_name")
    private String userName;

    private String message;

}
