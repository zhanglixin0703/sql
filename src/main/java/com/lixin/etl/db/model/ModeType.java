package com.lixin.etl.db.model;

/**
 * Description:
 * Copyright:   Copyright (c)2023
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2023-03-16 15:57:40
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2023-03-16     张李鑫                     1.0         1.0 Version
 */
public enum ModeType {
    EXCEL("excel",(byte)0),
    TABLE("table",(byte)1),
    BEAN("table",(byte)2);

    private final String description;
    private final byte value;

    ModeType(String description, byte value) {
        this.description = description;
        this.value = value;
    }
    public String getDescription() {
        return description;
    }

    public byte getValue() {
        return value;
    }
}
