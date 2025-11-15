package vcps.irsi.fetcher.dto.messages;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * TODO: doc
 */
public record PSRASaleMessage(String eircode, @JsonFormat(shape = JsonFormat.Shape.STRING) LocalDate date,
        @JsonFormat(shape = JsonFormat.Shape.STRING) BigDecimal price) {
}
