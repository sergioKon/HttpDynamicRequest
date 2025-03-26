package common.http.request;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"rest.controllers"})
public class Application {


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

