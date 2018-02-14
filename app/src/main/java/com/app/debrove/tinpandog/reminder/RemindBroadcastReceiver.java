package com.app.debrove.tinpandog.reminder;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.app.debrove.tinpandog.MainActivity;
import com.app.debrove.tinpandog.R;
import com.app.debrove.tinpandog.reminder.db.NoticeInfo;

import org.litepal.crud.DataSupport;

/**
 * Created by NameTooLong on 2018/1/31.
 *
 * 接受广播，发送通知
 * @see ActivityReminder#createRemind(Context, long, long, String)
 */

public class RemindBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "RemindBroadcastReceiver";

    public static final String NOTICE_ID="notice id";
    private static final int OPEN_APP=1;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG,"my:onReceive");
        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        long id=intent.getLongExtra(NOTICE_ID,0);
        NoticeInfo noticeInfo=DataSupport.find(NoticeInfo.class,id);
        noticeInfo.setIsSent(1);
        Intent intentOpenApp=new Intent(context, MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,OPEN_APP,intentOpenApp,PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .setContentTitle(noticeInfo.getContent())
                .setContentText("你报名的该活动将于一天后开始");
        notificationManager.notify(1,builder.build());
    }
}
