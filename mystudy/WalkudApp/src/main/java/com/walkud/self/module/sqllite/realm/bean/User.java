package com.walkud.self.module.sqllite.realm.bean;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by jan on 16/3/30.
 */
public class User extends RealmObject {

    /**
     * 主键，不支持自增长
     */
    @PrimaryKey
    private int id;

    private String name;
    private int age;

    private Date date;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
