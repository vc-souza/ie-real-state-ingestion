package vcps.irsi.fetcher.services.tracking;

import java.time.Duration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * TODO: doc
 */
public interface ITracker {
    /**
     * TODO: doc
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Options {
        private Duration duration = Duration.ofDays(90);
    }

    /**
     * TODO: doc
     */
    boolean isTracked(ITrackable trackable);

    /**
     * TODO: doc
     */
    void track(ITrackable trackable, Options options);
}
