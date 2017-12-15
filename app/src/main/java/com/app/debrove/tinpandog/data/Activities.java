package com.app.debrove.tinpandog.data;


import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by debrove on 2017/9/17.
 * Package Name : com.app.debrove.tinpandog.data
 * <p>
 * Activitie(Activities)的实体类
 * id为数据库的id，newsId是从服务器上获取的id
 */

public class Activities extends DataSupport {

    private int id;

    private String title;//标题

    private String time;//日期

    private String place_id;//地点

    private String text;//新闻内容

    private String detail;//新闻详情内容

    private String photo_url;//图片链接

    private boolean favorite;

    private boolean pre_sign_up;

    @Column(unique = true)
    private int newsId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setTime(String date) {
        this.time = date;
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

    public boolean isFavourite() {
        return favorite;
    }

    public void setFavourite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
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

    //    //比较时间排序
//    @Override
//    public int compareTo(@NonNull Activities activities) {
//        return (int) (getStringToDate(this.getTime()) - getStringToDate(activities.getTime()));
//    }
//
//    /* 将字符串转为时间戳 */
//    private static long getStringToDate(String time) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = new Date();
//        try {
//            date = sdf.parse(time);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return date.getTime() / 100000;
//    }


}