package vcps.irsi.fetcher.services.fetching.psra;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import vcps.irsi.fetcher.dto.messages.PSRASalesSearchMessage;
import vcps.irsi.fetcher.services.fetching.IFetcher;
import vcps.irsi.fetcher.services.throttling.IThrottler;
import vcps.irsi.fetcher.services.tracking.ITracker;

/**
 * TODO: doc
 */
@Slf4j
@Component
public class PSRASalesSearchFetcher implements IFetcher<PSRASalesSearchMessage> {
    private static final IFetcher.Options FETCHER_OPTIONS = new IFetcher.Options();
    private static final IThrottler.Options THROTTLER_OPTIONS = new IThrottler.Options();
    private static final ITracker.Options TRACKING_OPTIONS = new ITracker.Options();

    private final IThrottler throttler;
    private final ITracker tracker;

    public PSRASalesSearchFetcher(IThrottler throttler, ITracker tracker) {
        this.throttler = throttler;
        this.tracker = tracker;
    }

    @Override
    public IFetcher.Options getFetcherOptions() {
        return FETCHER_OPTIONS;
    }

    @Override
    public IThrottler.Options getThrottlingOptions() {
        return THROTTLER_OPTIONS;
    }

    @Override
    public IThrottler getThrottler() {
        return throttler;
    }

    @Override
    public ITracker.Options getTrackingOptions() {
        return TRACKING_OPTIONS;
    }

    @Override
    public ITracker getTracker() {
        return tracker;
    }

    @Override
    public Logger getLogger() {
        return log;
    }

    @Override
    public void fetch(PSRASalesSearchMessage request) {
        // TODO: impl
        log.info("Fetcher got {}!!!", request);
    }
}
