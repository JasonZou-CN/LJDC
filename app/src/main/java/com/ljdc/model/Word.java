package com.ljdc.model;

/**
 * Created with IntelliJ IDEA
 * User:邹旭
 * Date:2017/1/10 0010
 * Time:上午 1:42
 * Desc:略
 */
public class Word {
    public String name = "12333242255";
    public String info = "n. （河流的）三角洲；德耳塔（希腊字母的第四个字）" +
            "n. (Delta)人名；(英、罗、葡)德尔塔";
    public Word(String name, String info) {
        this.name = name;
        this.info = info;
    }

    public Word() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Word{" +
                "name='" + name + '\'' +
                ", info='" + info + '\'' +
                '}';
    }

}
