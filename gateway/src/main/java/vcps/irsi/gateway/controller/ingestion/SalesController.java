package vcps.irsi.gateway.controller.ingestion;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import vcps.irsi.gateway.dto.payload.SalesIngestionRequest;
import vcps.irsi.gateway.dto.payload.StatusMessageResponse;
import vcps.irsi.gateway.services.ingestion.ISalesIngestionHandler;

/**
 * TODO: doc
 */
@RestController
@RequestMapping("/api/ingestion")
public class SalesController {

    private final ISalesIngestionHandler handler;

    public SalesController(ISalesIngestionHandler handler) {
        this.handler = handler;
    }

    /**
     * TODO: doc
     */
    @PostMapping("/sales")
    public ResponseEntity<StatusMessageResponse> handle(@Valid @RequestBody SalesIngestionRequest request) {
        handler.accept(request);
        return ResponseEntity.ok(StatusMessageResponse.success());
    }
}
