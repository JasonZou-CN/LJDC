package com.ljdc.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA
 * User:邹旭
 * Date:2017/2/22 0022
 * Time:下午 7:57
 * Desc:略
 */
@DatabaseTable(tableName = "word_development")
public class WordDevelopmentServer {
    @DatabaseField(generatedId = true,columnName = "word_dev_id")
    public int wordDevId;

    @DatabaseField(columnName = "words_increase_num")
    public Integer wordsIncreaseNum;

    @DatabaseField(columnName = "word_increase_date")
    public Date wordIncreaseDate;

    @DatabaseField(columnName = "grasp_level")
    public Integer graspLevel;

    @DatabaseField(columnName = "status_modify")
    public int statusModify;

    @DatabaseField(columnName = "anchor")
    public Timestamp anchor;

    @DatabaseField(columnName = "user_id",foreign = true,foreignAutoRefresh = true)
    public UserServer user;

}
