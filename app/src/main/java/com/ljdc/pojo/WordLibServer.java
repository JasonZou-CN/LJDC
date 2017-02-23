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
@DatabaseTable(tableName = "word_lib_server")
public class WordLibServer {
    @DatabaseField(columnName = "word_id",generatedId = true)
    public int wordId;
    
    @DatabaseField(columnName = "word")
    public String word;
    
    @DatabaseField(columnName = "pron_str_en")
    public String pronStrEn;
    
    @DatabaseField(columnName = "pron_url_en")
    public String pronUrlEn;
    
    @DatabaseField(columnName = "pron_str_us")
    public String pronStrUs;

    @DatabaseField(columnName = "pron_url_us")
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

    @DatabaseField(columnName = "sent_en1")
    public String sentEn1;

    @DatabaseField(columnName = "sent_trans1")
    public String sentTrans1;

    @DatabaseField(columnName = "sent_en2")
    public String sentEn2;

    @DatabaseField(columnName = "sent_trans2")
    public String sentTrans2;

    @DatabaseField(columnName = "sent_en3")
    public String sentEn3;

    @DatabaseField(columnName = "sent_trans3")
    public String sentTrans3;

    @DatabaseField(columnName = "sent_en4")
    public String sentEn4;

    @DatabaseField(columnName = "sent_trans4")
    public String sentTrans4;

    @ForeignCollectionField(eager = true)
    public ForeignCollection<Lib1EnglishGrand4CoreServer> lib1;

    @ForeignCollectionField(eager = true)
    public ForeignCollection<Lib2MiddleSchoolServer> lib2;

}
