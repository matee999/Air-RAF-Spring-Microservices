package raf.petrovicpleskonjic.rafairlinesticketservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RafAirlinesTicketServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RafAirlinesTicketServiceApplication.class, args);
	}

}
