package com.app.debrove.tinpandog.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.app.debrove.tinpandog.MainActivity;
import com.app.debrove.tinpandog.R;
import com.app.debrove.tinpandog.login.LoginActivity;
import com.app.debrove.tinpandog.util.ShareUtils;
import com.app.debrove.tinpandog.util.StaticClass;
import com.hyphenate.chat.EMClient;

/**
 * Created by debrove on 2017/10/15.
 * Package Name : com.app.debrove.tinpandog.view
 * <p>
 * 闪屏页
 */

public class SplashActivity extends AppCompatActivity {
    /**
     * 1.延时2000ms
     * 2.判断程序是否第一次运行
     * 3.Activity全屏主题
     */

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case StaticClass.HANDLER_SPLASH:
                    //判断程序是否已经登陆
                    if (isLoggedIn()) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }
                    finish();
                    break;
            }
        }
    };

    //判断是否已经登录
    public boolean isLoggedIn() {
        boolean isLogIn = ShareUtils.getBoolean(this, StaticClass.SHARE_IS_LOG_IN, false);
        if (isLogIn) {
            return true;
        } else {
            return false;
        }
    }

    //判断程序是否是第一次运行
    private boolean isFirst() {
        boolean isFirst = ShareUtils.getBoolean(this, StaticClass.SHARE_IS_FIRST, true);
        if (isFirst) {
            ShareUtils.putBoolean(this, StaticClass.SHARE_IS_FIRST, false);
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //延时2000ms
        mHandler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH, 1000);
    }
}
