package com.app.debrove.tinpandog.data;

import java.util.List;

/**
 * Created by debrove on 2017/12/8.
 * Package Name : com.app.debrove.tinpandog.data
 */

public class BannerResponse {

    /**
     * status : 1
     * message : 获取轮播图成功
     * data : [{"id":7,"photo_url":"/project/dingdang/public/upload/20171206/390458bd9ca43725e6e2377efb54795c.jpg","listorder":0,"status":1},{"id":6,"photo_url":"/project/dingdang/public/upload/20171206/73cab09ab587c959cee2f4ae3444fdfb.jpg","listorder":0,"status":1},{"id":2,"photo_url":"/project/dingdang/public/upload\\20171201\\3c5deaee86eb670b015e545811484ade.gif","listorder":0,"status":1}]
     */

    private int status;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 7
         * photo_url : /project/dingdang/public/upload/20171206/390458bd9ca43725e6e2377efb54795c.jpg
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
