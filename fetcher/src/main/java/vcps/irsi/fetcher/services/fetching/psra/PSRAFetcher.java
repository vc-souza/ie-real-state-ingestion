package vcps.irsi.fetcher.services.fetching.psra;

import org.slf4j.Logger;

import vcps.irsi.fetcher.services.fetching.IFetcher;
import vcps.irsi.fetcher.services.throttling.IThrottler;
import vcps.irsi.fetcher.services.tracking.ITracker;

/**
 * TODO: doc
 */
public abstract class PSRAFetcher {
    // TODO: from config with defaults? no defaults in the class? validation as well?
    private static final IFetcher.Options FETCHER_OPTIONS = new IFetcher.Options();
    private static final IThrottler.Options THROTTLER_OPTIONS = new IThrottler.Options();
    private static final ITracker.Options TRACKING_OPTIONS = new ITracker.Options();

    private final IThrottler throttler;
    private final ITracker tracker;
    private final Logger log;

    public PSRAFetcher(IThrottler throttler, ITracker tracker, Logger log) {
        this.throttler = throttler;
        this.tracker = tracker;
        this.log = log;
    }

    public IFetcher.Options getFetcherOptions() {
        return FETCHER_OPTIONS;
    }

    public IThrottler.Options getThrottlingOptions() {
        return THROTTLER_OPTIONS;
    }

    public IThrottler getThrottler() {
        return throttler;
    }

    public ITracker.Options getTrackingOptions() {
        return TRACKING_OPTIONS;
    }

    public ITracker getTracker() {
        return tracker;
    }

    public Logger getLogger() {
        return log;
    }
}
