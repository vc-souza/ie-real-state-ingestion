package vcps.irsi.archiver.listeners;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import vcps.irsi.archiver.dto.messages.PSRASaleMessage;

/**
 * TODO: doc
 */
@Slf4j
@Service
public class PSRASalesListener {

    /**
     * TODO: doc
     */
    @KafkaListener(topics = "${suppliers.psra.sales.sales-topic}")
    public void listen(
            @Payload PSRASaleMessage message,
            ConsumerRecord<?, ?> record,
            Acknowledgment ack) {

        log.info("Processing message {} (p={},o={},t={})", message, record.partition(), record.offset(),
                record.timestamp());

        // TODO: archive -> ack
        // TODO: error -> nack? ack anyway?
    }
}
