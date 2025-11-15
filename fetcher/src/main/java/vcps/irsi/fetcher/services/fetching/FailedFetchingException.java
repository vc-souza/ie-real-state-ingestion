package vcps.irsi.fetcher.services.fetching;

/**
 * TODO: doc
 */
public class FailedFetchingException extends RuntimeException {
    public FailedFetchingException(Object request, Throwable cause) {
        super("Unable to fulfill request {}".formatted(request.toString()), cause);
    }
}
