package vcps.irsi.fetcher.services.throttling;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
        private double minJitter = .05;
        private double maxJitter = .15;
        private int maxRetries = 10;
    }

    static final Random RANDOM = new Random();

    /**
     * TODO: doc
     */
    boolean isAllowed(IThrottleable throttleable, Options options);

    /**
     * TODO: doc
     */
    private long secondsToNextAttempt(Options options) {
        double multiplier = 1.0 + RANDOM.nextDouble(options.minJitter, options.maxJitter);
        double seconds = multiplier * options.duration.toSeconds();
        return Math.round(seconds);
    }

    /**
     * TODO: doc
     */
    default void throttle(Runnable runnable, IThrottleable throttleable, Options options)
            throws InterruptedException, ThrottledRequestException {

        boolean allowed = false;

        for (int i = 0; i < options.getMaxRetries(); i++) {
            if (isAllowed(throttleable, options)) {
                allowed = true;
                break;
            }

            TimeUnit.SECONDS.sleep(secondsToNextAttempt(options));
        }

        if (!allowed) {
            throw new ThrottledRequestException(throttleable);
        }

        runnable.run();
    }
}
