package raf.petrovicpleskonjic.rafairlinesflightservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RafAirlinesFlightServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RafAirlinesFlightServiceApplication.class, args);
	}

}
