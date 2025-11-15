package vcps.irsi.fetcher.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.JsonMessageConverter;

/**
 * TODO: doc
 */
@Configuration
public class KafkaConfig {
    /**
     * TODO: doc
     */
    @Bean
    JsonMessageConverter jsonMessageConverter() {
        return new JsonMessageConverter();
    }
}
