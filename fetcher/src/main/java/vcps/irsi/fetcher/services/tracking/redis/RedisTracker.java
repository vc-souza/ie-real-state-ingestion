package vcps.irsi.fetcher.services.tracking.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import vcps.irsi.fetcher.services.tracking.ITrackable;
import vcps.irsi.fetcher.services.tracking.ITracker;

/**
 * TODO: doc
 */
@Service
public class RedisTracker implements ITracker {
    private static final String KEY_TEMPLATE = "IRSI-TRACKING-%s";

    private final StringRedisTemplate redisTemplate;

    public RedisTracker(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * TODO: doc
     */
    private String key(ITrackable trackable) {
        return KEY_TEMPLATE.formatted(trackable.getTrackingIdentifier());
    }

    @Override
    public boolean isTracked(ITrackable trackable) {
        // TODO: impl
        // TODO: remove
        System.out.println("KEY IS '%s'".formatted(key(trackable)));
        return false;
    }

    @Override
    public void track(ITrackable trackable) {
        // TODO: impl
        // TODO: remove
        System.out.println("KEY IS '%s'".formatted(key(trackable)));
    }
}
