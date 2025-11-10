package vcps.irsi.gateway.services.ingestion;

import java.time.Period;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import vcps.irsi.gateway.dto.message.PSRASalesSearchMessage;
import vcps.irsi.gateway.dto.payload.SalesIngestionRequest;

/**
 * TODO: doc
 */
@Service
public class PSRASalesIngestionHandler implements ISalesIngestionHandler {
    // TODO: set up kafka producer

    @Override
    public void accept(SalesIngestionRequest request) {
        var messages = generateMessages(request);

        // TODO: do your thing
        System.out.println(request);
        System.out.println(messages.toList());
    }

    /**
     * TODO: doc
     */
    private Stream<PSRASalesSearchMessage> generateMessages(SalesIngestionRequest request) {
        var start = request.getStart().withDayOfMonth(1);
        var end = request.getEnd().withDayOfMonth(1).plusMonths(1);

        return start
                .datesUntil(end, Period.ofMonths(1))
                .flatMap(date -> request.getCounties().stream()
                        .map(county -> PSRASalesSearchMessage.of(county, date.getYear(), date.getMonthValue())));
    }
}
