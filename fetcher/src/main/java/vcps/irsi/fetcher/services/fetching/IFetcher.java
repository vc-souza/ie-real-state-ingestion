package vcps.irsi.fetcher.services.fetching;

import lombok.Builder;

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
     * TODO: doc
     */
    @Builder
    static class Context {
        private IThrottler.Options throttling;
        private ITracker.Options tracking;
        private IThrottler throttler;
        private ITracker tracker;
    }

    /**
     * TODO: doc
     */
    Context getFetchingContext();

    /**
     * TODO: doc
     */
    void handle(T request);

    /**
     * TODO: doc
     */
    default boolean fetch(T request)
            throws ThrottledRequestException, InterruptedException {

        var ctx = getFetchingContext();

        if (ctx.tracker.isTracked(request)) {
            return false;
        }

        ctx.throttler.throttle(() -> handle(request), request, ctx.throttling);

        ctx.tracker.track(request, ctx.tracking);

        return true;
    }
}
