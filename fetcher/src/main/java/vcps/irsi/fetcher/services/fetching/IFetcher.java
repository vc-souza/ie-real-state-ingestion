package vcps.irsi.fetcher.services.fetching;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import vcps.irsi.fetcher.services.throttling.IThrottleable;
import vcps.irsi.fetcher.services.throttling.IThrottler;
import vcps.irsi.fetcher.services.throttling.ThrottledRequestException;
import vcps.irsi.fetcher.services.tracking.ITrackable;
import vcps.irsi.fetcher.services.tracking.ITracker;

/**
 * TODO: doc
 */
public interface IFetcher<T extends ITrackable & IThrottleable> {
    /**
     * TODO: config
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Options {
        private static final Random random = new Random();

        private double minJitter = .05;
        private double maxJitter = .15;
        private int maxRetries = 10;

        /**
         * TODO: doc
         */
        public long secondsToNextAttempt(IThrottler.Options throttlingOptions) {
            double multiplier = 1.0 + random.nextDouble(minJitter, maxJitter);
            double seconds = multiplier * throttlingOptions.getDuration().toSeconds();
            return Math.round(seconds);
        }
    }

    /**
     * TODO: doc
     */
    Options getFetcherOptions();

    /**
     * TODO: doc
     */
    IThrottler.Options getThrottlingOptions();

    /**
     * TODO: doc
     */
    IThrottler getThrottler();

    /**
     * TODO: doc
     */
    ITracker.Options getTrackingOptions();

    /**
     * TODO: doc
     */
    ITracker getTracker();

    /**
     * TODO: doc
     */
    Logger getLogger();

    /**
     * TODO: doc
     */
    void fetch(T request);

    /**
     * TODO: doc
     */
    default void handle(T request)
            throws ThrottledRequestException, InterruptedException {

        if (getTracker().isTracked(request)) {
            getLogger().info("Request {} already fulfilled, skipping.", request);
            return;
        }

        boolean allowed = false;

        for (int i = 0; i < getFetcherOptions().getMaxRetries(); i++) {
            if (getThrottler().isAllowed(request, getThrottlingOptions())) {
                allowed = true;
                break;
            }

            var sleepDuration = getFetcherOptions().secondsToNextAttempt(getThrottlingOptions());

            getLogger().debug("Request {} has been throttled, sleeping for {} seconds...", request, sleepDuration);

            TimeUnit.SECONDS.sleep(sleepDuration);
        }

        if (!allowed) {
            throw new ThrottledRequestException(request);
        }

        fetch(request);

        getTracker().track(request, getTrackingOptions());
    }
}
