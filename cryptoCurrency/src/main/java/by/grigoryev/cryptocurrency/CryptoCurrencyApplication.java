package by.grigoryev.cryptocurrency;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Crypto Currency Watcher",
                version = "1.2.0",
                contact = @Contact(
                        name = "Author: Grigoryev Pavel",
                        url = "https://pavelgrigoryev.github.io/GrigoryevPavel/"
                )
        ),
        servers = @Server(url = "http://localhost:8085")
)
@SpringBootApplication
public class CryptoCurrencyApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptoCurrencyApplication.class, args);
    }

}
