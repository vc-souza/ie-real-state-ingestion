package vcps.irsi.gateway.services.ingestion.psra;

import java.time.Period;
import java.util.stream.Stream;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import vcps.irsi.gateway.config.PSRASupplierConfig;
import vcps.irsi.gateway.dto.messages.PSRASalesSearchRequest;
import vcps.irsi.gateway.dto.payload.SalesIngestionRequest;
import vcps.irsi.gateway.services.ingestion.ISalesIngestionHandler;

/**
 * TODO: doc
 */
@Slf4j
@Service
public class PSRASalesIngestionHandler implements ISalesIngestionHandler {
    private final static String VERSION = "1.0.0";

    private final KafkaTemplate<String, PSRASalesSearchRequest> kafkaTemplate;
    private final PSRASupplierConfig supplierConfig;

    public PSRASalesIngestionHandler(
            KafkaTemplate<String, PSRASalesSearchRequest> kafkaTemplate,
            PSRASupplierConfig supplierConfig) {
        this.kafkaTemplate = kafkaTemplate;
        this.supplierConfig = supplierConfig;
    }

    /**
     * TODO: doc
     */
    private Stream<PSRASalesSearchRequest> messagesFor(SalesIngestionRequest request) {
        var start = request.getStart().withDayOfMonth(1);
        var end = request.getEnd().withDayOfMonth(1).plusMonths(1);

        return start
                .datesUntil(end, Period.ofMonths(1))
                .flatMap(date -> request.getCounties().stream()
                        .map(county -> new PSRASalesSearchRequest(county, date.getYear(), date.getMonthValue())));
    }

    /**
     * TODO: doc
     */
    private ProducerRecord<String, PSRASalesSearchRequest> toRecord(PSRASalesSearchRequest message) {
        var record = new ProducerRecord<>(
                supplierConfig.getSales().getSearchTopic(),
                message.county(),
                message);

        record.headers().add("GatewayVersion", VERSION.getBytes());

        return record;
    }

    @Override
    public void accept(SalesIngestionRequest request) {
        log.info("Generating PSRA messages for request: {}", request);

        messagesFor(request).map(this::toRecord).forEach(kafkaTemplate::send);
    }
}
