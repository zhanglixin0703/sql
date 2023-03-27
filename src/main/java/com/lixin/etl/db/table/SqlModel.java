package com.lixin.etl.db.table;

/**
 * Description: 单列字段的描述类
 * Copyright:   Copyright (c)2023
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2023-03-16 16:26:14
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2023-03-16     张李鑫                     1.0         1.0 Version
 */
public class SqlModel {

    /**
     * 字段
     */
    private String column;

    /**
     * 描述
     */
    private String comment;
    /**
     * 字段类型
     */
    private Byte type;

    /**
     * 字段长度
     */
    private Integer length;

    private boolean isNull;

    /**
     * 是否是主键
     */
    private boolean isPrimaryKey;
    /**
     * 是否需要子增
     */
    private boolean isAuto;


    public SqlModel() {
    }

    public SqlModel(String column, String comment, Byte type, Integer length, boolean isNull, boolean isPrimaryKey, boolean isAuto) {
        this.column = column;
        this.comment = comment;
        this.type = type;
        this.length = length;
        this.isNull = isNull;
        this.isPrimaryKey = isPrimaryKey;
        this.isAuto = isAuto;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public boolean isNull() {
        return isNull;
    }

    public void setNull(boolean aNull) {
        isNull = aNull;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    /**
     * 如果设置了主键必须设置主键不是null
     *
     * @param primaryKey 是否是主键
     */
    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
        this.isNull = false;
    }

    public boolean isAuto() {
        return isAuto;
    }

    /**
     * 必须是主键才可以设置自动递增
     *
     * @param auto
     */
    public void setAuto(boolean auto) {
        isAuto = isPrimaryKey && auto;
    }
}
