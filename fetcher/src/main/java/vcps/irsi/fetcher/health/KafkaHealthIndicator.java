package vcps.irsi.fetcher.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Component;

/**
 * TODO: doc
 */
@Component
public class KafkaHealthIndicator implements HealthIndicator {
    private final KafkaAdmin admin;

    public KafkaHealthIndicator(KafkaAdmin admin) {
        this.admin = admin;
    }

    @Override
    public Health health() {
        try {
            admin.describeTopics();
            return Health.up().build();
        } catch (Exception e) {
            return Health.down(e).build();
        }
    }
}
