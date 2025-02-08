package br.com.mastodontech.study.spring;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Spring-Boot-Api-JWT",
        version = "1.0.0",
        description = "Estudando uma implementacao com JWT em uma api"
))
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
