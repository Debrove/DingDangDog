package com.app.debrove.tinpandog.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.debrove.tinpandog.R;
import com.app.debrove.tinpandog.data.source.local.ActivitiesLocalDataSource;
import com.app.debrove.tinpandog.data.source.local.LecturesLocalDataSource;
import com.app.debrove.tinpandog.data.source.remote.ActivitiesRemoteDataSource;
import com.app.debrove.tinpandog.data.source.remote.LecturesRemoteDataSource;
import com.app.debrove.tinpandog.data.source.repository.ActivitiesRepository;
import com.app.debrove.tinpandog.data.source.repository.LecturesRepository;

import org.litepal.LitePal;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by debrove on 2017/7/17.
 * Package Name : com.app.debrove.tinpandog.news
 */

public class NewsFragment extends Fragment implements View.OnClickListener {

    private ActivitiesFragment mActivitiesFragment;
    private LecturesFragment mLecturesFragment;

    private DrawerLayout mDrawerLayout;
    private FloatingActionButton mFab;
    //    @BindView(R.id.toolbar_news)
    private Toolbar mToolbarNews;
    //    @BindView(R.id.tab_layout)
    private TabLayout mTabLayout;
    //    @BindView(R.id.view_pager)
//    ViewPager mViewPager;
    Unbinder unbinder;

    public NewsFragment() {
        // An empty constructor is needed as a fragment.
    }

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            FragmentManager fm = getChildFragmentManager();
            mActivitiesFragment = (ActivitiesFragment) fm.getFragment(savedInstanceState, ActivitiesFragment.class.getSimpleName());
            mLecturesFragment = (LecturesFragment) fm.getFragment(savedInstanceState, LecturesFragment.class.getSimpleName());
        } else {
            mActivitiesFragment = ActivitiesFragment.newInstance();
            mLecturesFragment = LecturesFragment.newInstance();
        }

        new ActivitiesPresenter(mActivitiesFragment, ActivitiesRepository.getInstance(
                ActivitiesRemoteDataSource.getInstance(),
                ActivitiesLocalDataSource.getInstance()));

        new LecturesPresenter(mLecturesFragment, LecturesRepository.getInstance(
                LecturesRemoteDataSource.getInstance(),
                LecturesLocalDataSource.getInstance()));

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        initView(view);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void initView(View view) {
        //Toolbar
        mToolbarNews = view.findViewById(R.id.toolbar_news);
        setHasOptionsMenu(true);
        mToolbarNews.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        mToolbarNews.inflateMenu(R.menu.menu_news);
        mToolbarNews.setTitle(R.string.app_name);
        mToolbarNews.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        mDrawerLayout = getActivity().findViewById(R.id.drawer);

        ViewPager mViewPager = view.findViewById(R.id.view_pager);
        mViewPager.setAdapter(new NewsFragmentPageAdapter(
                getChildFragmentManager(),
                getContext(),
                mActivitiesFragment,
                mLecturesFragment
        ));
        mViewPager.setOffscreenPageLimit(2);

        mTabLayout = view.findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mViewPager);

        mFab = view.findViewById(R.id.fab);
        mFab.setOnClickListener(this);
        LitePal.getDatabase();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager fm = getChildFragmentManager();
        if (mActivitiesFragment.isAdded()) {
            fm.putFragment(outState, ActivitiesFragment.class.getSimpleName(), mActivitiesFragment);
        }
        if (mLecturesFragment.isAdded()) {
            fm.putFragment(outState, LecturesFragment.class.getSimpleName(), mLecturesFragment);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        if (mTabLayout.getSelectedTabPosition() == 0) {
            mActivitiesFragment.showDatePickerDialog();
        } else {
            mLecturesFragment.showDatePickerDialog();
        }
    }
}