package vcps.irsi.archiver.listeners;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import vcps.irsi.archiver.archivers.IArchiver;
import vcps.irsi.archiver.dto.messages.PSRASaleMessage;

/**
 * TODO: doc
 */
@Slf4j
@Service
public class PSRASalesListener {

    private final List<IArchiver<PSRASaleMessage>> archivers;

    public PSRASalesListener(List<IArchiver<PSRASaleMessage>> archivers) {
        this.archivers = archivers;
    }

    /**
     * TODO: doc
     */
    @KafkaListener(topics = "${suppliers.psra.sales.sales-topic}")
    public void listen(
            @Payload PSRASaleMessage message,
            ConsumerRecord<?, ?> record,
            Acknowledgment ack) {

        log.debug("Processing message {} (p={},o={},t={})", message, record.partition(), record.offset(),
                record.timestamp());

        // TODO: archive -> ack (try/catch for each archiver)
        archivers.forEach(a -> a.archive(message));
        // TODO: error -> nack? ack anyway?
    }
}
