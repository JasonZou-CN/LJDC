package com.ljdc.pojo;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA
 * User:邹旭
 * Date:2017/2/22 0022
 * Time:下午 7:57
 * Desc:略
 */
@DatabaseTable(tableName = "learn_lib2")
public class LearnLib2Server implements Serializable {

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, columnName = "learnLib2Id")
    public UUID learnLib2Id;


    @DatabaseField(columnName = "graspLevel")
    public int graspLevel;

    @DatabaseField(columnName = "updataTime", dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss")
    public Date updataTime;

    @DatabaseField(columnName = "statusModify", defaultValue = "0")
    public int statusModify;

    @DatabaseField(columnName = "anchor", dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss", defaultValue = "1970-01-01 08:00:00")
    public Date anchor;

    @DatabaseField(foreign = true, columnName = "lib2Id")
    public Lib2MiddleSchoolServer lib2;

    @DatabaseField(foreign = true, columnName = "userId")
    public UserServer user;

    //    不进行持久化的属性
    public int userId;
    public int lib2Id;
    public String oldId;//需要删除的不同步的记录ID

    public LearnLib2Server(UUID learnLib2Id) {
        this.learnLib2Id = learnLib2Id;
    }

    public LearnLib2Server() {
    }
}
