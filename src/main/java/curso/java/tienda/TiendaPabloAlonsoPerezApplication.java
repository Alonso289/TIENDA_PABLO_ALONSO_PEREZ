package curso.java.tienda;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TiendaPabloAlonsoPerezApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiendaPabloAlonsoPerezApplication.class, args);
		BasicConfigurator.configure();
	}

}
