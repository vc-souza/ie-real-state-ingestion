package vcps.irsi.fetcher;

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
public class FetcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(FetcherApplication.class, args);
	}

	/**
	 * TODO: doc
	 */
	@Bean
	ApplicationRunner runner() {
		return args -> {
			log.info("IRSI Fetcher is running");
		};
	}
}
