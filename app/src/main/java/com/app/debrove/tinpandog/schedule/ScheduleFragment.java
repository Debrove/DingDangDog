package com.app.debrove.tinpandog.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.debrove.tinpandog.R;
import com.app.debrove.tinpandog.data.Activities;
import com.app.debrove.tinpandog.data.ContentType;
import com.app.debrove.tinpandog.data.Lectures;
import com.app.debrove.tinpandog.interfaze.OnRecyclerViewItemOnClickListener;
import com.app.debrove.tinpandog.signup.SignInActivity;
import com.app.debrove.tinpandog.util.DateFormatUtils;
import com.app.debrove.tinpandog.util.L;
import com.app.debrove.tinpandog.view.CustomDayView;
import com.ldf.calendar.component.CalendarAttr;
import com.ldf.calendar.component.CalendarViewAdapter;
import com.ldf.calendar.interf.OnSelectDateListener;
import com.ldf.calendar.model.CalendarDate;
import com.ldf.calendar.view.Calendar;
import com.ldf.calendar.view.MonthPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by debrove on 2017/7/17.
 * Package Name : com.app.debrove.tinpandog.schedule
 * <p>
 * Fragment主要执行View相关的操作
 */

public class ScheduleFragment extends Fragment implements ScheduleContract.View, Toolbar.OnMenuItemClickListener {

    private static final String LOG_TAG = ScheduleFragment.class.getSimpleName();

    @BindView(R.id.show_month_view)
    TextView mShowMonthView;
    @BindView(R.id.show_year_view)
    TextView mShowYearView;
    @BindView(R.id.show_day_view)
    TextView mShowDayView;
    //    @BindView(R.id.choose_date_view)
//    LinearLayout mChooseDateView;
//    @BindView(R.id.scroll_switch)
//    TextView mScrollSwitch;
//    @BindView(R.id.back_today_button)
//    TextView mBackTodayButton;
    @BindView(R.id.content)
    CoordinatorLayout mContent;
    @BindView(R.id.month_pager)
    MonthPager mMonthPager;
    @BindView(R.id.toolbar_schedule)
    Toolbar mToolbarSchedule;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.empty_view)
    LinearLayout mEmptyView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    private ScheduleContract.Presenter mPresenter;

    private ScheduleAdapter mAdapter;

    private CalendarViewAdapter calendarAdapter;
    private OnSelectDateListener onSelectDateListener;
    private int mCurrentPage = MonthPager.CURRENT_DAY_INDEX;
    private ArrayList<Calendar> currentCalendars = new ArrayList<>();
    private CalendarDate currentDate;

    private List<Activities> mActivitiesList;

    private List<Lectures> mLecturesList;

    Unbinder unbinder;

    public static ScheduleFragment newInstance() {
        return new ScheduleFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();
        showCalendarView();
        showCalendarDate();
        return view;
    }

    private void initView() {
        //Toolbar
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbarSchedule);
        setHasOptionsMenu(true);
        mToolbarSchedule.inflateMenu(R.menu.menu_schedule);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadAll();
                setLoadingIndicator(false);
            }
        });

        mPresenter.loadAll();

    }

    @Override
    public void showList(final List<Activities> activitiesList, final List<Lectures> lecturesList) {
        boolean isEmpty = true;
        if (activitiesList == null && lecturesList == null) {
            mEmptyView.setVisibility(View.VISIBLE);
            setLoadingIndicator(false);
            return;
        } else {
            isEmpty = false;
        }
        L.d(LOG_TAG, activitiesList + " " + lecturesList);

        if (mAdapter == null && !isEmpty) {
            L.d(LOG_TAG, activitiesList + " 1 " + lecturesList);
            mActivitiesList = activitiesList;
            mLecturesList = lecturesList;

            mAdapter = new ScheduleAdapter(getContext(), mActivitiesList, mLecturesList);
            mAdapter.setItemClickListener(new OnRecyclerViewItemOnClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    int viewType = mAdapter.getItemViewType(position);
                    L.d(LOG_TAG, "viewType " + viewType + " " + ScheduleAdapter.ItemWrapper.TYPE_ACTIVITIES);

                    switch (v.getId()) {
                        case R.id.btn_sign_in:
                            L.d("signIN", "signIn " + position);
//                            L.d(LOG_TAG,activitiesList.get(mAdapter.getOriginalIndex(position)).getNewsId()+
//                                    " id "+ lecturesList.get(mAdapter.getOriginalIndex(position)).getNewsId());

                            if (viewType == ScheduleAdapter.ItemWrapper.TYPE_ACTIVITIES) {
                                Intent intent = new Intent(getContext(), SignInActivity.class);
                                intent.putExtra(SignInActivity.KEY_ARTICLE_ID, activitiesList.
                                        get(mAdapter.getOriginalIndex(position)).getNewsId());
                                intent.putExtra(SignInActivity.KEY_ARTICLE_TYPE, ContentType.TYPE_ACTIVITIES);
                                startActivity(intent);
                            } else if (viewType == ScheduleAdapter.ItemWrapper.TYPE_LECTURES) {
                                Intent intent = new Intent(getContext(), SignInActivity.class);
                                intent.putExtra(SignInActivity.KEY_ARTICLE_ID, lecturesList.
                                        get(mAdapter.getOriginalIndex(position)).getNewsId());
                                intent.putExtra(SignInActivity.KEY_ARTICLE_TYPE, ContentType.TYPE_LECTURES);
                                startActivity(intent);
                            }

                            break;
                        default:
                            Toast.makeText(getContext(), "敬请期待", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });

            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.updateData(activitiesList, lecturesList);
        }
        mRecyclerView.setVisibility(activitiesList.isEmpty() && lecturesList.isEmpty() ? View.GONE : View.VISIBLE);
        mEmptyView.setVisibility(activitiesList.isEmpty() && lecturesList.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void getAllList(List<Activities> activitiesList, List<Lectures> lecturesList) {
        if (activitiesList != null) {
            mActivitiesList = activitiesList;
        }

        if (lecturesList != null) {
            mLecturesList = lecturesList;
        }

        L.d(LOG_TAG, "list all " + mActivitiesList + " " + mLecturesList);
    }

    public void showCalendarView() {
        initListener();
        CustomDayView customDayView = new CustomDayView(
                getContext(), R.layout.custom_day);
        calendarAdapter = new CalendarViewAdapter(
                getContext(),
                onSelectDateListener,
                CalendarAttr.CalendayType.MONTH,
                customDayView);
        initMarkData();
        initMonthPager();
    }

    public void showCalendarDate() {
        currentDate = new CalendarDate();
        mShowYearView.setText(currentDate.getYear() + "年");
        mShowMonthView.setText(currentDate.getMonth() + "");
        mShowDayView.setText(currentDate.getDay() + "日");

        final Long date = DateFormatUtils.formatNewsDateStringToLong(currentDate.toString());
        L.d(LOG_TAG, " currentDate " + date + " " + currentDate);

        L.d(LOG_TAG, "refresh in showCalendarDate() " + date);
        mPresenter.loadList(date);
    }


    private void initListener() {
        onSelectDateListener = new OnSelectDateListener() {
            @Override
            public void onSelectDate(CalendarDate date) {
                refreshClickDate(date);
            }

            @Override
            public void onSelectOtherMonth(int offset) {
                //偏移量 -1表示刷新成上一个月数据 ， 1表示刷新成下一个月数据
                mMonthPager.selectOtherMonth(offset);
            }
        };
    }

    private void refreshClickDate(final CalendarDate date) {
        currentDate = date;
        mShowYearView.setText(date.getYear() + "年");
        mShowMonthView.setText(date.getMonth() + "");
        mShowDayView.setText(date.getDay() + "日");

        final Long time = DateFormatUtils.formatNewsDateStringToLong(date.toString());
        L.d(LOG_TAG, "click date " + time + " " + date);

        L.d(LOG_TAG, "refresh in refreshClickDate() " + date);
        mPresenter.loadList(time);
    }

    /**
     * 初始化monthPager，MonthPager继承自ViewPager
     *
     * @return void
     */
    private void initMonthPager() {
        mMonthPager.setAdapter(calendarAdapter);
        mMonthPager.setCurrentItem(MonthPager.CURRENT_DAY_INDEX);
        mMonthPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                position = (float) Math.sqrt(1 - Math.abs(position));
                page.setAlpha(position);
            }
        });
        mMonthPager.addOnPageChangeListener(new MonthPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
                currentCalendars = calendarAdapter.getPagers();
                if (currentCalendars.get(position % currentCalendars.size()) != null) {
                    currentDate = currentCalendars.get(position % currentCalendars.size()).getSeedDate();
                    mShowYearView.setText(currentDate.getYear() + "年");
                    mShowMonthView.setText(currentDate.getMonth() + "");
//                    mCardShowMonthView.setText(currentDate.getMonth() + "");
//                    mCardShowDayView.setText(currentDate.getDay() + "日");

                    Long date = DateFormatUtils.formatNewsDateStringToLong(currentDate.toString());
                    L.d(LOG_TAG, "refresh in onPageSelected() " + date);
                    mPresenter.loadList(date);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initMarkData() {
        currentDate = new CalendarDate();

        HashMap markData = new HashMap<>();

        String time, time2;
        long time1, currentDate1;
        currentDate1 = DateFormatUtils.formatNewsDateStringToLong(currentDate.toString());

        //0表示红点，1表示灰点
        L.d(LOG_TAG, "markData " + mActivitiesList);
        for (Activities item : mActivitiesList) {
            time = item.getTime();
            time1 = DateFormatUtils.formatNewsDateStringToLong(time);
            time2 = DateFormatUtils.formatNewsDateLongToStringWithoutZero(time1);

            if (time1 - currentDate1 >= 0) {
                markData.put(time2, "0");
            } else {
                markData.put(time2, "1");
            }
            L.d(LOG_TAG, " time " + time + " time2 " + time2 + " currentDate1 " + currentDate1);
        }

        for (Lectures item : mLecturesList) {
            time = item.getTime();
            time1 = DateFormatUtils.formatNewsDateStringToLong(time);
            time2 = DateFormatUtils.formatNewsDateLongToStringWithoutZero(time1);

            if (time1 - currentDate1 >= 0) {
                markData.put(time2, "0");
            } else {
                markData.put(time2, "1");
            }
            L.d(LOG_TAG, " time " + time + " time2 " + time2);
        }

        calendarAdapter.setMarkData(markData);
    }

    @Override
    public void onResume() {
        super.onResume();
        currentDate = new CalendarDate();
        final Long date = DateFormatUtils.formatNewsDateStringToLong(currentDate.toString());
        L.d(LOG_TAG, "refresh in onResume() " + date);
        mPresenter.loadList(date);
    }

    @Override
    public void setPresenter(ScheduleContract.Presenter presenter) {
        if (presenter != null) {
            mPresenter = presenter;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public boolean isActive() {
        return isAdded() && isResumed();
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(active);
            }
        });
    }


    //菜单栏的点击事件
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_back_today:
                onClickBackToDayBtn();
                break;
        }
        return true;
    }

    //回到今天
    private void onClickBackToDayBtn() {
        CalendarDate today = new CalendarDate();
        calendarAdapter.notifyDataChanged(today);
    }
}
