package com.app.debrove.tinpandog.groups;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.debrove.tinpandog.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by debrove on 2017/7/17.
 * Package Name : com.app.debrove.tinpandog.groups
 */

public class GroupsFragment extends Fragment implements GroupsContract.View, Toolbar.OnMenuItemClickListener {

    private GroupsContract.Presenter mPresenter;

    Unbinder unbinder;
    @BindView(R.id.toolbar_groups)
    Toolbar mToolbarGroups;

    private DrawerLayout mDrawerLayout;

    public static GroupsFragment newInstance() {
        return new GroupsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_groups, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();
        return view;
    }

    private void initView() {
        //Toolbar
        setHasOptionsMenu(true);
        mToolbarGroups.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        mToolbarGroups.inflateMenu(R.menu.menu_groups);
        mToolbarGroups.setTitle("群组");
        mToolbarGroups.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        mToolbarGroups.setOnMenuItemClickListener(this);
        mDrawerLayout = getActivity().findViewById(R.id.drawer);
    }

    @Override
    public void setPresenter(GroupsContract.Presenter presenter) {
        if (presenter != null) {
            mPresenter = presenter;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //菜单栏的点击事件
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                Toast.makeText(getContext(), "add", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @OnClick(R.id.groups_layout)
    public void onViewClicked() {

    }
}
