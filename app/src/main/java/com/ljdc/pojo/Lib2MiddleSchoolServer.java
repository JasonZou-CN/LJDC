package com.ljdc.pojo;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created with IntelliJ IDEA
 * User:邹旭
 * Date:2017/2/22 0022
 * Time:下午 7:57
 * Desc:略
 */
@DatabaseTable(tableName = "lib2")
public class Lib2MiddleSchoolServer {
    @DatabaseField(columnName = "lib2Id",generatedId = true)
    public int lib2Id;
    
    @ForeignCollectionField(eager = false)
    public ForeignCollection<LearnLib2Server> learnLib2;
    
    @DatabaseField(foreign = true,columnName = "wordId")
    public WordLibServer wordLib;
}
