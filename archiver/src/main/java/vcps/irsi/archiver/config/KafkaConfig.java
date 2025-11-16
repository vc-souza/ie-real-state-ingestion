package vcps.irsi.archiver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;

/**
 * TODO: doc
 */
@Configuration
public class KafkaConfig {
    /**
     * TODO: doc
     */
    @Bean
    RecordMessageConverter jsonMessageConverter() {
        return new JsonMessageConverter();
    }
}
