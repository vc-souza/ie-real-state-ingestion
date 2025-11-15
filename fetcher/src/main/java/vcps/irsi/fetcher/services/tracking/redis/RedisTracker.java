package vcps.irsi.fetcher.services.tracking.redis;

import java.time.Instant;
import java.util.Optional;

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
        var id = trackable.getTrackingIdentifier();

        if (id == null) {
            return null;
        }

        return KEY_TEMPLATE.formatted(id);
    }

    @Override
    public boolean isTracked(ITrackable trackable) {
        return Optional.ofNullable(key(trackable)).map(redisTemplate::hasKey).orElse(false);
    }

    @SuppressWarnings("null")
    @Override
    public void track(ITrackable trackable, ITracker.Options options) {
        var trackingKey = key(trackable);

        if (trackingKey == null) {
            return;
        }

        redisTemplate.opsForValue().set(trackingKey, Instant.now().toString(), options.getDuration());
    }
}
