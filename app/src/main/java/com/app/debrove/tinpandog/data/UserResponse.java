package com.app.debrove.tinpandog.data;

/**
 * Created by cp4yin on 2017/12/17.
 */

public class UserResponse {


    /**
     * status : 1
     * message : 获取成功
     * data : {"id":17,"name":"温一二","telephone":18902521110,"number":2015051110,"status":1}
     */

    private int status;
    private String message;
    private User data;

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

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

}
