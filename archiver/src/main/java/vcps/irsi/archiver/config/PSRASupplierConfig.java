package vcps.irsi.archiver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * TODO: doc
 */
@Getter
@Setter
@Validated
@ConfigurationProperties("suppliers.psra")
public class PSRASupplierConfig {

    /**
     * TODO: doc
     */
    @Getter
    @Setter
    public static class Sales {
        /**
         * TODO: doc
         */
        @NotBlank
        private String salesTopic;

        /**
         * TODO: doc
         */
        @NotBlank
        private String propertiesTopic;
    }

    /**
     * TODO: doc
     */
    @Valid
    private final Sales sales = new Sales();
}
