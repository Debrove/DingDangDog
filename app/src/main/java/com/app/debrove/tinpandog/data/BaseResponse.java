package com.app.debrove.tinpandog.data;

import java.util.List;

/**
 * Created by debrove on 2017/11/20.
 * Package Name : com.app.debrove.tinpandog.data
 * T可用类:Activities、Lectures、Token、SignUp
 */

public class BaseResponse<T> {

    /**
     * status : 1
     * message : 获取全部活动信息成功
     * data : {"data":[{"id":5,"title":"","holder":"","detail":"","time":"2017-12-04","place_id":{"id":4,"name":"香洲区","max":200,"status":1},"photo_url":"","status":2,"listorder":0},{"id":8,"title":"品茶静心 读书会","holder":"123","detail":"","time":"2017-11-17","place_id":{"id":2,"name":"会议室","max":200,"status":1},"photo_url":"","status":2,"listorder":0},{"id":2,"title":"","holder":"","detail":"","time":"2017-11-12","place_id":{"id":2,"name":"会议室","max":200,"status":1},"photo_url":"","status":2,"listorder":0}],"request_url":"/dingdang/public/api/v1/allActivities"}
     */

    private int status;
    private String message;
    private BaseDataResponse<T> data;

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

    public BaseDataResponse<T> getData() {
        return data;
    }

    public void setData(BaseDataResponse<T> data) {
        this.data = data;
    }
}
