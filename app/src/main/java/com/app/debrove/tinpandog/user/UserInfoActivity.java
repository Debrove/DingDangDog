package com.app.debrove.tinpandog.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.app.debrove.tinpandog.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by debrove on 2017/11/26.
 * Package Name : com.app.debrove.tinpandog.user
 */

public class UserInfoActivity extends AppCompatActivity {

    public static final String KEY_USERNAME = "KEY_USERNAME";
    public static final String KEY_USER_TELEPHONE = "KEY_USER_TELEPHONE";
    public static final String KEY_USER_STU_NUM = "KEY_USER_STU_NUM";

    @BindView(R.id.toolbar_user)
    Toolbar mToolbarUser;
    @BindView(R.id.tv_username)
    TextView mTvUsername;
    @BindView(R.id.tv_user_telephone)
    TextView mTvUserTelephone;
    @BindView(R.id.tv_user_stu_num)
    TextView mTvUserStuNum;

    private String name;
    private String telephone;
    private String stuNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        //初始化Toolbar
        setSupportActionBar(mToolbarUser);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.userinfo);
        }

        name = getIntent().getStringExtra(KEY_USERNAME);
        telephone = getIntent().getStringExtra(KEY_USER_TELEPHONE);
        stuNum = getIntent().getStringExtra(KEY_USER_STU_NUM);

        mTvUsername.setText(name);
        mTvUserTelephone.setText(telephone);
        mTvUserStuNum.setText(stuNum);
    }

    @OnClick({R.id.ll_user_images, R.id.ll_user_name, R.id.ll_user_telephone, R.id.ll_user_stu_num})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_user_images:
                break;
            case R.id.ll_user_name:
                break;
            case R.id.ll_user_telephone:
                break;
            case R.id.ll_user_stu_num:
                break;
            default:
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
