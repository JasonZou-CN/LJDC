package com.ljdc.pojo;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Desc:词库信息
 */
@DatabaseTable(tableName = "libs")
public class Libs implements Serializable {

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, columnName = "libsId")
    public int libsId;

    @DatabaseField(columnName = "totalNum")//词汇总数
    public int totalNum;

    @DatabaseField(columnName = "libName")
    public String libName;

    @DatabaseField(columnName = "tableName")
    public String tableName;

    @DatabaseField(columnName = "anchor", dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss", defaultValue = "1970-01-01 08:00:00")
    public Date anchor;//同步锚点

    @DatabaseField(columnName = "status", defaultValue = "0")
    public int status;//同步状态
}
