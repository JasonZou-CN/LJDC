package com.ljdc.pojo;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * User:邹旭
 * DateTime:2017/4/1 14:52
 * Desc:词汇量预估
 */
@DatabaseTable(tableName = "word_evalution")
public class WordEvaluation {

    @DatabaseField(columnName = "evaluationId", generatedId = true, allowGeneratedIdInsert = true)
    public int evaluationId;

    @DatabaseField(foreign = true, columnName = "wordId")
    public WordLibServer wordLib;

    @DatabaseField(columnName = "level", canBeNull = false)
    public int level;//预估词汇的等级（1-9）

    @DatabaseField(columnName = "status", defaultValue = "0")
    public int status;//同步状态

    @DatabaseField(columnName = "anchor", dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss", defaultValue = "1970-01-01 08:00:00")
    public Date anchor;//同步锚点

    //不参与持久化
    public Date modified;
    public int wordId;//传递外键ID

}
