package vcps.irsi.gateway;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import lombok.extern.slf4j.Slf4j;

/**
 * TODO: doc
 */
@Slf4j
@SpringBootApplication
@ConfigurationPropertiesScan
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	/**
	 * TODO: doc
	 */
	@Bean
	ApplicationRunner runner() {
		return args -> {
			log.info("IRSI Gateway is running");
		};
	}
}
