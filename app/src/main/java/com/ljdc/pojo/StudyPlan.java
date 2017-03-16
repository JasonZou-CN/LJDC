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
 * Date:2017/3/9 0009
 * Time:下午 11:41
 * Desc:略
 */
@DatabaseTable(tableName = "study_plan")
public class StudyPlan implements Serializable {

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, columnName = "planId")
    public UUID planId;

    @DatabaseField(foreign = true,columnName = "userId")
    public UserServer user;

    @DatabaseField(columnName = "currentLib")//当前学习词库:table,使用Map来结合
    public String currentLib;

    @DatabaseField(columnName = "totalNum")//词汇总数
    public int totalNum;

    @DatabaseField(columnName = "leftNum")//剩余词汇数
    public int leftNum;

    @DatabaseField(columnName = "planOfDay")//每日学习计划
    public int planOfDay;

    @DatabaseField(columnName = "doOfDay")//每日已经学习词汇
    public int doOfDay;

    @DatabaseField(columnName = "finishDate", dataType = DataType.DATE_STRING, format = "yyyy-MM-dd")//完成日期
    public Date finishDate;

    @DatabaseField(columnName = "anchor", dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss", defaultValue = "1970-01-01 08:00:00")
    public Date anchor;//同步锚点

    @DatabaseField(columnName = "status",defaultValue = "0")
    public int status;//同步状态

    //不参与持久化
    public int userId;//网络传输
    public String oldId;//需要删除的不同步的记录ID

    public StudyPlan() {}

    public StudyPlan(UUID planId) {
        this.planId = planId;
    }
}
