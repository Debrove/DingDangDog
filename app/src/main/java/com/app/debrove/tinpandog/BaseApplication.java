package com.app.debrove.tinpandog;

import android.app.Application;

import com.app.debrove.tinpandog.util.StaticClass;

import cn.bmob.v3.Bmob;

/**
 * Created by debrove on 2017/9/21.
 * Package Name : com.app.debrove.tinpandog
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Bmob ID
        Bmob.initialize(this, StaticClass.BMOB_APP_ID);
    }
}
