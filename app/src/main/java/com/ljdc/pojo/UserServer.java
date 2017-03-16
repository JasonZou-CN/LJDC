package com.ljdc.pojo;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Collection;


/**
 * Created with IntelliJ IDEA
 * User:邹旭
 * Date:2017/2/22 0022
 * Time:下午 7:57
 * Desc:略
 */
@DatabaseTable(tableName = "user")
public class UserServer implements Serializable{
    @DatabaseField(columnName = "userId", generatedId = true, allowGeneratedIdInsert = true)
    //if the field ID is null or 0 then the id will be generated,不能传0值
    public int userId;

    @DatabaseField(columnName = "phone")
    public String phone;

    @DatabaseField(columnName = "password")
    public String password;

    @DatabaseField(columnName = "email")
    public String email;

    @DatabaseField(columnName = "nickname")
    public String nickname;

    @DatabaseField(columnName = "headImageUrl")
    public String headImageUrl;

    @ForeignCollectionField(eager = false)
    public Collection<LearnLib1Server> learnLib1;

    @ForeignCollectionField(eager = false)
    public Collection<LearnLib2Server> learnLib2;

    @ForeignCollectionField(eager = false)
    public Collection<WordDevelopmentServer> wordDevelopment;

    @ForeignCollectionField(eager = false)
    public Collection<StudyPlan> studyPlen;

    public UserServer() {
    }

    public UserServer(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserServer{" +
                "userId=" + userId +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", headImageUrl='" + headImageUrl + '\'' +
                '}';
    }
}
