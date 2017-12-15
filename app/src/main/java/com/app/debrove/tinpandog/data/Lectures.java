package com.app.debrove.tinpandog.data;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by debrove on 2017/12/2.
 * Package Name : com.app.debrove.tinpandog.data
 * <p>
 * Lectures(Lectures)的实体类
 */

public class Lectures extends DataSupport {
    private int id;

    @Column(unique = true)
    private int newsId;

    private String title;//标题

    private String time;//日期

    private String place_id;//地点

    private String text;//新闻内容

    private String detail;//新闻详情内容

    private String photo_url;//图片链接

    private boolean favorite;

    private boolean pre_sign_up;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public boolean isPre_sign_up() {
        return pre_sign_up;
    }

    public void setPre_sign_up(boolean pre_sign_up) {
        this.pre_sign_up = pre_sign_up;
    }
}
