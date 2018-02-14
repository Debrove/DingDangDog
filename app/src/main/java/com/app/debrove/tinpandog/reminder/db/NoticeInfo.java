package com.app.debrove.tinpandog.reminder.db;

import android.util.Log;

import org.litepal.annotation.Column;
import org.litepal.annotation.Encrypt;
import org.litepal.crud.DataSupport;

/**
 * Created by NameTooLong on 2018/1/31.
 *
 * 储存通知的具体内容
 */

public class NoticeInfo extends DataSupport {
    @Column(ignore = true)
    private static final String TAG = "NoticeInfo";

    /**
     * 通知的时间
     */
    private long when;

    /**
     * 通知id，litepal自动管理，无需再赋值
     */
    private long id;

    /**
     * 该通知是否向用户发送
     * 可选值：0（默认值），未向用户推送；
     *        1，已向用户推送
     */
    @Column(defaultValue = "0")
    private int isSent;

    /**
     * 通知的具体文字内容
     */
    private String content;

    private NoticeInfo(){

    }

    public static long saveNoticeInfo(long when,String content){
        NoticeInfo noticeInfo=new NoticeInfo();
        noticeInfo.when=when;
        noticeInfo.content=content;
        boolean isSaved=noticeInfo.save();
        Log.e(TAG,"my:save="+isSaved);
        return noticeInfo.id;
    }

    public long getWhen() {
        return when;
    }

    public String getContent() {
        return content;
    }

    public int getIsSent() {
        return isSent;
    }

    public void setIsSent(int isSent) {
        this.isSent = isSent;
    }

    public long getId() {
        return id;
    }
}
