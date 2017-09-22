package com.app.debrove.tinpandog.schedule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
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
    @BindView(R.id.list)
    RecyclerView mList;

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

        showCalendarView();
        showCalendarDate();
        return view;
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
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //显示菜单
//        setHasOptionsMenu(true);
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
        initMonthPager();

        //events = Schedule.createSchedule("暨大羽毛球赛");

    }

    public void showCalendarDate() {
        currentDate = new CalendarDate();
        mShowYearView.setText(currentDate.getYear() + "年");
        mShowMonthView.setText(currentDate.getMonth() + "");
    }


//    //回到今天
//    private void onClickBackToDayBtn() {
//        CalendarDate today = new CalendarDate();
//        calendarAdapter.notifyDataChanged(today);
//    }
}
