package by.grigoryev.overwatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class OverWatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(OverWatchApplication.class, args);
	}

}
