package com.app.debrove.tinpandog.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.app.debrove.tinpandog.R;
import com.app.debrove.tinpandog.login.LoginActivity;
import com.app.debrove.tinpandog.util.ShareUtils;
import com.app.debrove.tinpandog.util.StaticClass;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cp4yin on 2018/4/7.
 * package ：com.app.debrove.tinpandog.user
 * description：
 */

public class SettingsActivity extends AppCompatActivity {
    @BindView(R.id.toolbar_settings)
    Toolbar mToolbarSettings;
    @BindView(R.id.btn_log_out)
    Button mBtnLogOut;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        //初始化Toolbar
        setSupportActionBar(mToolbarSettings);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.settings);
        }

    }

    @OnClick(R.id.btn_log_out)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.btn_log_out:
                Intent intent = new Intent(SettingsActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ShareUtils.putBoolean(this, StaticClass.SHARE_IS_LOG_IN,false);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return true;
    }
}
