package vcps.irsi.archiver.archivers.mysql;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import vcps.irsi.archiver.archivers.IArchiver;
import vcps.irsi.archiver.dto.messages.PSRAPropertyMessage;

/**
 * TODO: doc
 */
@Slf4j
@Service
public class MySQLPropertyArchiver implements IArchiver<PSRAPropertyMessage> {

    private static final String INSERT_PROPERTY = """
            INSERT INTO `dim_properties`
                (
                    `eircode`,
                    `county`,
                    `address`,
                    `type`
                )
            VALUES (?, ?, ?, ? ) AS new
            ON DUPLICATE KEY UPDATE
                `county` = new.`county`,
                `address` = new.`address`,
                `type` = new.`type`
            """;

    private final JdbcTemplate jdbcTemplate;

    public MySQLPropertyArchiver(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    @Override
    public void archive(PSRAPropertyMessage target) {
        jdbcTemplate.update(INSERT_PROPERTY, ps -> {
            ps.setString(1, target.eircode());
            ps.setString(2, target.county());
            ps.setString(3, target.address());
            ps.setString(4, target.type());
        });
    }
}
