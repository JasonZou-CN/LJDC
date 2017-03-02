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
@DatabaseTable(tableName = "learn_lib1")
public class LearnLib1Server {

    @DatabaseField(generatedId = true,columnName = "learnLib1Id")
    public int learnLib1Id;

    @DatabaseField(columnName = "graspLevel")
    public int graspLevel;

    @DatabaseField(columnName = "updataTime")
    public Date updataTime;

    @DatabaseField(columnName = "statusModify")
    public int statusModify;

    @DatabaseField(columnName = "anchor")
    public Date anchor;

    @DatabaseField(foreign = true,columnName = "lib1Id")
    public Lib1EnglishGrand4CoreServer lib1_word;

    @DatabaseField(foreign = true,columnName = "userId")
    public UserServer user;

}