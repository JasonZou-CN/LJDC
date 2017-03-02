package com.ljdc.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * User:邹旭
 * Date:2017/2/22 0022
 * Time:下午 7:57
 * Desc:略
 */
@DatabaseTable(tableName = "learn_lib2")
public class LearnLib2Server {

@DatabaseField(generatedId = true,columnName = "learnLib2Id")
    private int learnLib2Id;


    @DatabaseField(columnName = "graspLevel")
    private int graspLevel;

    @DatabaseField(columnName = "updataTime")
    private Date updataTime;

    @DatabaseField(columnName = "statusModify")
    public int statusModify;

    @DatabaseField(columnName = "anchor")
    public Date anchor;

    @DatabaseField(foreign = true,columnName = "lib2Id")
    private Lib2MiddleSchoolServer lib2MiddleSchool;

    @DatabaseField(foreign = true,columnName = "userId")
    private UserServer user;
}
