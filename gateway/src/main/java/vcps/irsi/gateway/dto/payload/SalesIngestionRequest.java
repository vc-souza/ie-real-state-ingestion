package vcps.irsi.gateway.dto.payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO: doc
 */
class IngestionWindowValidator
        implements ConstraintValidator<ValidIngestionWindow, SalesIngestionRequest> {
    @Override
    public boolean isValid(SalesIngestionRequest request, ConstraintValidatorContext context) {
        if (request.getStart() == null || request.getEnd() == null) {
            return false;
        }

        if (request.getStart().isEqual(request.getEnd())) {
            return true;
        }

        if (request.getStart().isBefore(request.getEnd())) {
            return true;
        }

        return false;
    }
}

/**
 * TODO: doc
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IngestionWindowValidator.class)
@interface ValidIngestionWindow {
    String message() default "Ingestion start must precede or be equal to ingestion end";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

/**
 * TODO: doc
 */
@Data
@NoArgsConstructor
@ValidIngestionWindow
public class SalesIngestionRequest {
    private static final String COUNTY_REGEX = """
            Carlow\
            |Cavan\
            |Clare\
            |Cork\
            |Donegal\
            |Dublin\
            |Galway\
            |Kerry\
            |Kildare\
            |Kilkenny\
            |Laois\
            |Leitrim\
            |Limerick\
            |Longford\
            |Louth\
            |Mayo\
            |Meath\
            |Monaghan\
            |Offaly\
            |Roscommon\
            |Sligo\
            |Tipperary\
            |Waterford\
            |Westmeath\
            |Wexford\
            |Wicklow\
            """;

    @NotNull
    @NotEmpty
    private List<@NotNull @Pattern(regexp = COUNTY_REGEX) String> counties;

    @NotNull
    @PastOrPresent
    private LocalDate start;

    @NotNull
    @PastOrPresent
    private LocalDate end;
}
