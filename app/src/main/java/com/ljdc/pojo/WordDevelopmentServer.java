package com.ljdc.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * User:邹旭
 * Date:2017/2/22 0022
 * Time:下午 7:57
 * Desc:略
 */
@DatabaseTable(tableName = "word_development")
public class WordDevelopmentServer {
    @DatabaseField(generatedId = true,columnName = "wordDevId")
    public int wordDevId;

    @DatabaseField(columnName = "wordsIncreaseNum")
    public Integer wordsIncreaseNum;

    @DatabaseField(columnName = "wordIncreaseDate")
    public Date wordIncreaseDate;

    @DatabaseField(columnName = "graspLevel")
    public Integer graspLevel;

    @DatabaseField(columnName = "statusModify",defaultValue = "0")
    public int statusModify;

    @DatabaseField(columnName = "anchor",defaultValue = "0000-00-00 17:42:14.000")
    public Date anchor;

    @DatabaseField(columnName = "userId",foreign = true,foreignAutoRefresh = true)
    public UserServer user;

}
