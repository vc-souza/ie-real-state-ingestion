package vcps.irsi.fetcher.dto.messages;

import vcps.irsi.fetcher.enums.Supplier;
import vcps.irsi.fetcher.services.throttling.IThrottleable;
import vcps.irsi.fetcher.services.tracking.ITrackable;

/**
 * TODO: doc
 */
public record PSRASalesSearchRequest(String county, int year, int month) implements ITrackable, IThrottleable {
    private static final String TRACKING_TEMPLATE = "%S-SALES-SEARCH-%S-%d-%d";

    @Override
    public String getThrottlingIdentifier() {
        return Supplier.PSRA.name().toUpperCase();
    }

    @Override
    public String getTrackingIdentifier() {
        return TRACKING_TEMPLATE.formatted(Supplier.PSRA.name(), county(), year(), month());
    }
}
