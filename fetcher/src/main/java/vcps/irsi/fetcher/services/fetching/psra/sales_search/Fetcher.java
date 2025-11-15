package vcps.irsi.fetcher.services.fetching.psra.sales_search;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import vcps.irsi.fetcher.dto.messages.PSRASalesSearchMessage;
import vcps.irsi.fetcher.services.fetching.FailedFetchingException;
import vcps.irsi.fetcher.services.fetching.IFetcher;
import vcps.irsi.fetcher.services.throttling.IThrottler;
import vcps.irsi.fetcher.services.tracking.ITracker;

/**
 * TODO: doc
 */
@Slf4j
@Service
public class Fetcher implements IFetcher<PSRASalesSearchMessage> {
    private static final String SEARCH_TEMPLATE = "%1$s/Downloads/PPR-%3$d-%4$02d-%2$s.csv/$FILE/PPR-%3$d-%4$02d-%2$s.csv";
    private static final String BASE_URL = "https://propertypriceregister.ie/website/npsra/ppr/npsra-ppr.nsf";
    private static final String USER_AGENT = "Mozilla/5.0 (Linux; Android 6.0)";

    private final IFetcher.Context context;
    private final HttpClient httpClient;

    public Fetcher(IThrottler throttler, ITracker tracker, HttpClient httpClient) {
        this.context = IFetcher.Context.builder().throttler(throttler).throttling(new IThrottler.Options())
                .tracker(tracker).tracking(new ITracker.Options()).build();
        this.httpClient = httpClient;
    }

    @Override
    public IFetcher.Context getFetchingContext() {
        return context;
    }

    @Override
    public void handle(PSRASalesSearchMessage request) {
        try {
            Parser.parse(
                    search(request),
                    sale -> {
                        // TODO: impl
                    },
                    property -> {
                        // TODO: impl
                    });
        } catch (Exception e) {
            throw new FailedFetchingException(request, e);
        }
    }

    /**
     * TODO: doc
     */
    private URI searchURI(PSRASalesSearchMessage request) {
        return URI.create(SEARCH_TEMPLATE.formatted(BASE_URL, request.county(), request.year(), request.month()));
    }

    /**
     * TODO: doc
     */
    private Stream<String> search(PSRASalesSearchMessage request) throws IOException, InterruptedException {
        var httpRequest = HttpRequest.newBuilder(searchURI(request)).header("user-agent", USER_AGENT).build();
        var httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString(Charset.forName("CP1252")));

        // skip CSV header
        return httpResponse.body().lines().skip(1);
    }
}
