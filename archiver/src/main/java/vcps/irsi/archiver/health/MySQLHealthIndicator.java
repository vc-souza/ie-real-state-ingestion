package vcps.irsi.archiver.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * TODO: doc
 */
@Component
public class MySQLHealthIndicator implements HealthIndicator {

    private final JdbcTemplate jdbcTemplate;

    public MySQLHealthIndicator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Health health() {
        try {
            jdbcTemplate.execute("SELECT 1");
            return Health.up().build();
        } catch (Exception e) {
            return Health.down(e).build();
        }
    }

}
