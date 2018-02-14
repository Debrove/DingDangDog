package com.app.debrove.tinpandog.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.app.debrove.tinpandog.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by debrove on 2017/10/13.
 * Package Name : com.app.debrove.tinpandog.login
 * <p>
 * 登录和注册页
 */

public class LoginActivity extends AppCompatActivity {
    private TabLayout mTabLayout;

    private LoginFragment mLoginFragment;
    private RegisterFragment mRegisterFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initFragments(savedInstanceState);

        showFragment(mLoginFragment);
    }


    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.login_tab_layout);
        mTabLayout.addTab(mTabLayout.newTab().setText("登录"));
        mTabLayout.addTab(mTabLayout.newTab().setText("注册"));
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        showFragment(mLoginFragment);
                        break;
                    case 1:
                        showFragment(mRegisterFragment);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        ft.commit();
    }


    private void initFragments(Bundle savedInstanceState) {
        FragmentManager fm = getSupportFragmentManager();
        if (savedInstanceState == null) {
            mRegisterFragment = RegisterFragment.newInstance();
            mLoginFragment = LoginFragment.newInstance();
        } else {
            mRegisterFragment = (RegisterFragment) fm.getFragment(savedInstanceState, RegisterFragment.class.getSimpleName());
            mLoginFragment = (LoginFragment) fm.getFragment(savedInstanceState, LoginFragment.class.getSimpleName());
        }

        if (!mRegisterFragment.isAdded()) {
            fm.beginTransaction()
                    .add(R.id.container, mRegisterFragment, RegisterFragment.class.getSimpleName())
                    .commit();
        }

        if (!mLoginFragment.isAdded()) {
            fm.beginTransaction()
                    .add(R.id.container, mLoginFragment, RegisterFragment.class.getSimpleName())
                    .commit();
        }
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        if (fragment instanceof RegisterFragment) {
            fm.beginTransaction()
                    .show(mRegisterFragment)
                    .hide(mLoginFragment)
                    .commit();
        } else if (fragment instanceof LoginFragment) {
            fm.beginTransaction()
                    .show(mLoginFragment)
                    .hide(mRegisterFragment)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager fm = getSupportFragmentManager();
        if (mRegisterFragment.isAdded()) {
            fm.putFragment(outState, RegisterFragment.class.getSimpleName(), mRegisterFragment);
        }
        if (mLoginFragment.isAdded()) {
            fm.putFragment(outState, LoginFragment.class.getSimpleName(), mLoginFragment);
        }
    }
}
