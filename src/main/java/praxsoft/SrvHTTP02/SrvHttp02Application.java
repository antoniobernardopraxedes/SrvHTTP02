package praxsoft.SrvHTTP02;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SrvHttp02Application implements CommandLineRunner {

	public static void main(String[] args) throws Exception {

		SpringApplication.run(SrvHttp02Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println("\nPrograma Iniciado\n");
	}

}
