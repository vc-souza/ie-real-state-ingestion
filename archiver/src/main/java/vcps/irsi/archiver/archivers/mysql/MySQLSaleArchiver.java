package vcps.irsi.archiver.archivers.mysql;

import java.time.format.DateTimeFormatter;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import vcps.irsi.archiver.archivers.IArchiver;
import vcps.irsi.archiver.dto.messages.PSRASaleMessage;
import vcps.irsi.archiver.enums.Supplier;

/**
 * TODO: doc
 */
@Slf4j
@Service
public class MySQLSaleArchiver implements IArchiver<PSRASaleMessage> {

    private static final String INSERT_SALE = """
            INSERT INTO `fact_property_sales`
                (
                    `property_eircode`,
                    `sold_at`,
                    `price`,
                    `currency_code`,
                    `supplier_id`
                )
            VALUES (?, ?, ?, ?, ?) AS new
            ON DUPLICATE KEY UPDATE
                `price` = new.`price`,
                `currency_code` = new.`currency_code`,
                `supplier_id` = new.`supplier_id`
            """;

    private static final String INSERT_MINIMAL_PROPERTY = """
            INSERT INTO `dim_properties`
                (
                    `eircode`,
                    `county`
                )
            VALUES (?, ? ) AS new
            ON DUPLICATE KEY UPDATE
                `county` = new.`county`
            """;

    private final JdbcTemplate jdbcTemplate;

    public MySQLSaleArchiver(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    @Override
    public void archive(PSRASaleMessage target) {
        jdbcTemplate.update(INSERT_MINIMAL_PROPERTY, ps -> {
            ps.setString(1, target.eircode());
            ps.setString(2, target.county());
        });

        jdbcTemplate.update(INSERT_SALE, ps -> {
            ps.setString(1, target.eircode());
            ps.setString(2, target.date().format(DateTimeFormatter.ISO_LOCAL_DATE));
            ps.setBigDecimal(3, target.price());
            ps.setString(4, "EUR");
            ps.setInt(5, Supplier.PSRA.getMySQLId());
        });
    }
}
