package com.lixin.etl.db.provider;

import com.lixin.etl.db.table.SqlModel;
import com.lixin.etl.db.table.Table;
import org.apache.ibatis.jdbc.SQL;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Description:
 * Copyright:   Copyright (c)2023
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2023-03-23 17:32:55
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2023-03-23     张李鑫                     1.0         1.0 Version
 */
public class TableOrFileProvider implements SqlProvider{
    /**
     * 构造List sql insert语句
     * 由原生的 statement.executeUpdate(sql);来执行这句sql
     * 场景:一般用于 table || excel 的场景无法建立字段映射
     *
     * @param values
     * @return
     */
    public List<SQL> builderInsertSql(List<List<String>> values, Table table) {
        List<SQL> resSql = new ArrayList<>();
        //收集arr[i]所产生的所有sql返回
        values.forEach(val -> resSql.add(builder(val, table)));
        return resSql;
    }

    /**
     * 通过行数据构造当前行的insert语句
     *
     * @param val
     * @return
     */
    public SQL builder(List<String> val, Table table) {
        //前缀构造
        SQL sql = sqlPrefix(table);
        String sqlStr = val.toString();
        sql.INTO_VALUES(sqlStr.substring(1, sqlStr.length() - 1));
        return sql;
    }

    /**
     * 转换sql成为String 产生的String 由原生jdbc执行
     * inset into （a,b,c) values(a,v,c);
     * inset into （a,b,c) values(a1,v1,c1);
     *
     * @param lists
     * @return
     */
    public String sqlList2Str(List<SQL> lists) {
        StringBuilder sb = new StringBuilder();
        lists.forEach((sql) -> sb.append(sql).append(ending + lineFeed));
        return sb.toString();
    }

    /**
     * 转换数组成为List
     *
     * @param values 数组转换成list
     * @return
     */
    public List<List<String>> array2List(List<String[]> values) {
        List<List<String>> res = new ArrayList<>();
        values.forEach((value) -> res.add(List.of(value)));
        return res;
    }


    /**
     * 构造前缀
     */
    public SQL sqlPrefix(Table table) {
        SQL sql = new SQL();
        sql.INSERT_INTO(table.getTableName());
        List<SqlModel> models = table.getModels();
        models.forEach((model) -> sql.INTO_COLUMNS(model.getColumn()));
        return sql;
    }


}
