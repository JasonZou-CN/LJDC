package com.ljdc.pojo;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA
 * User:邹旭
 * Date:2017/2/22 0022
 * Time:下午 7:57
 * Desc:略
 */
@DatabaseTable(tableName = "learn_lib")
public class LearnLib implements Serializable {
    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, columnName = "learnLibId")
    public UUID learnLibId;

    @DatabaseField(columnName = "graspLevel")
    public int graspLevel;

    @DatabaseField(columnName = "updateTime", dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss")
    public Date updateTime;

    /**
     * 属性状态
     * 0：新增
     * 1：修改
     * -1：删除
     * 9:已经同步
     */
    @DatabaseField(columnName = "statusModify", defaultValue = "0")
    public int statusModify;
    /**
     * 上次和服务器同步时间,同步锚点
     */
    @DatabaseField(columnName = "anchor", dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss", defaultValue = "1970-01-01 08:00:00")
    public Date anchor;

    @DatabaseField(foreign = true, columnName = "libId")
    public Lib lib;

    @DatabaseField(foreign = true, columnName = "userId")
    public UserServer user;

    //JSON解析时用
    public int userId;
    public int libId;
    public String oldId;//需要删除的不同步的记录ID

    public LearnLib(UUID learnLibId) {
        this.learnLibId = learnLibId;
    }

    public LearnLib() {
    }
}