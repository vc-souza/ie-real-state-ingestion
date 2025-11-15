package vcps.irsi.fetcher.services.throttling.redis;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import vcps.irsi.fetcher.services.throttling.IThrottleable;
import vcps.irsi.fetcher.services.throttling.IThrottler;

/**
 * TODO: doc
 */
@Service
public class RedisThrottler implements IThrottler {
    private static final String KEY_TEMPLATE = "IRSI-THROTTLING-%s";

    private final StringRedisTemplate redisTemplate;

    public RedisThrottler(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * TODO: doc
     */
    private String key(IThrottleable throttleable) {
        var id = throttleable.getThrottlingIdentifier();

        if (id == null) {
            return null;
        }

        return KEY_TEMPLATE.formatted(id);
    }

    @SuppressWarnings("null")
    @Override
    public boolean isAllowed(IThrottleable throttleable, IThrottler.Options options) {
        var throttlingKey = key(throttleable);

        if (throttlingKey == null) {
            return true;
        }

        return Optional
                .ofNullable(redisTemplate.opsForValue().setIfAbsent(throttlingKey, Instant.now().toString(),
                        options.getDuration()))
                .orElse(false);
    }
}
