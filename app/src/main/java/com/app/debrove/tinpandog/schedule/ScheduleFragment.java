package com.app.debrove.tinpandog.schedule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.debrove.tinpandog.R;
import com.app.debrove.tinpandog.view.CustomDayView;
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
 * Fragment主要执行View相关的操作
 */

public class ScheduleFragment extends Fragment implements ScheduleContract.View {

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
    //    @BindView(R.id.list)
//    RecyclerView mList;
    @BindView(R.id.toolbar_schedule)
    Toolbar mToolbarSchedule;
    @BindView(R.id.card_show_month_view)
    TextView mCardShowMonthView;
    @BindView(R.id.card_show_day_view)
    TextView mCardShowDayView;

    private ScheduleContract.Presenter mPresenter;

//    private ScheduleAdapter mAdapter;

    //    private ArrayList<Schedule> events = new ArrayList<>();
    private CalendarViewAdapter calendarAdapter;
    private OnSelectDateListener onSelectDateListener;
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

        initView();
        showCalendarView();
        showCalendarDate();
        return view;
    }

    private void initView() {
        //Toolbar
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbarSchedule);
        setHasOptionsMenu(true);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            actionBar.setDisplayShowTitleEnabled(false);
        }
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

    private void refreshClickDate(CalendarDate date) {
        currentDate = date;
        mShowYearView.setText(date.getYear() + "年");
        mShowMonthView.setText(date.getMonth() + "");
        mShowDayView.setText(date.getDay() + "日");
        mCardShowMonthView.setText(date.getMonth() + "");
        mCardShowDayView.setText(date.getDay() + "日");
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
                    mCardShowMonthView.setText(currentDate.getMonth() + "");
                    mCardShowDayView.setText(currentDate.getDay() + "日");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initMarkData() {
        HashMap markData = new HashMap<>();
        //0表示红点，1表示灰点
        markData.put("2017-10-2" , "1");
        markData.put("2017-10-3" , "1");
        markData.put("2017-10-5" , "1");
        markData.put("2017-10-6" , "1");
        markData.put("2017-10-7" , "0");
        markData.put("2017-10-9" , "0");
        markData.put("2017-10-11" , "0");
        markData.put("2017-10-12" , "0");
        markData.put("2017-10-15" , "0");
        markData.put("2017-10-17" , "0");
        markData.put("2017-10-18" , "0");
        markData.put("2017-10-20" , "0");
        calendarAdapter.setMarkData(markData);
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
        mCardShowMonthView.setText(currentDate.getMonth() + "");
        mCardShowDayView.setText(currentDate.getDay() + "日");
    }


    //    //回到今天
//    private void onClickBackToDayBtn() {
//        CalendarDate today = new CalendarDate();
//        calendarAdapter.notifyDataChanged(today);
//    }
}
