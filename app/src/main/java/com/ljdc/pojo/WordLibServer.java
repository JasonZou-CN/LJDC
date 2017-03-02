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
@DatabaseTable(tableName = "word_lib")
public class WordLibServer {
    @DatabaseField(columnName = "wordId",generatedId = true)
    public int wordId;
    
    @DatabaseField(columnName = "word")
    public String word;
    
    @DatabaseField(columnName = "pronStrEn")
    public String pronStrEn;
    
    @DatabaseField(columnName = "pronUrlEn")
    public String pronUrlEn;
    
    @DatabaseField(columnName = "pronStrUs")
    public String pronStrUs;

    @DatabaseField(columnName = "pronUrlUs")
    public String pronUrlUs;

    @DatabaseField(columnName = "pos1")
    public String pos1;

    @DatabaseField(columnName = "acceptation1")
    public String acceptation1;

    @DatabaseField(columnName = "pos2")
    public String pos2;

    @DatabaseField(columnName = "acceptation2")
    public String acceptation2;

    @DatabaseField(columnName = "pos3")
    public String pos3;

    @DatabaseField(columnName = "acceptation3")
    public String acceptation3;

    @DatabaseField(columnName = "pos4")
    public String pos4;

    @DatabaseField(columnName = "acceptation4")
    public String acceptation4;

    @DatabaseField(columnName = "sentEn1")
    public String sentEn1;

    @DatabaseField(columnName = "sentTrans1")
    public String sentTrans1;

    @DatabaseField(columnName = "sentEn2")
    public String sentEn2;

    @DatabaseField(columnName = "sentTrans2")
    public String sentTrans2;

    @DatabaseField(columnName = "sentEn3")
    public String sentEn3;

    @DatabaseField(columnName = "sentTrans3")
    public String sentTrans3;

    @DatabaseField(columnName = "sentEn4")
    public String sentEn4;

    @DatabaseField(columnName = "sentTrans4")
    public String sentTrans4;

    @ForeignCollectionField(eager = true)
    public ForeignCollection<Lib1EnglishGrand4CoreServer> lib1;

    @ForeignCollectionField(eager = true)
    public ForeignCollection<Lib2MiddleSchoolServer> lib2;

}
