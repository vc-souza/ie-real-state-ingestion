package vcps.irsi.fetcher.services.fetching.psra;

import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import vcps.irsi.fetcher.dto.messages.PSRASalesDetailsMessage;
import vcps.irsi.fetcher.services.fetching.IFetcher;
import vcps.irsi.fetcher.services.throttling.IThrottler;
import vcps.irsi.fetcher.services.tracking.ITracker;

@Slf4j
@Service
public class PSRASalesDetailsFetcher extends PSRAFetcher implements IFetcher<PSRASalesDetailsMessage> {
    public PSRASalesDetailsFetcher(IThrottler throttler, ITracker tracker) {
        super(throttler, tracker, log);
    }

    @Override
    public void doFetch(PSRASalesDetailsMessage request) {
        // TODO: impl
        log.info("Fetcher got {}!!!", request);
    }
}
