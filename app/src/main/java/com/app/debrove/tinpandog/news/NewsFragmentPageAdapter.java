package com.app.debrove.tinpandog.news;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.app.debrove.tinpandog.R;

/**
 * Created by debrove on 2017/10/24.
 * Package Name : com.app.debrove.tinpandog.news
 */

public class NewsFragmentPageAdapter extends FragmentPagerAdapter {

    private final int pageCount = 2;
    private String[] titles;

    private ActivitiesFragment mActivitiesFragment;
    private LecturesFragment mLecturesFragment;

    public NewsFragmentPageAdapter(FragmentManager fm,
                                   Context context,
                                   ActivitiesFragment activitiesFragment,
                                   LecturesFragment lecturesFragment) {
        super(fm);
        titles = new String[]{
                context.getString(R.string.activities_news),
                context.getString(R.string.lectures_news)};
        this.mActivitiesFragment = activitiesFragment;
        this.mLecturesFragment = lecturesFragment;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return mActivitiesFragment;
        } else {
            return mLecturesFragment;
        }
    }

    @Override
    public int getCount() {
        return pageCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
