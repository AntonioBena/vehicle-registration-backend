package org.interview.vehicleregistration;

import org.interview.vehicleregistration.configuration.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class VehicleRegistrationApplication {
	public static void main(String[] args) {
		SpringApplication.run(VehicleRegistrationApplication.class, args);
	}
}