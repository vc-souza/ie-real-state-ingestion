package vcps.irsi.gateway.services.ingestion;

import java.time.Period;
import java.util.stream.Stream;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

import vcps.irsi.gateway.dto.message.PSRASalesSearchMessage;
import vcps.irsi.gateway.dto.payload.SalesIngestionRequest;

/**
 * TODO: doc
 */
@Slf4j
@Service
public class PSRASalesIngestionHandler implements ISalesIngestionHandler {
    private final static String VERSION = "0.0.1";

    private final KafkaTemplate<String, PSRASalesSearchMessage> kafkaTemplate;
    private final String producerTopic;

    public PSRASalesIngestionHandler(
        @Value("${psra.sales.kafka.producer.topic}") String producerTopic,
        KafkaTemplate<String, PSRASalesSearchMessage> kafkaTemplate
    ) {
        this.producerTopic = producerTopic;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * TODO: doc
     */
    @PostConstruct
    void init() {
        log.info("Initialized.");
    }

    /**
     * TODO: doc
     */
    @PreDestroy
    void shutdown() {
        log.info("Shutting down...");
        log.info("Graceful shutdown complete.");
    }

    @Override
    public void accept(SalesIngestionRequest request) {
        log.info("Generating PSRA messages for request: {}", request);

        generateMessages(request).map(this::toRecord).forEach(
                r -> {
                    log.info(r.toString());
                });
        // TODO: send records
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
        ProducerRecord<String, PSRASalesSearchMessage> record = new ProducerRecord<>(producerTopic, message);

        record.headers().add("GatewayVersion", VERSION.getBytes());

        return record;
    }
}
