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
	private final PSRASalesSearchFetcher sales;

	// TODO: remove
	public FetcherApplication(PSRASalesSearchFetcher sales) {
		this.sales = sales;
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

			var request = new PSRASalesSearchMessage("Dublin", 2025, 1);
			boolean fetched = sales.fetch(request);
			log.info("fetched? {}", fetched);
		};
	}
}
