package com.app.debrove.tinpandog.data;

/**
 * Created by debrove on 2017/11/26.
 * Package Name : com.app.debrove.tinpandog.data
 */

public class UserResponse {

    /**
     * status : 1
     * message : 获取成功
     * data : {"id":36,"name":"温一二","telephone":18902521110,"number":2015051110,"status":1}
     */

    private int status;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 36
         * name : 温一二
         * telephone : 18902521110
         * number : 2015051110
         * status : 1
         */

        private int id;
        private String name;
        private long telephone;
        private int number;
        private int status;

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

        public long getTelephone() {
            return telephone;
        }

        public void setTelephone(long telephone) {
            this.telephone = telephone;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
