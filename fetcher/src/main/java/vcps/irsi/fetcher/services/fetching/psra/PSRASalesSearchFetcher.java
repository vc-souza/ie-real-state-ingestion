package vcps.irsi.fetcher.services.fetching.psra;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

import vcps.irsi.fetcher.dto.messages.PSRAPropertyMessage;
import vcps.irsi.fetcher.dto.messages.PSRASaleMessage;
import vcps.irsi.fetcher.dto.messages.PSRASalesSearchMessage;
import vcps.irsi.fetcher.services.fetching.IFetcher;
import vcps.irsi.fetcher.services.throttling.IThrottler;
import vcps.irsi.fetcher.services.tracking.ITracker;

/**
 * TODO: doc
 */
@Slf4j
@Service
public class PSRASalesSearchFetcher implements IFetcher<PSRASalesSearchMessage> {
    /**
     * TODO
     */
    private static enum Field {
        DATE_OF_SALE,
        ADDRESS,
        COUNTY,
        EIRCODE,
        PRICE_EUR,
        NOT_FULL_MARKET_PRICE,
        VAT_EXCLUSIVE,
        DESCRIPTION_OF_PROPERTY,
        PROPERTY_SIZE_DESCRIPTION;

        /**
         * TODO: doc
         */
        public String pick(List<String> fields) {
            return fields.get(this.ordinal());
        }
    }

    private static final String BASE_API_URL = "https://propertypriceregister.ie/website/npsra/ppr/npsra-ppr.nsf";
    private static final String SEARCH_URL_TEMPLATE = "%1$s/Downloads/PPR-%3$d-%4$02d-%2$s.csv/$FILE/PPR-%3$d-%4$02d-%2$s.csv";
    private static final DateTimeFormatter DATE_FORMAT_SLASHES = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final Pattern SEARCH_RESULT_FIELD_PATTERN = Pattern.compile("\"(.*?)\"");

    private final IFetcher.Context context;
    private HttpClient httpClient;

    public PSRASalesSearchFetcher(IThrottler throttler, ITracker tracker) {
        this.context = IFetcher.Context.builder().throttler(throttler).throttling(new IThrottler.Options())
                .tracker(tracker).tracking(new ITracker.Options()).build();
    }

    /**
     * TODO: doc
     */
    @PostConstruct
    void init() {
        try {
            httpClient = buildInsecureHttpClient();
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * TODO: doc
     */
    @PreDestroy
    void shutdown() {
        if (httpClient != null) {
            httpClient.close();
        }
    }

    /**
     * TODO: MAKE SECURE!
     */
    private HttpClient buildInsecureHttpClient() throws NoSuchAlgorithmException, KeyManagementException {
        var sslContext = SSLContext.getInstance("TLS");

        sslContext.init(null, new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType)
                            throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType)
                            throws CertificateException {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                }
        }, null);

        return HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).sslContext(sslContext).build();
    }

    /**
     * TODO: doc
     */
    private URI buildSearchURI(PSRASalesSearchMessage request) {
        return URI
                .create(SEARCH_URL_TEMPLATE.formatted(BASE_API_URL, request.county(), request.year(), request.month()));
    }

    /**
     * TODO: doc
     */
    private Stream<String> search(PSRASalesSearchMessage request) throws IOException, InterruptedException {
        var httpRequest = HttpRequest.newBuilder(buildSearchURI(request))
                .header("user-agent", "Mozilla/5.0 (Linux; Android 6.0)").build();
        var bodyHandler = HttpResponse.BodyHandlers.ofString(Charset.forName("CP1252"));
        return httpClient.send(httpRequest, bodyHandler).body().lines().skip(1);
    }

    /**
     * TODO: doc
     */
    private Optional<PSRASaleMessage> parseSale(List<String> fields) {
        try {
            return Optional.of(
                    new PSRASaleMessage(
                            Field.EIRCODE.pick(fields),
                            LocalDate.parse(Field.DATE_OF_SALE.pick(fields), DATE_FORMAT_SLASHES),
                            new BigDecimal(Field.PRICE_EUR.pick(fields).replaceAll(",|€|\s", ""))));
        } catch (Exception e) {
            log.error("Unable to parse sale from fields: %s".formatted(fields.toString()), e);
        }

        return Optional.empty();
    }

    /**
     * TODO: doc
     */
    private Optional<PSRAPropertyMessage> parseProperty(List<String> fields) {
        try {
            return Optional.of(
                    new PSRAPropertyMessage(
                            Field.EIRCODE.pick(fields),
                            Field.COUNTY.pick(fields),
                            Field.ADDRESS.pick(fields),
                            Field.DESCRIPTION_OF_PROPERTY.pick(fields)));
        } catch (Exception e) {
            log.error("Unable to parse property from fields: %s".formatted(fields.toString()), e);
        }

        return Optional.empty();
    }

    @Override
    public IFetcher.Context getFetchingContext() {
        return context;
    }

    @Override
    public void handle(PSRASalesSearchMessage request) {
        try {
            search(request)
                    .map(line -> SEARCH_RESULT_FIELD_PATTERN.matcher(line).results().map(m -> m.group(1)).toList())
                    .filter(fields -> fields.size() == 9)
                    .filter(fields -> Field.NOT_FULL_MARKET_PRICE.pick(fields).toUpperCase().equals("NO")
                            && Field.VAT_EXCLUSIVE.pick(fields).toUpperCase().equals("NO"))
                    .forEach(
                            fields -> {
                                // parseSale(fields).ifPresent(sales::add);
                                // parseProperty(fields).ifPresent(properties::add);
                            });
        } catch (IOException e) {
            // TODO: handle
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO: handle
            e.printStackTrace();
        }

        // TODO: impl
    }
}
