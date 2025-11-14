package vcps.irsi.fetcher.services.throttling;

/**
 * TODO: doc
 */
public class ThrottledRequestException extends Exception {
    private static final String MESSAGE_TEMPLATE = "Request %s could not be fulfilled due to throttling on %s";

    public ThrottledRequestException(IThrottleable throttleable) {
        super(MESSAGE_TEMPLATE.formatted(throttleable, throttleable.getThrottlingIdentifier()));
    }
}
