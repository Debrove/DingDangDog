package com.app.debrove.tinpandog.data;

import java.util.List;

/**
 * Created by debrove on 2017/12/8.
 * Package Name : com.app.debrove.tinpandog.data
 */

public class BannerResponse {

    /**
     * status : 1
     * message : 成功
     * data : {"data":[{"id":8,"photo_url":"https://20298479.rehellinen.cn/dingdang/public/upload/20180227/97f3f46337e0005bb16312c890bdc276.jpg","listorder":0,"status":1},{"id":7,"photo_url":"https://20298479.rehellinen.cn/dingdang/public/upload/20180227/d08ae521e45c2fa313d49cfcf19834ba.jpg","listorder":0,"status":1},{"id":6,"photo_url":"https://20298479.rehellinen.cn/dingdang/public/upload/20180227/bf5f9fcf41cf4beb6bfa5bb4c7403a54.jpg","listorder":0,"status":1},{"id":2,"photo_url":"https://20298479.rehellinen.cn/dingdang/public/upload/20180227/e533b9a6da27752f52a1ab51bce7a2fa.jpg","listorder":0,"status":1}],"request_url":"/dingdang/public/api/v1/banner"}
     */

    private int status;
    private String message;
    private BaseDataResponse<Banner> data;

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

    public BaseDataResponse<Banner> getData() {
        return data;
    }

    public void setData(BaseDataResponse<Banner> data) {
        this.data = data;
    }


    public static class Banner {
        /**
         * id : 8
         * photo_url : https://20298479.rehellinen.cn/dingdang/public/upload/20180227/97f3f46337e0005bb16312c890bdc276.jpg
         * listorder : 0
         * status : 1
         */

        private int id;
        private String photo_url;
        private int listorder;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPhoto_url() {
            return photo_url;
        }

        public void setPhoto_url(String photo_url) {
            this.photo_url = photo_url;
        }

        public int getListorder() {
            return listorder;
        }

        public void setListorder(int listorder) {
            this.listorder = listorder;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
