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
    @DatabaseField(columnName = "lib2Id",generatedId = true,allowGeneratedIdInsert = true)
    public int lib2Id;
    
    @ForeignCollectionField(eager = false)
    public ForeignCollection<LearnLib2Server> learnLib2;
    
    @DatabaseField(foreign = true,columnName = "wordId")
    public WordLibServer wordLib;

    public int wordId;//暂存初始化得到的WordId，和WordLibServer绑定，存入数据库

    public Lib2MiddleSchoolServer(int lib2Id) {
        this.lib2Id = lib2Id;
    }

    public Lib2MiddleSchoolServer() {//ORM框架默认调用次构造方法
    }
}
