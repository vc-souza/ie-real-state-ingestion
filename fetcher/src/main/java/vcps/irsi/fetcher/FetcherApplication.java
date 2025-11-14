package vcps.irsi.fetcher;

import java.util.stream.IntStream;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import lombok.extern.slf4j.Slf4j;
import vcps.irsi.fetcher.dto.messages.PSRASalesDetailsMessage;
import vcps.irsi.fetcher.dto.messages.PSRASalesSearchMessage;
import vcps.irsi.fetcher.services.fetching.psra.PSRASalesDetailsFetcher;
import vcps.irsi.fetcher.services.fetching.psra.PSRASalesSearchFetcher;
import vcps.irsi.fetcher.services.throttling.ThrottledRequestException;

/**
 * TODO: doc
 */
@Slf4j
@SpringBootApplication
@ConfigurationPropertiesScan
public class FetcherApplication {

	// TODO: remove
	private final PSRASalesSearchFetcher salesSearchFetcher;
	private final PSRASalesDetailsFetcher salesDetailsFetcher;

	// TODO: remove
	public FetcherApplication(PSRASalesSearchFetcher salesSearchFetcher, PSRASalesDetailsFetcher salesDetailsFetcher) {
		this.salesSearchFetcher = salesSearchFetcher;
		this.salesDetailsFetcher = salesDetailsFetcher;
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
			IntStream.rangeClosed(1, 5).forEach(i -> {
				try {
					salesSearchFetcher.fetch(message);
				} catch (ThrottledRequestException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});

			// // TODO: remove
			// var message = new PSRASalesDetailsMessage("D11V6K6", "UNID-18CE1B61AD9C2D2680258D4000602679");
			// IntStream.rangeClosed(1, 5).forEach(i -> {
			// 	try {
			// 		salesDetailsFetcher.fetch(message);
			// 	} catch (ThrottledRequestException e) {
			// 		// TODO Auto-generated catch block
			// 		e.printStackTrace();
			// 	} catch (InterruptedException e) {
			// 		// TODO Auto-generated catch block
			// 		e.printStackTrace();
			// 	}
			// });
		};
	}
}
