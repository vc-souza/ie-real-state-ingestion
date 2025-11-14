package vcps.irsi.fetcher.services.throttling.redis;

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
        return KEY_TEMPLATE.formatted(throttleable.getThrottlingIdentifier());
    }

    @Override
    public boolean isAllowed(IThrottleable throttleable, Options options) {
        // TODO: impl!
        // TODO: remove
        System.out.println("KEY IS '%s'".formatted(key(throttleable)));
        return true;

    }
}
