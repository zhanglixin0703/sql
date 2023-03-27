package com.lixin.etl;

import com.lixin.etl.db.constructor.SqlManager;
import com.lixin.etl.db.model.DbType;
import com.lixin.etl.db.model.MysqlColumn;
import com.lixin.etl.db.table.SqlModel;
import com.lixin.etl.db.util.CreateUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Copyright:   Copyright (c)2023
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2023-03-24 13:44:49
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2023-03-24     张李鑫                     1.0         1.0 Version
 */
public class SqlManagerTest {

    //MysqlColumn
    public SqlManager getSqlmanager() {
        SqlModel sqlModel = new SqlModel("id", "id", MysqlColumn.VARCHAR.getValue(), 20, false, true, true);
        List<SqlModel> sqlModels = new ArrayList<>();
        sqlModels.add(sqlModel);
        for (int i = 0; i < 10; i++) {
            SqlModel model = new SqlModel("INTEGER" + i, "INTEGER" + i, MysqlColumn.INTEGER.getValue(), 20, i % 2 == 0, false, false);
            sqlModels.add(model);
        }
        for (int i = 0; i < 10; i++) {
            SqlModel model = new SqlModel("TEXT" + i, "TEXT" + i, MysqlColumn.TEXT.getValue(), 0, i % 2 == 0, false, false);
            sqlModels.add(model);
        }
        for (int i = 0; i < 10; i++) {
            SqlModel model = new SqlModel("VARCHAR" + i, "VARCHAR" + i, MysqlColumn.VARCHAR.getValue(), 20, i % 2 == 0, false, false);
            sqlModels.add(model);
        }
        for (int i = 0; i < 10; i++) {
            SqlModel model = new SqlModel("TIMESTAMP" + i, "TIMESTAMP" + i, MysqlColumn.TIMESTAMP.getValue(), 0, i % 2 == 0, false, false);
            sqlModels.add(model);
        }
        return new SqlManager(CreateUtils.createTable(DbType.MYSQL, sqlModels, "tableNameNoAuto", "测试表1111"));
    }

    /**
     *
     */
    @Test
    public void getCreateSql() {
        long l = System.currentTimeMillis();
        SqlManager sqlmanager = getSqlmanager();
        String createTableSql = sqlmanager.getCreateTableSql();
        System.out.println(createTableSql);
        System.out.println(System.currentTimeMillis() - l);
    }


    @Test
    public void getCommentSql() {
        long l = System.currentTimeMillis();
        SqlManager sqlmanager = getSqlmanager();
        String createTableSql = sqlmanager.getCommentSql();
        System.out.println(createTableSql);
        System.out.println(System.currentTimeMillis() - l);
    }

    @Test
    public void getCreateTableAndCommentSql() {
        long l = System.currentTimeMillis();
        String createTableSql = getSqlmanager().getCreateTableAndCommentSql();
        System.out.println(createTableSql);
        System.out.println(System.currentTimeMillis() - l);
    }

}
