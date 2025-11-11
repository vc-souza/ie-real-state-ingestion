package vcps.irsi.gateway.config.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import vcps.irsi.gateway.dto.kafka.PSRASalesSearchMessage;

/**
 * TODO: doc
 */
@Configuration
public class KafkaProducerConfig {
    /**
     * TODO: doc
     */
    @Bean
    ProducerFactory<String, PSRASalesSearchMessage> psraSalesSearchProducerFactory(KafkaProperties kafkaProperties) {
        var props = kafkaProperties.buildProducerProperties();

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(props);
    }

    /**
     * TODO: doc
     */
    @Bean
    KafkaTemplate<String, PSRASalesSearchMessage> psraSalesSearchKafkaTemplate(
            ProducerFactory<String, PSRASalesSearchMessage> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
