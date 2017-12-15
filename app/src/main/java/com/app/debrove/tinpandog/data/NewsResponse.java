package com.app.debrove.tinpandog.data;

import java.util.List;

/**
 * Created by debrove on 2017/11/20.
 * Package Name : com.app.debrove.tinpandog.data
 * T可用类:Activities、Lectures、Token
 */

public class NewsResponse<T> {

    /**
     * status : 1
     * message : 获取全部讲座信息成功
     * data : [{"id":1,"title":"测试1","detail":"测试1详情","time":"2017-11-11","place_id":"","photo_url":"11","status":-1,"listorder":0}]
     */

    private int status;
    private String message;
    private List<T> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
