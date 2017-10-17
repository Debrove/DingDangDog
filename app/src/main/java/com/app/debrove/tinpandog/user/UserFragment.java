package com.app.debrove.tinpandog.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.debrove.tinpandog.R;
import com.app.debrove.tinpandog.util.ShareUtils;
import com.app.debrove.tinpandog.util.StaticClass;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by debrove on 2017/9/28.
 * Package Name : com.app.debrove.tinpandog.user
 */

public class UserFragment extends Fragment implements UserContract.View {


    Unbinder unbinder;
    @BindView(R.id.toolbar_user)
    Toolbar mToolbarUser;
    @BindView(R.id.user_name)
    TextView mUserName;

    private DrawerLayout mDrawerLayout;

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        unbinder = ButterKnife.bind(this, view);
        initView();

        return view;
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

        mUserName.setText(ShareUtils.getString(getContext(), StaticClass.KEY_USER_NAME,"用户名"));
    }

    @Override
    public void setPresenter(UserContract.Presenter presenter) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
