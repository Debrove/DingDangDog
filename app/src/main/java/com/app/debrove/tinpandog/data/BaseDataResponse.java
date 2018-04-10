package com.app.debrove.tinpandog.data;

import java.util.List;

/**
 * Created by cp4yin on 2018/4/4.
 * package ：com.app.debrove.tinpandog.data
 * description：
 */

public class BaseDataResponse<T> {
    /**
     * data : [{"id":5,"title":"南色美术活动基地 · 创意现场（第二十四期）","holder":"","detail":"南色美术活动基地 · 创意现场（第二十四期）南色美术活动基地 · 创意现场（第二十四期）南色美术活动基地 · 创意现场（第二十四期）","time":"2017-12-04","place_id":{"id":4,"name":"香洲区","max":200,"status":1},"photo_url":"https://20298479.rehellinen.cn/dingdang/public/upload/20180227/2776b9cdd11f3d1236ee0fa213014714.jpg","status":2,"listorder":0},{"id":8,"title":"品茶静心 读书会","holder":"123","detail":"此刻，读到此文时的你\r\n\r\n是在繁杂的人事物不断周旋后，想要给自己的心安个家的你\u2026\r\n还是年轻如你，漂流在异乡城市，有点小空虚，有点小寂寞，又开始有点小厌烦酒吧和KTV的嘈杂，想换换口味，体会些小清新和小文艺\u2026\r\n抑或是你遇到了人生转折，百思都无法百全，敲不定那要前行的方向，困惑徘徊\u2026\r\n那么我推荐你来。。\r\n\r\n我们只是\r\n喝茶\r\n静心\r\n读书\r\n分享人生感悟\r\n\r\n我们绝大部分时间都在做重要的事，要求自己更快、更有效率。但我们必须花时间跟自己相处，享受人生。然后，或许我们才会看到，真正重要的事情。很多答案，在真实面对安静的自己时，才是真正的尘埃落定。\r\n这里\r\n没有怀疑 没有评判 \r\n只有真实 接纳 包容\r\n\r\n时光太纷扰，短暂忘却方可寻回初心。 \r\n\r\n我们是一帮有爱的团体，欢迎你一起同往，共修，路很长，志同道合之士碰到不易，且行且珍惜。\r\n\r\n活动带领者：雪花 高级茶艺师，高级品茶师，幸福密码训练师，资深茶室设计师，茶行业导师，茶疗导师 \r\n\r\n活动带领者：天禧 高级茶艺师， 高级品茶师，资深茶疗导师，茶呼吸导师\r\n\r\n活动带领者，活动发起人：Vivien 高级茶艺师，外企管理者，三级心理咨询师 \r\n\r\n活动环节：- 带领静心茶，让参与者感受归心，回到当下\r\n- 参与者轮流读书/诗 \r\n- 由当天读书内容主题，引申讨论分享\r\n- 视乎场域能量，会带领冥想，与自己的连接（选择）","time":"2017-11-17","place_id":{"id":2,"name":"会议室","max":200,"status":1},"photo_url":"https://20298479.rehellinen.cn/dingdang/public/upload/20180227/44facc831bf05e0b8825e1758ba526e7.jpg","status":2,"listorder":0},{"id":2,"title":"珠海沙滩音乐节门票预售正式开启！","holder":"","detail":"需要购买电子票的用户，扫描二维码或点击\u201c阅读原文\u201d进入【2017珠海沙滩音乐节官方商城】页面购买即可；","time":"2017-11-12","place_id":{"id":2,"name":"会议室","max":200,"status":1},"photo_url":"https://20298479.rehellinen.cn/dingdang/public/upload/20180227/233070db42ca1c5240682cc015472ea1.jpg","status":2,"listorder":0}]
     * request_url : /dingdang/public/api/v1/allActivities
     */

    private String request_url;
    private List<T> data;

    public String getRequest_url() {
        return request_url;
    }

    public void setRequest_url(String request_url) {
        this.request_url = request_url;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
