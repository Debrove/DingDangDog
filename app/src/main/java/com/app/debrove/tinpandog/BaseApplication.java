package com.app.debrove.tinpandog;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.app.debrove.tinpandog.reminder.ActivityReminder;
import com.app.debrove.tinpandog.reminder.db.NoticeInfo;
import com.app.debrove.tinpandog.util.StaticClass;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;
import org.litepal.crud.DataSupport;

import java.util.List;

//import cn.bmob.v3.Bmob;

/**
 * Created by debrove on 2017/9/21.
 * Package Name : com.app.debrove.tinpandog
 */

public class BaseApplication extends LitePalApplication{

    private final static String TAG=BaseApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        //初始化Bmob ID
//        Bmob.initialize(this, StaticClass.BMOB_APP_ID);

        //初始化环信SDK
//        EMOptions options = new EMOptions();

//        EMClient.getInstance().init(getApplicationContext(), options);
//        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
//        EMClient.getInstance().setDebugMode(true);
        showNoticeIfNeeded();
    }

    /**
     *如果有此前未成功通知用户的消息，则告知其数量
     */
    private void showNoticeIfNeeded(){
        long when=System.currentTimeMillis();
        int noticeUnsentCount=DataSupport.where("isSent=? and when<?",0+"",when+"").find(NoticeInfo.class).size();
        if(noticeUnsentCount>0)
            Toast.makeText(this,"你有"+noticeUnsentCount+"个活动可能未被通知。",Toast.LENGTH_SHORT).show();
        else
            Log.e(TAG,"my:no data found");
    }
}
