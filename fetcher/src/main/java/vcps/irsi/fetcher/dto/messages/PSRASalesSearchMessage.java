package vcps.irsi.fetcher.dto.messages;

import vcps.irsi.fetcher.enums.Supplier;
import vcps.irsi.fetcher.services.throttling.IThrottleable;
import vcps.irsi.fetcher.services.tracking.ITrackable;

/**
 * TODO: doc
 */
public record PSRASalesSearchMessage(String county, int year, int month) implements ITrackable, IThrottleable {
    @Override
    public String getThrottlingIdentifier() {
        return Supplier.PSRA.name().toUpperCase();
    }

    @Override
    public String getTrackingIdentifier() {
        return String.format("%S-SALES-SEARCH-%S-%d-%d", Supplier.PSRA.name(), county(), year(), month());
    }
}
