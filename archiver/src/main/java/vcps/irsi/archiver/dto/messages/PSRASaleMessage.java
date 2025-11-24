package vcps.irsi.archiver.dto.messages;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * TODO: doc
 */
public record PSRASaleMessage(String eircode, String county,
                @JsonFormat(shape = JsonFormat.Shape.STRING) LocalDate date,
                @JsonFormat(shape = JsonFormat.Shape.STRING) BigDecimal price) {
}
