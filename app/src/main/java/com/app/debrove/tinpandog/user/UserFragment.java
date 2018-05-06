package com.app.debrove.tinpandog.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.debrove.tinpandog.R;
import com.app.debrove.tinpandog.data.User;
import com.app.debrove.tinpandog.favorites.FavoritesActivity;
import com.app.debrove.tinpandog.reminder.db.NoticeInfo;
import com.app.debrove.tinpandog.util.L;
import com.app.debrove.tinpandog.util.ShareUtils;
import com.app.debrove.tinpandog.util.StaticClass;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by debrove on 2017/9/28.
 * Package Name : com.app.debrove.tinpandog.user
 */

public class UserFragment extends Fragment implements UserContract.View {

    private static final String LOG_TAG = UserFragment.class.getSimpleName();


    private UserContract.Presenter mPresenter;

    Unbinder unbinder;
    @BindView(R.id.toolbar_user)
    Toolbar mToolbarUser;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.telephone)
    TextView mTelephone;
    @BindView(R.id.notice_text)
    TextView mNoticeText;

    private DrawerLayout mDrawerLayout;

    private String token;
    private String telephone;
    private String name = "";
    private String stuNum = "";

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        unbinder = ButterKnife.bind(this, view);
        initView();
        //getUserInfoFromDB();
        //获取用户信息
        mPresenter.loadUserInfo(telephone, token);
        showNoticeInfo();

        return view;
    }

    /**
     * 显示未到期活动数量，到期的从数据库删除
     */
    private void showNoticeInfo() {
        List<NoticeInfo> noticeInfoList = DataSupport.findAll(NoticeInfo.class);
        long time = System.currentTimeMillis();
        int noticeNeedShownCount = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        for (NoticeInfo noticeInfo : noticeInfoList) {
            Date date = new Date(noticeInfo.getWhen());
            L.d("attended", noticeInfo + " ");
            Log.e(LOG_TAG, "my:" + simpleDateFormat.format(date) + " " +
                    simpleDateFormat.format(noticeInfo.getWhen()) + " " + simpleDateFormat.format(time));
            if (time >= noticeInfo.getWhen())
                noticeInfo.delete();
            else
                noticeNeedShownCount++;
        }
        if (noticeNeedShownCount > 0)
            mNoticeText.setText("当前活动数：" + noticeNeedShownCount);
        else
            mNoticeText.setText("没有待处理活动");
    }

    @Override
    public void showInfo(List<User> userInfo) {

        L.d(LOG_TAG, "userinfo " + userInfo);

        for (User user : userInfo) {
            name = user.getName();
            stuNum = user.getNumber();
        }
        L.d(LOG_TAG, "name " + name);
        mTelephone.setText(telephone);
        mUserName.setText(name);
        ShareUtils.putString(getContext(), StaticClass.KEY_USERNAME, name);
        ShareUtils.putString(getContext(), StaticClass.KEY_STU_NUM, stuNum);
    }

    @Override
    public void getToken(String token) {
        //刷新token
        L.d(LOG_TAG, " new token " + token);
        ShareUtils.putString(getContext(), StaticClass.KEY_ACCESS_TOKEN, token);
    }

    @Override
    public void refreshToken() {
        mPresenter.refreshToken(telephone);
        mPresenter.loadUserInfo(telephone, token);
        L.d(LOG_TAG, "token in refreshing " + token);
    }

    private void initView() {
        //Toolbar
        setHasOptionsMenu(true);
        mToolbarUser.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        mToolbarUser.setTitle("我");
        mToolbarUser.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        mDrawerLayout = getActivity().findViewById(R.id.drawer);

        token = ShareUtils.getString(getContext(), StaticClass.KEY_ACCESS_TOKEN, "0");
        telephone = ShareUtils.getString(getContext(), StaticClass.KEY_USER_TELEPHONE, "");

        L.d(LOG_TAG, "telephone " + telephone + " token " + token);
    }

    @Override
    public void setPresenter(UserContract.Presenter presenter) {
        if (presenter != null) {
            mPresenter = presenter;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ll_favorite, R.id.ll_user_info, R.id.ll_user_settings, R.id.ll_attended
            , R.id.ll_notice})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_favorite:
                Intent intent = new Intent(getActivity(), FavoritesActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_user_info:
                Intent intent1 = new Intent(getActivity(), UserInfoActivity.class);
                intent1.putExtra(UserInfoActivity.KEY_USER_TELEPHONE, telephone);
                intent1.putExtra(UserInfoActivity.KEY_USERNAME, name);
                intent1.putExtra(UserInfoActivity.KEY_USER_STU_NUM, stuNum);
                startActivity(intent1);
                break;
            case R.id.ll_user_settings:
                Intent intent2 = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent2);
                break;
            case R.id.ll_attended:
                Intent intent3 = new Intent(getActivity(), AttendedActivity.class);
                startActivity(intent3);
                break;
            default:
                break;
        }
    }


    @Override
    public boolean isActive() {
        return isAdded() && isResumed();
    }
}
