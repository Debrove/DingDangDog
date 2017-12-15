package com.app.debrove.tinpandog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.app.debrove.tinpandog.data.source.local.ActivitiesLocalDataSource;
import com.app.debrove.tinpandog.data.source.local.LecturesLocalDataSource;
import com.app.debrove.tinpandog.data.source.remote.ActivitiesRemoteDataSource;
import com.app.debrove.tinpandog.data.source.remote.LecturesRemoteDataSource;
import com.app.debrove.tinpandog.data.source.repository.ActivitiesRepository;
import com.app.debrove.tinpandog.data.source.repository.LecturesRepository;
import com.app.debrove.tinpandog.favorites.FavoritesActivity;
import com.app.debrove.tinpandog.groups.GroupsFragment;
import com.app.debrove.tinpandog.helper.BottomNavigationViewHelper;
import com.app.debrove.tinpandog.news.NewsFragment;
import com.app.debrove.tinpandog.schedule.ScheduleFragment;
import com.app.debrove.tinpandog.schedule.SchedulePresenter;
import com.app.debrove.tinpandog.user.UserFragment;

import org.litepal.LitePal;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by debrove on 2017/7/16.
 * <p>
 * Main activity of the app.主页面
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String KEY_BOTTOM_NAVIGATION_VIEW_SELECTED_ID = "KEY_BOTTOM_NAVIGATION_VIEW_SELECTED_ID";

    NewsFragment mNewsFragment;
    ScheduleFragment mScheduleFragment;
    GroupsFragment mGroupsFragment;
    UserFragment mUserFragment;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigation;
    @BindView(R.id.drawer)
    public DrawerLayout mDrawer;
    @BindView(R.id.navigation)
    NavigationView mNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        initFragments(savedInstanceState);

        new SchedulePresenter(
                mScheduleFragment,
                ActivitiesRepository.getInstance(ActivitiesRemoteDataSource.getInstance(),
                        ActivitiesLocalDataSource.getInstance()),
                LecturesRepository.getInstance(LecturesRemoteDataSource.getInstance(),
                        LecturesLocalDataSource.getInstance()));

        //返回app后可以保留在上次的页面，如果为空则为首页
        if (savedInstanceState != null) {
            int id = savedInstanceState.getInt(KEY_BOTTOM_NAVIGATION_VIEW_SELECTED_ID, R.id.bottom_menu_news);
            switch (id) {
                case R.id.bottom_menu_news:
                    showFragment(mNewsFragment);
                    break;
                case R.id.bottom_menu_schedule:
                    showFragment(mScheduleFragment);
                    break;
                case R.id.bottom_menu_groups:
                    showFragment(mGroupsFragment);
                    break;
                case R.id.bottom_menu_user:
                    showFragment(mUserFragment);
                    break;
            }
        } else {
            showFragment(mNewsFragment);
        }

        mBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()) {
                    case R.id.bottom_menu_news:
                        showFragment(mNewsFragment);
                        break;
                    case R.id.bottom_menu_schedule:
                        showFragment(mScheduleFragment);
                        break;
                    case R.id.bottom_menu_groups:
                        showFragment(mGroupsFragment);
                        break;
                    case R.id.bottom_menu_user:
                        showFragment(mUserFragment);
                        break;
                    default:
                        break;
                }
                ft.commit();
                return true;
            }
        });

        //禁止动画效果
        BottomNavigationViewHelper.disableShiftMode(mBottomNavigation);
    }


    private void initView() {
        //设置Toolbar和DrawerLayout实现动画和联动
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, null, 0, 0);
        mDrawer.addDrawerListener(toggle);//设置监听
        toggle.syncState();//加上同步

        mNavigation.setNavigationItemSelectedListener(this);
    }

    //缓存退出时的 状态or页面
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_BOTTOM_NAVIGATION_VIEW_SELECTED_ID, mBottomNavigation.getSelectedItemId());
        FragmentManager fm = getSupportFragmentManager();
        if (mNewsFragment.isAdded()) {
            fm.putFragment(outState, NewsFragment.class.getSimpleName(), mNewsFragment);
        }
        if (mScheduleFragment.isAdded()) {
            fm.putFragment(outState, ScheduleFragment.class.getSimpleName(), mScheduleFragment);
        }
        if (mGroupsFragment.isAdded()) {
            fm.putFragment(outState, GroupsFragment.class.getSimpleName(), mGroupsFragment);
        }
        if (mUserFragment.isAdded()) {
            fm.putFragment(outState, UserFragment.class.getSimpleName(), mUserFragment);
        }
    }

    private void initFragments(Bundle savedInstanceState) {
        FragmentManager fm = getSupportFragmentManager();
        if (savedInstanceState == null) {
            //初始化Fragment
            mNewsFragment = NewsFragment.newInstance();
            mScheduleFragment = ScheduleFragment.newInstance();
            mGroupsFragment = GroupsFragment.newInstance();
            mUserFragment = UserFragment.newInstance();
        } else {
            mNewsFragment = (NewsFragment) fm.getFragment(savedInstanceState, NewsFragment.class.getSimpleName());
            mScheduleFragment = (ScheduleFragment) fm.getFragment(savedInstanceState, ScheduleFragment.class.getSimpleName());
            mGroupsFragment = (GroupsFragment) fm.getFragment(savedInstanceState, GroupsFragment.class.getSimpleName());
            mUserFragment = (UserFragment) fm.getFragment(savedInstanceState, UserFragment.class.getSimpleName());
        }
        //添加Fragment
        if (!mNewsFragment.isAdded()) {
            fm.beginTransaction()
                    .add(R.id.container, mNewsFragment, NewsFragment.class.getSimpleName())
                    .commit();
        }
        if (!mScheduleFragment.isAdded()) {
            fm.beginTransaction()
                    .add(R.id.container, mScheduleFragment, ScheduleFragment.class.getSimpleName())
                    .commit();
        }
        if (!mGroupsFragment.isAdded()) {
            fm.beginTransaction()
                    .add(R.id.container, mGroupsFragment, GroupsFragment.class.getSimpleName())
                    .commit();
        }
        if (!mUserFragment.isAdded()) {
            fm.beginTransaction()
                    .add(R.id.container, mUserFragment, UserFragment.class.getSimpleName())
                    .commit();
        }
    }

    //动态展示Fragment
    private void showFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        if (fragment instanceof NewsFragment) {
            fm.beginTransaction().show(mNewsFragment)
                    .hide(mScheduleFragment)
                    .hide(mGroupsFragment)
                    .hide(mUserFragment)
                    .commit();
        } else if (fragment instanceof ScheduleFragment) {
            fm.beginTransaction().show(mScheduleFragment)
                    .hide(mNewsFragment)
                    .hide(mGroupsFragment)
                    .hide(mUserFragment)
                    .commit();
        } else if (fragment instanceof GroupsFragment) {
            fm.beginTransaction().show(mGroupsFragment)
                    .hide(mNewsFragment)
                    .hide(mScheduleFragment)
                    .hide(mUserFragment)
                    .commit();
        } else if (fragment instanceof UserFragment) {
            fm.beginTransaction().show(mUserFragment)
                    .hide(mNewsFragment)
                    .hide(mScheduleFragment)
                    .hide(mGroupsFragment)
                    .commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.drawer_user:
                //Intent intent=new Intent(this,UserInfo.class);
                break;
            case R.id.drawer_favorite:
                Intent intent = new Intent(this, FavoritesActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }
}
