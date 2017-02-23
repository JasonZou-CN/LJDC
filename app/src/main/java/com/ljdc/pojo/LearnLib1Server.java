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
@DatabaseTable(tableName = "learn_lib1_server")
public class LearnLib1Server {

    @DatabaseField(generatedId = true,columnName = "learn_lib1_id")
    public int learnLib1Id;

    @DatabaseField(columnName = "grasp_level")
    public int graspLevel;

    @DatabaseField(columnName = "updata_time")
    public Timestamp updataTime;

    @DatabaseField(columnName = "modified")
    public Timestamp modified;

    @DatabaseField(foreign = true,columnName = "lib1_id")
    public Lib1EnglishGrand4CoreServer lib1EnglishGrand4CoreServer;

    @DatabaseField(foreign = true,columnName = "user_id")
    public UserServer userServer;

}