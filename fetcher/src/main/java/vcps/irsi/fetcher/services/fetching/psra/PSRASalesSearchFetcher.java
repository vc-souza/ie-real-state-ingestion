package vcps.irsi.fetcher.services.fetching.psra;

import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import vcps.irsi.fetcher.dto.messages.PSRASalesSearchMessage;
import vcps.irsi.fetcher.services.fetching.IFetcher;
import vcps.irsi.fetcher.services.throttling.IThrottler;
import vcps.irsi.fetcher.services.tracking.ITracker;

/**
 * TODO: doc
 */
@Slf4j
@Service
public class PSRASalesSearchFetcher implements IFetcher<PSRASalesSearchMessage> {
    private final IFetcher.Context context;

    public PSRASalesSearchFetcher(IThrottler throttler, ITracker tracker) {
        this.context = IFetcher.Context.builder().throttler(throttler).throttling(new IThrottler.Options())
                .tracker(tracker).tracking(new ITracker.Options()).build();
    }

    @Override
    public IFetcher.Context getFetchingContext() {
        return context;
    }

    @Override
    public void handle(PSRASalesSearchMessage request) {
        // TODO: impl
        log.info("Fetcher got {}!!!", request);
    }
}
