package com.ljdc.pojo;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA
 * User:邹旭
 * Date:2017/2/22 0022
 * Time:下午 7:57
 * Desc:略
 */
@DatabaseTable(tableName = "word_development")
public class WordDevelopmentServer {
    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true,columnName = "wordDevId")
    public UUID wordDevId;

    @DatabaseField(columnName = "wordsIncreaseNum")
    public Integer wordsIncreaseNum;

    @DatabaseField(columnName = "wordIncreaseDate",dataType = DataType.DATE_STRING,format = "yyyy-MM-dd")
    public Date wordIncreaseDate;

    @DatabaseField(columnName = "graspLevel")
    public Integer graspLevel;

    @DatabaseField(columnName = "statusModify", defaultValue = "0")
    public int statusModify;

    @DatabaseField(columnName = "anchor",dataType = DataType.DATE_STRING,format = "yyyy-MM-dd HH:mm:ss",defaultValue = "1970-01-01 08:00:00")
    public Date anchor;

    @DatabaseField(columnName = "userId", foreign = true, foreignAutoRefresh = true)
    public UserServer user;

    //不参与持久化
    public int userId;
    public String oldId;//需要删除的不同步的记录ID

    public WordDevelopmentServer(UUID wordDevId) {
        this.wordDevId = wordDevId;
    }

    public WordDevelopmentServer() {
    }
}
