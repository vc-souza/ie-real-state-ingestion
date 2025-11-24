package vcps.irsi.fetcher.services.fetching.psra.sales_search;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

import vcps.irsi.fetcher.dto.messages.PSRAPropertyMessage;
import vcps.irsi.fetcher.dto.messages.PSRASaleMessage;

/**
 * TODO: doc
 */
@Slf4j
public class Parser {
    // TODO: doc
    public static record Result(PSRASaleMessage sale, PSRAPropertyMessage property) {
    }

    private static final DateTimeFormatter SLASHES_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final Pattern FIELD_PATTERN = Pattern.compile("\"(.*?)\"");

    /**
     * TODO: doc
     */
    public static Stream<Result> parse(Stream<String> rows) {
        return rows.map(Parser::toFields).filter(Parser::isValid)
                .map(fields -> new Result(parseSale(fields), parseProperty(fields)));
    }

    /**
     * TODO: doc
     */
    private static boolean isValid(List<String> fields) {
        return (fields != null
                && fields.size() == 9
                && !eircode(fields).isBlank()
                && !isNotFullMarketPrice(fields)
                && !isVATExclusive(fields));
    }

    /**
     * TODO: doc
     */
    private static LocalDate dateOfSale(List<String> fields) {
        return LocalDate.parse(fields.get(0), SLASHES_FORMATTER);
    }

    /**
     * TODO: doc
     */
    private static String eircode(List<String> fields) {
        return fields.get(3);
    }

    /**
     * TODO: doc
     */
    private static BigDecimal salePrice(List<String> fields) {
        return new BigDecimal(fields.get(4).replaceAll(",|€|\s", ""));
    }

    /**
     * TODO: doc
     */
    private static String county(List<String> fields) {
        return fields.get(2);
    }

    /**
     * TODO: doc
     */
    private static String address(List<String> fields) {
        return fields.get(1);
    }

    /**
     * TODO: doc
     */
    private static String propertyDescription(List<String> fields) {
        return fields.get(7);
    }

    /**
     * TODO: doc
     */
    private static boolean isNotFullMarketPrice(List<String> fields) {
        return fields.get(5).equalsIgnoreCase("YES");
    }

    /**
     * TODO: doc
     */
    private static boolean isVATExclusive(List<String> fields) {
        return fields.get(6).equalsIgnoreCase("YES");
    }

    /**
     * TODO: doc
     */
    private static PSRASaleMessage parseSale(List<String> fields) {
        try {
            return new PSRASaleMessage(
                    eircode(fields),
                    county(fields),
                    dateOfSale(fields),
                    salePrice(fields));
        } catch (Exception e) {
            log.error("Unable to parse sale from fields: %s".formatted(fields.toString()), e);
        }

        return null;
    }

    /**
     * TODO: doc
     */
    private static PSRAPropertyMessage parseProperty(List<String> fields) {
        try {
            return new PSRAPropertyMessage(
                    eircode(fields),
                    county(fields),
                    address(fields),
                    propertyDescription(fields));
        } catch (Exception e) {
            log.error("Unable to parse property from fields: %s".formatted(fields.toString()), e);
        }

        return null;
    }

    /**
     * TODO: doc
     */
    private static List<String> toFields(String row) {
        return FIELD_PATTERN.matcher(row).results().map(m -> m.group(1)).toList();
    }
}
