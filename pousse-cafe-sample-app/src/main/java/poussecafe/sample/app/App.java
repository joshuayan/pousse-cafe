package poussecafe.sample.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = {"poussecafe.sample.domain.mongo", "poussecafe.spring.mongo"})
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
