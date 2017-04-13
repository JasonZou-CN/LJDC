package com.ljdc.pojo;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "lib")
public class Lib {

    @DatabaseField(columnName = "libId", generatedId = true, allowGeneratedIdInsert = true)
    public int libId;

    @DatabaseField(foreign = true, columnName = "wordId")
    public WordLibServer wordLib;

    @DatabaseField(columnName = "libName",canBeNull = false)
    public String  libName;//所属词库


    @DatabaseField(columnName = "status", defaultValue = "0")
    public int status;//同步状态

    @DatabaseField(columnName = "anchor", dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss", defaultValue = "1970-01-01 08:00:00")
    public Date anchor;//同步锚点


    //不参与持久化
    public Date modified;
    public int wordId; //传递外键ID

    public Lib(int libId) {
        this.libId = libId;
    }

    public Lib() {
    }
}
