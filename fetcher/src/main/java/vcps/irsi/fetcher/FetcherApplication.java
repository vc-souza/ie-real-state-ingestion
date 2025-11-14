package vcps.irsi.fetcher;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import lombok.extern.slf4j.Slf4j;
import vcps.irsi.fetcher.dto.messages.PSRASalesSearchMessage;
import vcps.irsi.fetcher.services.fetching.psra.PSRASalesSearchFetcher;

/**
 * TODO: doc
 */
@Slf4j
@SpringBootApplication
@ConfigurationPropertiesScan
public class FetcherApplication {

	// TODO: remove
	private final PSRASalesSearchFetcher salesSearchFetcher;

	// TODO: remove
	public FetcherApplication(PSRASalesSearchFetcher salesSearchFetcher) {
		this.salesSearchFetcher = salesSearchFetcher;
	}

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

			// TODO: remove
			var message = new PSRASalesSearchMessage("Dublin", 2025, 1);
			salesSearchFetcher.handle(message);
		};
	}
}
