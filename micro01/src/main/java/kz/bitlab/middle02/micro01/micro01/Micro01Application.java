package kz.bitlab.middle02.micro01.micro01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Micro01Application {
// task-service
	public static void main(String[] args) {
		SpringApplication.run(Micro01Application.class, args);
	}

}
