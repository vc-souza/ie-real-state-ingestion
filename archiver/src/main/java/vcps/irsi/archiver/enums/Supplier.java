package vcps.irsi.archiver.enums;

import lombok.Getter;

/**
 * TODO: doc
 */
@Getter
public enum Supplier {
    PSRA(1);

    private int mySQLId;

    private Supplier(int mySQLId) {
        this.mySQLId = mySQLId;
    }
}
