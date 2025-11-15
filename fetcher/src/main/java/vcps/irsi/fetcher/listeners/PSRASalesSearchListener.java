package vcps.irsi.fetcher.listeners;

import java.time.Duration;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import vcps.irsi.fetcher.dto.messages.PSRASalesSearchRequest;
import vcps.irsi.fetcher.services.fetching.IFetcher;
import vcps.irsi.fetcher.services.throttling.ThrottledRequestException;

/**
 * TODO: doc
 */
@Slf4j
@Service
public class PSRASalesSearchListener {
    private final IFetcher<PSRASalesSearchRequest> fetcher;

    public PSRASalesSearchListener(IFetcher<PSRASalesSearchRequest> fetcher) {
        this.fetcher = fetcher;
    }

    @KafkaListener(topics = "${suppliers.psra.sales.search-topic}")
    public void listen(
            @Payload PSRASalesSearchRequest request,
            ConsumerRecord<?, ?> record,
            Acknowledgment ack) {

        log.info("Processing message {} (p={},o={},t={})", request, record.partition(), record.offset(),
                record.timestamp());

        try {
            boolean fetched = fetcher.fetch(request);

            log.info(fetched ? "Request processed" : "Skipping: request already processed");

            ack.acknowledge();

        } catch (ThrottledRequestException e) {
            log.error("Giving up for now, operation was throttled", e);
            ack.nack(Duration.ofHours(1));

        } catch (InterruptedException e) {
            log.error("Message handling interrupted", e);
            Thread.currentThread().interrupt();
        }
    }
}
