package com.app.debrove.tinpandog.data;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by cp4yin on 2017/12/21.
 * <p>
 * 地点
 */

public class Place extends DataSupport {


    /**
     * id : 6
     * name : 情侣路
     * max : 130
     * status : 1
     */

    private int id;
    private String name;//地点名称
    private int max;//容量
    private int status;

    @Column(unique = true)
    private int newsId;

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

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }
}
