package com.app.debrove.tinpandog.schedule;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.debrove.tinpandog.R;
import com.ldf.calendar.component.CalendarAttr;
import com.ldf.calendar.component.CalendarViewAdapter;
import com.ldf.calendar.interf.OnSelectDateListener;
import com.ldf.calendar.model.CalendarDate;
import com.ldf.calendar.view.Calendar;
import com.ldf.calendar.view.MonthPager;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by debrove on 2017/7/17.
 * Package Name : com.app.debrove.tinpandog.schedule
 * <p>
 * 日历引用开源库SuperCalendar
 */

public class ScheduleFragment extends Fragment implements ScheduleContract.View {

    @BindView(R.id.show_month_view)
    TextView mShowMonthView;
    @BindView(R.id.show_year_view)
    TextView mShowYearView;
    @BindView(R.id.back_today_button)
    TextView mBackTodayButton;
    @BindView(R.id.scroll_switch)
    TextView mScrollSwitch;
    @BindView(R.id.theme_switch)
    TextView mThemeSwitch;
    @BindView(R.id.list)
    RecyclerView mRvList;
    @BindView(R.id.calendar_view)
    MonthPager monthPager;
    @BindView(R.id.content)
    CoordinatorLayout mContent;


    private OnSelectDateListener onSelectDateListener;
    private Context context;
    private CalendarViewAdapter calendarAdapter;
    private int mCurrentPage = MonthPager.CURRENT_DAY_INDEX;
    private ArrayList<Calendar> currentCalendars = new ArrayList<>();
    private CalendarDate currentDate;

    Unbinder unbinder;

    public static ScheduleFragment newInstance() {
        return new ScheduleFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        unbinder = ButterKnife.bind(this, view);
        context = getContext();

        //RecyclerView的配置
        mRvList.setHasFixedSize(true);
        mRvList.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvList.setAdapter(new ScheduleAdapter(getContext()));

        initCurrentDate();
        initScheduleView();
        return view;
    }

    //初始化当前日期
    private void initCurrentDate() {
        currentDate = new CalendarDate();
        mShowYearView.setText(currentDate.getYear() + "年");
        mShowMonthView.setText(currentDate.getMonth() + "");
    }

    //初始化日程View
    private void initScheduleView() {
        initListener();
        CustomDayView customDayView = new CustomDayView(context, R.layout.custom_day);
        calendarAdapter = new CalendarViewAdapter(
                context,
                onSelectDateListener,
                CalendarAttr.CalendayType.MONTH,
                customDayView);
        initMarkData();
        initMonthPager();
    }

    //使用此方法初始化日历标记数据
    private void initMarkData() {
        HashMap<String, String> markData = new HashMap<>();
        //1表示标记，0表示未标记
        markData.put("2017-8-9", "1");
        markData.put("2017-7-9", "0");
        markData.put("2017-6-9", "1");
        markData.put("2017-6-10", "0");
        calendarAdapter.setMarkData(markData);
    }

    //使用此方法回调日历点击事件
    private void initListener() {
        onSelectDateListener = new OnSelectDateListener() {
            @Override
            public void onSelectDate(CalendarDate calendarDate) {
                refreshClickDate(calendarDate);
            }

            @Override
            public void onSelectOtherMonth(int i) {
                //偏移量 -1表示上一个月 ， 1表示下一个月
                monthPager.selectOtherMonth(i);
            }
        };
    }

    private void refreshClickDate(CalendarDate calendarDate) {
        currentDate = calendarDate;
        mShowYearView.setText(calendarDate.getYear() + "年");
        mShowMonthView.setText(calendarDate.getMonth() + "");
    }

    @Override
    public void setPresenter(ScheduleContract.Presenter presenter) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //使用此方法给MonthPager添加上相关监听
    private void initMonthPager() {
        monthPager.setAdapter(calendarAdapter);
        monthPager.setCurrentItem(MonthPager.CURRENT_DAY_INDEX);
        monthPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                position = (float) Math.sqrt(1 - Math.abs(position));
                page.setAlpha(position);
            }
        });
        monthPager.addOnPageChangeListener(new MonthPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
                currentCalendars = calendarAdapter.getPagers();
                if (currentCalendars.get(position % currentCalendars.size()) != null) {
                    CalendarDate date = currentCalendars.get(position % currentCalendars.size()).getSeedDate();
                    currentDate = date;
                    mShowYearView.setText(date.getYear() + "年");
                    mShowMonthView.setText(date.getMonth() + "");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}
