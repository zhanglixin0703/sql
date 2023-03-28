package com.lixin.etl.db.table;


import com.lixin.etl.db.model.ColumnStatus;
import com.lixin.etl.db.model.MysqlColumn;
import io.micrometer.common.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Description:
 * Copyright:   Copyright (c)2023
 * Company:     sci
 *
 * @author: 张李鑫
 * @version: 1.0
 * Create at:   2023-03-16 16:38:53
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * ------------------------------------------------------------------
 * 2023-03-16     张李鑫                     1.0         1.0 Version
 */
public class MysqlTable extends Table {

    private final String format = " `%s` ";
    private final String lineFeed = ",\n";
    private final String timestampDefault = " DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ";

    private final String primaryKeyStr = "  PRIMARY KEY (`%s`)";

    /**
     * mysqlColumnMap {@link MysqlColumn}
     */


    public MysqlTable(List<SqlModel> models, String tableName, String tableDoc) {
        super(models, tableName, tableDoc);
    }

    public MysqlTable(List<SqlModel> models, String tableName) {
        super(models, tableName);
    }

    /**
     * 获取创建的sql语句
     *
     * @return
     */
    @Override
    public String getCreateTableSql() {
        if (this.getModels().isEmpty()) {
            throw new RuntimeException("models isEmpty");
        }

        StringBuilder sql = new StringBuilder();
        //前缀
        sql.append(String.format("CREATE TABLE `%s` (\n", this.getTableName()));

        SqlModel primaryKey = this.getPrimaryKey();
        //行数据拼接
        for (SqlModel model : this.getModels()) {
            sql.append(buildColumnDefinition(model));
        }
        //如果有主键拼接主键字符串，没有的话删除最后一个逗号
        if (primaryKey != null) {
            sql.append(String.format(primaryKeyStr, getPrimaryKey().getColumn()));
        } else {
            sql.delete(sql.length() - lineFeed.length(), sql.length() - 1);
        }
        //后缀
        sql.append("\n").append(builderEnding());
        return sql.toString();
    }


    /**
     * 构造行字段
     *
     * @param model {@link SqlModel}
     */
    private StringBuilder buildColumnDefinition(SqlModel model) {
        StringBuilder sql = new StringBuilder();
        sql.append(String.format(format, model.getColumn()));
        //获取当前模型的实际类型
        MysqlColumn mysqlColumn = model.getType();

        sql.append(mysqlColumn.isHasSuffix() ? String.format(mysqlColumn.getSuffix(), model.getLength()) : mysqlColumn.getSuffix());

        sql.append(model.isNull() ? ColumnStatus.ISNULL.getDescription() : ColumnStatus.NOTNULL.getDescription());
        //设置主键
        if (model.getPrimaryKey().isPrimaryKey()) {
            sql.append(" AUTO_INCREMENT");
        }
        //设置时间默认值
        if (model.getType().getValue()==MysqlColumn.TIMESTAMP.getValue()) {
            sql.append(timestampDefault);
        }
        return sql.append(lineFeed);
    }

    /**
     * 拼接comment
     *
     * @return
     */
    public String builderCommentLine(SqlModel model) {
        if (StringUtils.isBlank(model.getComment())) {
            return "";
        }
        MysqlColumn mysqlColumn = MysqlColumn.enumMap.get(model.getType().getValue());
        //是否有后缀 (?) :有的话需要替换长度 部分字段不需要设置长度
        String suffix = mysqlColumn.isHasSuffix() ? String.format(mysqlColumn.getSuffix(), model.getLength()) : mysqlColumn.getSuffix();
        //设置TIMESTAMP的默认值 如果是这个类型需要追加
        suffix = mysqlColumn == MysqlColumn.TIMESTAMP ? suffix + timestampDefault : suffix;
        return "alter table " + this.getTableName() + " modify  " + String.format(format, model.getColumn()) + " " + suffix + " comment '" + model.getComment() + "';";
    }


    /**
     * 更新column字段
     *
     * @param model
     * @return
     */
    @Override
    public String getUpdateColumnDocSql(SqlModel model) {
        return builderCommentLine(model);
    }


    @Override
    public String updateOrInsertTableCommentSql(String tableName, String tableDesc) {
        return "alter table " + tableName + " comment '" + tableDesc + "';";
    }

    /**
     * 构造结束语句 ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
     */
    public StringBuilder builderEnding() {
        StringBuilder sql = new StringBuilder();
        sql.append(") ENGINE=").append(this.getEngine()).append(this.getCharacter());
        return sql;
    }

    /**
     * 行备注的构造
     *
     * @return
     */
    @Override
    public String getCommentColumnSql() {
        StringBuilder sql = new StringBuilder();
        for (SqlModel model : this.getModels()) {
            sql.append(builderCommentLine(model)).append("\n");
        }
        return sql.toString();
    }


    /**
     * 表备注的构造
     *
     * @return
     */
    @Override
    public String getCommentTableSql() {
        return updateOrInsertTableCommentSql(this.getTableName(), this.getTableDesc());
    }

}
