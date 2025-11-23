package vcps.irsi.archiver.archivers.mysql;

import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import vcps.irsi.archiver.archivers.IArchiver;
import vcps.irsi.archiver.dto.messages.PSRAPropertyMessage;

/**
 * TODO: doc
 */
@Slf4j
@Service
public class MySQLPropertyArchiver implements IArchiver<PSRAPropertyMessage> {

    @Override
    public void archive(PSRAPropertyMessage target) {
        // TODO: remove
        log.info("Archiving {}", target.toString());
        // TODO: impl
    }
}
