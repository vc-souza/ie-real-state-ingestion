package vcps.irsi.gateway.dto.kafka;

/**
 * TODO: doc
 */
public record PSRASalesSearchMessage(String county, int year, int month) {
    public static PSRASalesSearchMessage of(String county, int year, int month) {
        return new PSRASalesSearchMessage(county, year, month);
    }
}
