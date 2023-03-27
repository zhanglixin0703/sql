package com.lixin.etl.db.constructor;

import com.lixin.etl.db.model.ModeType;
import com.lixin.etl.db.provider.SqlProvider;
import com.lixin.etl.db.table.SqlModel;
import com.lixin.etl.db.table.Table;
import com.lixin.etl.db.util.CreateUtils;

/**
 * Description:
 * Copyright:   Copyright (c)2023
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2023-03-16 16:00:13
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2023-03-16     张李鑫                     1.0         1.0 Version
 */
public class SqlConstructor {

    /**
     * 换行符号
     */
    final String lineFeed = " \n";


    /**
     * 表结构
     */
    private Table table;
    /**
     * sql构造器
     */
    private SqlProvider sqlProvider;


    /**
     * 获取创建表语句
     *
     * @return
     */
    public String getCreateTableSql() {
        return this.table.getCreateTableSql();
    }

    /**
     * 创建备注语句
     *
     * @return
     */
    public String getCommentSql() {
        return this.table.getCommentColumnSql() + this.table.getCommentTableSql();
    }

    /**
     * 更新单个字段
     *
     * @return
     */
    public String getUpdateColumnDoc(SqlModel sqlModel) {
        return this.table.getUpdateColumnDocSql(sqlModel);
    }

    /**
     * 更新表备注
     *
     * @return
     */
    public String getUpdateTableDoc(String tableName, String tableDoc) {
        return this.table.updateOrInsertTableCommentSql(tableName, tableDoc);
    }

    /**
     * 获取建表语句以及备注语句
     *
     * @return
     */
    public String getCreateTableAndCommentSql() {
        return getCreateTableSql() + lineFeed + getCommentSql();
    }

    public SqlConstructor(Table table, ModeType modeType) {
        this.table = table;
        this.sqlProvider = CreateUtils.builderSqlProvider(modeType);
    }


    public SqlConstructor(Table table) {
        this.table = table;
        this.sqlProvider = CreateUtils.builderSqlProvider(ModeType.BEAN);
    }

    public SqlProvider getSqlProvider() {
        return sqlProvider;
    }

    public void setSqlProvider(SqlProvider sqlProvider) {
        this.sqlProvider = sqlProvider;
    }


    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

}
