package com.ljdc.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA
 * User:邹旭
 * Date:2017/2/22 0022
 * Time:下午 7:57
 * Desc:略
 */
@DatabaseTable(tableName = "learn_lib2")
public class LearnLib2Server {

@DatabaseField(generatedId = true,columnName = "learn_lib2_id")
    private int learnLib2Id;


    @DatabaseField(columnName = "grasp_level")
    private int graspLevel;

    @DatabaseField(columnName = "updata_time")
    private Timestamp updataTime;

    @DatabaseField(columnName = "status_modify")
    public int statusModify;

    @DatabaseField(columnName = "anchor")
    public Timestamp anchor;

    @DatabaseField(foreign = true,columnName = "lib2_id")
    private Lib2MiddleSchoolServer lib2MiddleSchool;

    @DatabaseField(foreign = true,columnName = "user_id")
    private UserServer user;
}
