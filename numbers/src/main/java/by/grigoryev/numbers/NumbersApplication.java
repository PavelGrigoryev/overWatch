package by.grigoryev.numbers;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Day Today",
                version = "1.0.2",
                contact = @Contact(
                        name = "Author: Grigoryev Pavel",
                        url = "https://pavelgrigoryev.github.io/GrigoryevPavel/"
                )
        ),
        servers = @Server(url = "http://localhost:8086")
)
@SpringBootApplication
public class NumbersApplication {

    public static void main(String[] args) {
        SpringApplication.run(NumbersApplication.class, args);
    }

}
