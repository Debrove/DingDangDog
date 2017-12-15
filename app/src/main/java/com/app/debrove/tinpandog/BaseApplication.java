package com.app.debrove.tinpandog;

import android.app.Application;
import android.util.Log;

import com.app.debrove.tinpandog.util.StaticClass;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import org.litepal.LitePalApplication;

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
        //初始化Bmob ID
//        Bmob.initialize(this, StaticClass.BMOB_APP_ID);

        //初始化环信SDK
//        EMOptions options = new EMOptions();

//        EMClient.getInstance().init(getApplicationContext(), options);
//        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
//        EMClient.getInstance().setDebugMode(true);
    }
}
