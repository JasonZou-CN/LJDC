package com.ljdc.pojo;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * User:邹旭
 * Date:2017/2/22 0022
 * Time:下午 7:57
 * Desc:略
 */
@DatabaseTable(tableName = "word_lib")
public class WordLibServer implements Serializable{
    @DatabaseField(columnName = "wordId",generatedId = true,allowGeneratedIdInsert = true)
    public int wordId;

    @Override
    public String toString() {
        return "WordLibServer{" +
                "wordId=" + wordId +
                ", word='" + word + '\'' +
                ", pronStrEn='" + pronStrEn + '\'' +
                ", pronUrlEn='" + pronUrlEn + '\'' +
                ", pronStrUs='" + pronStrUs + '\'' +
                ", pronUrlUs='" + pronUrlUs + '\'' +
                ", pos1='" + pos1 + '\'' +
                ", acceptation1='" + acceptation1 + '\'' +
                ", pos2='" + pos2 + '\'' +
                ", acceptation2='" + acceptation2 + '\'' +
                ", pos3='" + pos3 + '\'' +
                ", acceptation3='" + acceptation3 + '\'' +
                ", pos4='" + pos4 + '\'' +
                ", acceptation4='" + acceptation4 + '\'' +
                ", sentEn1='" + sentEn1 + '\'' +
                ", sentTrans1='" + sentTrans1 + '\'' +
                ", sentEn2='" + sentEn2 + '\'' +
                ", sentTrans2='" + sentTrans2 + '\'' +
                ", sentEn3='" + sentEn3 + '\'' +
                ", sentTrans3='" + sentTrans3 + '\'' +
                ", sentEn4='" + sentEn4 + '\'' +
                ", sentTrans4='" + sentTrans4 + '\'' +
                ", lib1=" + lib1 +
                ", lib2=" + lib2 +
                '}';
    }

    @DatabaseField(columnName = "word",unique = true)
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
