package vcps.irsi.fetcher.services.tracking;

/**
 * TODO: doc
 */
public interface ITracker {
    /**
     * TODO: doc
     */
    boolean isTracked(ITrackable trackable);

    /**
     * TODO: doc
     */
    void track(ITrackable trackable);
}
