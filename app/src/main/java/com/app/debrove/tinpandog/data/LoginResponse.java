package com.app.debrove.tinpandog.data;

/**
 * Created by debrove on 2017/11/23.
 * Package Name : com.app.debrove.tinpandog.data
 * 登录和报名Response
 */

public class LoginResponse {

    /**
     * status : 1
     * message : 登录成功
     * data : {"token":"37a6766fc7239ea00932be1cf60c2a86"}
     */

    private String status;
    private String message;
    private DataBean data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * token : 37a6766fc7239ea00932be1cf60c2a86
         * 成功时
         */

        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
