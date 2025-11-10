package vcps.irsi.gateway.dto.payload;

/**
 * TODO: doc
 */
public record StatusMessageResponse(String status) {
    /**
     * TODO: doc
     */
    public static StatusMessageResponse success() {
        return new StatusMessageResponse("success");
    }
}
