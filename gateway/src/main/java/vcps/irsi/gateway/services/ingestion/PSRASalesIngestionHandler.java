package vcps.irsi.gateway.services.ingestion;

import java.time.Period;
import java.util.Properties;
import java.util.stream.Stream;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import vcps.irsi.gateway.dto.message.PSRASalesSearchMessage;
import vcps.irsi.gateway.dto.payload.SalesIngestionRequest;
import vcps.irsi.gateway.utils.JsonKafkaSerializer;

/**
 * TODO: doc
 */
@Service
public class PSRASalesIngestionHandler implements ISalesIngestionHandler {
    private final static String VERSION = "1";

    private final KafkaProducer<String, PSRASalesSearchMessage> producer;
    private final String producerBootstrapServers;
    private final String producerTopic;
    private final int producerMaxRetries;

    public PSRASalesIngestionHandler(
            @Value("${psra.kafka.producer.bootstrap.servers}") String producerBootstrapServers,
            @Value("${psra.kafka.producer.topic}") String producerTopic,
            @Value("${psra.kafka.producer.retries}") int producerMaxRetries) {
        this.producerBootstrapServers = producerBootstrapServers;
        this.producerTopic = producerTopic;
        this.producerMaxRetries = producerMaxRetries;
        this.producer = new KafkaProducer<>(producerProperties());
    }

    /**
     * TODO: doc
     */
    @PostConstruct
    void init() {
        // TODO: log properly
    }

    /**
     * TODO: doc
     */
    @PreDestroy
    void shutdown() {
        try {
            producer.flush();
        } catch (Exception e) {
            // TODO: log properly
            System.err.println("unable to flush producer");
        }

        try {
            producer.close();
        } catch (Exception e) {
            // TODO: log properly
            System.err.println("unable to close producer");
        }
    }

    @Override
    public void accept(SalesIngestionRequest request) {
        generateMessages(request).map(this::toRecord).forEach(
            r -> {
                System.out.println(r);
            }
        );
        // TODO: send records
    }

    /**
     * TODO: doc
     */
    private Properties producerProperties() {
        Properties props = new Properties();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.producerBootstrapServers);
        props.put(ProducerConfig.RETRIES_CONFIG, this.producerMaxRetries);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonKafkaSerializer.class.getName());

        return props;
    }

    /**
     * TODO: doc
     */
    private Stream<PSRASalesSearchMessage> generateMessages(SalesIngestionRequest request) {
        var start = request.getStart().withDayOfMonth(1);
        var end = request.getEnd().withDayOfMonth(1).plusMonths(1);

        return start
                .datesUntil(end, Period.ofMonths(1))
                .flatMap(date -> request.getCounties().stream()
                        .map(county -> PSRASalesSearchMessage.of(county, date.getYear(), date.getMonthValue())));
    }

    /**
     * TODO: doc
     */
    private ProducerRecord<String, PSRASalesSearchMessage> toRecord(PSRASalesSearchMessage message) {
        ProducerRecord<String, PSRASalesSearchMessage> record = new ProducerRecord<>(producerTopic, message.key(),
                message);

        record.headers().add("GatewayVersion", VERSION.getBytes());

        return record;
    }
}
