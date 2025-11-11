package vcps.irsi.gateway.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import vcps.irsi.gateway.dto.message.PSRASalesSearchMessage;

/**
 * TODO: doc
 */
@Configuration
public class KafkaProducerConfig {
    /**
     * TODO: doc
     */
    @Bean
    ProducerFactory<String, PSRASalesSearchMessage> psraProducerFactory(
            @Value("${kafka.producer.bootstrap.servers}") String bootstrapServers,
            @Value("${kafka.producer.retries}") int maxRetries) {
        Map<String, Object> props = new HashMap<>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.RETRIES_CONFIG, maxRetries);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(props);
    }

    /**
     * TODO: doc
     */
    @Bean
    KafkaTemplate<String, PSRASalesSearchMessage> psraKafkaTemplate(
            ProducerFactory<String, PSRASalesSearchMessage> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
