package vcps.irsi.archiver.archivers.mysql;

import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import vcps.irsi.archiver.archivers.IArchiver;
import vcps.irsi.archiver.dto.messages.PSRASaleMessage;

/**
 * TODO: doc
 */
@Slf4j
@Service
public class MySQLSaleArchiver implements IArchiver<PSRASaleMessage> {

    @Override
    public void archive(PSRASaleMessage target) {
        // TODO: remove
        log.info("Archiving {}", target.toString());
        // TODO: impl
    }
}
