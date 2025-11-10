package vcps.irsi.gateway.services.ingestion;

import java.util.function.Consumer;

import vcps.irsi.gateway.dto.payload.SalesIngestionRequest;

// TODO: better name?
/**
 * TODO: doc
 */
@FunctionalInterface
public interface ISalesIngestionHandler extends Consumer<SalesIngestionRequest> {
}
