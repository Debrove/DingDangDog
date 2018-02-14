package com.app.debrove.tinpandog.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.app.debrove.tinpandog.reminder.db.NoticeInfo;

/**
 * Created by NameTooLong on 2018/1/31.
 *
 * 创建通知的工具类
 * ps：在华为等部分手机上会因某些原因延误甚至无法发出通知，
 * 未找到有效解决方案
 */

public final class ActivityReminder {
    private static final String TAG = "ActivityReminder";

    private ActivityReminder(){
        //do nothing
    }

    public static final int BROADCAST_CODE_SEND_NOTICE=1;

    /**
     * 发送一个通知，将在消息栏和app内展示
     * ps：仅在当时发送一个广播，不做具体事务处理，具体功能由{@link RemindBroadcastReceiver}实现
     * @param context 当前context
     * @param whenToSend 通知产生时间
     * @param whenActivityStart 活动发生时间
     * @param content 通知具体内容
     */
    public static void createRemind(Context context,long whenToSend,long whenActivityStart,String content){
        long id=NoticeInfo.saveNoticeInfo(whenActivityStart,content);
        Intent intent=new Intent(context.getApplicationContext(),RemindBroadcastReceiver.class);
        intent.putExtra(RemindBroadcastReceiver.NOTICE_ID,id);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context.getApplicationContext(),BROADCAST_CODE_SEND_NOTICE,intent,PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,whenToSend,pendingIntent);
    }
}
