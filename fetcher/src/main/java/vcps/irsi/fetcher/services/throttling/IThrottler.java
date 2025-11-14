package vcps.irsi.fetcher.services.throttling;

import java.time.Duration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * TODO: doc
 */
public interface IThrottler {
    /**
     * TODO: config
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Options {
        private Duration duration = Duration.ofSeconds(60);
    }

    /**
     * TODO: doc
     */
    boolean isAllowed(IThrottleable throttleable, Options options);
}
