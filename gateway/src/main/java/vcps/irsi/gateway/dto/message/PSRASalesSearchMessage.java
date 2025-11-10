package vcps.irsi.gateway.dto.message;

import java.util.Objects;

/**
 * TODO: doc
 */
public record PSRASalesSearchMessage(String county, int year, int month) {
    public static PSRASalesSearchMessage of(String county, int year, int month) {
        return new PSRASalesSearchMessage(county, year, month);
    }

    /**
     * TODO: doc (avoiding hot partitions)
     */
    public String key() {
        return String.format("%x", Objects.hash(county, year, month));
    }
}
