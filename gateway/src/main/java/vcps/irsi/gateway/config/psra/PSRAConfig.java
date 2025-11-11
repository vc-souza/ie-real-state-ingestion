package vcps.irsi.gateway.config.psra;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * TODO: doc
 */
@Getter
@Setter
@ConfigurationProperties("suppliers.psra")
public class PSRAConfig {

    /**
     * TODO: doc
     */
    @Getter
    @Setter
    public static class Sales {
        /**
         * TODO: doc
         */
        private String producerTopic;
    }

    /**
     * TODO: doc
     */
    private final Sales sales = new Sales();
}
