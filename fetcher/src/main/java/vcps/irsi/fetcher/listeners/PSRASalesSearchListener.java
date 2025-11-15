package vcps.irsi.fetcher.listeners;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import vcps.irsi.fetcher.config.PSRASupplierConfig;
import vcps.irsi.fetcher.dto.messages.PSRASalesSearchRequest;
import vcps.irsi.fetcher.services.fetching.IFetcher;

/**
 * TODO: doc
 */
@Slf4j
@Service
public class PSRASalesSearchListener {
    private final IFetcher<PSRASalesSearchRequest> fetcher;
    public final PSRASupplierConfig supplierConfig;

    public PSRASalesSearchListener(IFetcher<PSRASalesSearchRequest> fetcher, PSRASupplierConfig supplierConfig) {
        this.fetcher = fetcher;
        this.supplierConfig = supplierConfig;
    }

    @KafkaListener(topics = "#{ __listener.supplierConfig.getSales().getSearchTopic() }")
    public void listen(@Payload PSRASalesSearchRequest request, ConsumerRecord<?, ?> record, Acknowledgment ack) {
        log.info("Processing message {} (p={},o={},t={}) <headers={}>", request, record.partition(),
                record.offset(), record.timestamp(), record.headers().toString());

        // TODO: impl
    }
}
