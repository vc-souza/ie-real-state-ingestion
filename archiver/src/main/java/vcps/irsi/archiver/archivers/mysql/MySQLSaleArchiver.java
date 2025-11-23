package vcps.irsi.archiver.archivers.mysql;

import org.springframework.stereotype.Repository;
import lombok.extern.slf4j.Slf4j;

import vcps.irsi.archiver.archivers.IArchiver;
import vcps.irsi.archiver.dto.messages.PSRASaleMessage;

/**
 * TODO: doc
 */
@Slf4j
@Repository
public class MySQLSaleArchiver implements IArchiver<PSRASaleMessage> {

    @Override
    public void archive(PSRASaleMessage target) {
        // TODO: remove
        log.info("Archiving {}", target.toString());
        // TODO: impl
    }
}
