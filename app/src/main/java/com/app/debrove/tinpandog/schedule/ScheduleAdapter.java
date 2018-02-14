package com.app.debrove.tinpandog.schedule;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.debrove.tinpandog.R;
import com.app.debrove.tinpandog.data.Activities;
import com.app.debrove.tinpandog.data.Lectures;
import com.app.debrove.tinpandog.data.Place;
import com.app.debrove.tinpandog.interfaze.OnRecyclerViewItemOnClickListener;
import com.app.debrove.tinpandog.util.DateFormatUtils;
import com.app.debrove.tinpandog.util.L;
import com.ldf.calendar.model.CalendarDate;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by debrove on 2017/7/24.
 * Package Name : com.app.debrove.tinpandog.schedule
 */

class ScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ACTIVITIES = 0;

    private static final int TYPE_LECTURES = 1;
    private static final String LOG_TAG = ScheduleAdapter.class.getSimpleName();

    private final Context mContext;

    @NonNull
    private final LayoutInflater mLayoutInflater;

    @NonNull
    private final List<Activities> mActivitiesList;

    @NonNull
    private final List<Lectures> mLecturesList;

    private final List<ItemWrapper> mWrapperList;

    private OnRecyclerViewItemOnClickListener mListener;

    ScheduleAdapter(Context context,
                    @NonNull List<Activities> mActivitiesList,
                    @NonNull List<Lectures> mLecturesList) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.mActivitiesList = mActivitiesList;
        this.mLecturesList = mLecturesList;

        mWrapperList = new ArrayList<>();
        if (mActivitiesList.isEmpty() && mLecturesList.isEmpty()) {
            mWrapperList.add(new ItemWrapper(ItemWrapper.TYPE_EMPTY));
            L.d(LOG_TAG, "all empty ");
        } else if (!mActivitiesList.isEmpty()) {
            L.d(LOG_TAG, "activity not empty ");
            for (int i = 0; i < mActivitiesList.size(); i++) {
                ItemWrapper iw = new ItemWrapper(ItemWrapper.TYPE_ACTIVITIES);
                iw.index = i;
                mWrapperList.add(iw);
            }
        } else if (!mLecturesList.isEmpty()) {
            L.d(LOG_TAG, "lecture not empty ");
            for (int i = 0; i < mLecturesList.size(); i++) {
                ItemWrapper iw = new ItemWrapper(ItemWrapper.TYPE_LECTURES);
                iw.index = i;
                mWrapperList.add(iw);
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        return mWrapperList.get(position).viewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case ItemWrapper.TYPE_EMPTY:
                viewHolder = new ItemViewHolder(mLayoutInflater.inflate(R.layout.schedule_item, parent, false), mListener);
                break;
            case ItemWrapper.TYPE_ACTIVITIES:
                viewHolder = new ItemViewHolder(mLayoutInflater.inflate(R.layout.schedule_item, parent, false), mListener);
                break;
            case ItemWrapper.TYPE_LECTURES:
                viewHolder = new ItemViewHolder(mLayoutInflater.inflate(R.layout.schedule_item, parent, false), mListener);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String date, time, time2,currentDate3;
        long date1, currentDate1, currentTime, time1,currentDate2;
        String month;
        String day;
        Date currentDate = new Date();

        ItemWrapper iw = mWrapperList.get(position);
        switch (iw.viewType) {
            case ItemWrapper.TYPE_ACTIVITIES:
                ItemViewHolder viewHolder = (ItemViewHolder) holder;
                Activities activitiesItem = mActivitiesList.get(iw.index);
                viewHolder.title.setText(activitiesItem.getTitle());
                viewHolder.sponsor.setText(activitiesItem.getHolder());
                viewHolder.place.setText(getPlace(activitiesItem.getNewsId()));

                //时间格式化
                date = activitiesItem.getTime();//news日期
                time = activitiesItem.getTime1();//news时间
                date1 = DateFormatUtils.formatNewsDateStringToLong(date);//news日期的时间戳
                currentDate1 = DateFormatUtils.formatSystemDateStringToLong(String.valueOf(currentDate));//当前日期时间戳
                month = DateFormatUtils.getMonth(date1);//news月份
                day = DateFormatUtils.getDay(date1);//news day
                time2=DateFormatUtils.formatNewsTimeLongToString(currentDate1);//当前时分
                currentTime =DateFormatUtils.formatNewsTimeStringToLong(time2);//只包含时分的当前时间戳
                currentDate3=DateFormatUtils.formatNewsDateLongToString(currentDate1);//当前年月日
                currentDate2=DateFormatUtils.formatNewsDateStringToLong(currentDate3);//只包含年月日的当前时间戳

                L.d(LOG_TAG, " 当前时分：" + DateFormatUtils.formatNewsTimeLongToString(currentTime));
                time1 = DateFormatUtils.formatNewsTimeStringToLong(time);//news时分的时间戳

                viewHolder.showMonthView.setText(month);
                viewHolder.showDayView.setText(day + mContext.getString(R.string.tv_day));
                viewHolder.time.setText(activitiesItem.getTime1());
                L.d(LOG_TAG, " place " + getPlace(activitiesItem.getNewsId()) + " " + getPlace(activitiesItem.getId()));
                L.d(LOG_TAG, "date " + date + " Month " + month + " Day " + day + " currentDate " + currentDate + " currentDate1 " + currentDate1 +
                        " currentTime " + currentTime +" date1 "+date1 +" currentDate2 " + currentDate2+" time1 "+time1);

                if (date1 - currentDate2 > 0) {
                    viewHolder.start.setText("未开始");
                } else if (date1 - currentDate2 < 0) {
                    viewHolder.start.setText("已结束");
                } else {
                    if (time1 > currentTime) {
                        viewHolder.start.setText("未开始");
                    } else if (time1 < currentTime) {
                        viewHolder.start.setText("已经开始");
                    }
                }

                break;
            case ItemWrapper.TYPE_LECTURES:
                ItemViewHolder viewHolder1 = (ItemViewHolder) holder;
                Lectures lecturesItem = mLecturesList.get(iw.index);
                viewHolder1.title.setText(lecturesItem.getTitle());
                viewHolder1.sponsor.setText(lecturesItem.getHolder());
                viewHolder1.place.setText(getPlace(lecturesItem.getNewsId()));

                //时间格式化
                date = lecturesItem.getTime();//news日期
                time = lecturesItem.getTime1();//news时间
                date1 = DateFormatUtils.formatNewsDateStringToLong(date);//news日期的时间戳
                currentDate1 = DateFormatUtils.formatSystemDateStringToLong(String.valueOf(currentDate));//当前日期时间戳
                month = DateFormatUtils.getMonth(date1);//news月份
                day = DateFormatUtils.getDay(date1);//news day
                time2=DateFormatUtils.formatNewsTimeLongToString(currentDate1);//当前时分
                currentTime = DateFormatUtils.formatNewsTimeStringToLong(time2);//只包含时分的当前时间戳
                currentDate2=currentDate1/100000*100000;//只包含年月日的当前时间戳
                time1 = DateFormatUtils.formatNewsTimeStringToLong(time);//news时分的时间戳

                viewHolder1.showMonthView.setText(month);
                viewHolder1.showDayView.setText(day + mContext.getString(R.string.tv_day));
                viewHolder1.time.setText(lecturesItem.getTime1());

                L.d(LOG_TAG, " place " + getPlace(lecturesItem.getNewsId()) + " " + getPlace(lecturesItem.getId()));
                L.d(LOG_TAG, "date " + date + " Month " + month + " Day " + day + " currentDate " + currentDate + " currentDate1 " + currentDate1 +
                        " currentTime " + currentTime);

                if (date1 - currentDate2 > 0) {
                    viewHolder1.start.setText("未开始");
                } else if (date1 - currentDate2 < 0) {
                    viewHolder1.start.setText("已结束");
                } else {
                    if (time1 > currentTime) {
                        viewHolder1.start.setText("未开始");
                    } else if (time1 < currentTime) {
                        viewHolder1.start.setText("已经开始");
                    }
                }

                break;
        }
    }


    @Override
    public int getItemCount() {
        //return (mActivitiesList.isEmpty() && mLecturesList.isEmpty()) ? 0 : mActivitiesList.size() + mLecturesList.size();
        return mWrapperList == null ? 0 : mWrapperList.size();
    }

    private int getActivitiesSize() {
        return mActivitiesList.isEmpty() ? 0 : mActivitiesList.size();
    }

    public void setItemClickListener(OnRecyclerViewItemOnClickListener listener) {
        this.mListener = listener;
    }

    public static class ItemWrapper {
        final static int TYPE_ACTIVITIES = 0x00;
        final static int TYPE_LECTURES = 0x01;
        final static int TYPE_EMPTY = 0x02;
        final static int TYPE_ACTIVITIES_CATEGORY = 0x03;
        final static int TYPE_LECTURES_CATEGORY = 0x04;

        int viewType;
        int index;

        ItemWrapper(int viewType) {
            this.viewType = viewType;
        }
    }


    private class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private OnRecyclerViewItemOnClickListener listener;
        private TextView showMonthView;
        private TextView showDayView;
        private TextView time;
        private TextView title;
        private TextView place;
        private TextView sponsor;
        private TextView start;

        ItemViewHolder(View itemView, OnRecyclerViewItemOnClickListener listener) {
            super(itemView);
            showMonthView = itemView.findViewById(R.id.show_month_view);
            showDayView = itemView.findViewById(R.id.show_day_view);
            time = itemView.findViewById(R.id.tv_time);
            title = itemView.findViewById(R.id.title);
            place = itemView.findViewById(R.id.tv_place);
            sponsor = itemView.findViewById(R.id.tv_sponsor);
            start = itemView.findViewById(R.id.tv_isStarted);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClick(v, getLayoutPosition());
            }
        }
    }

    public void updateData(List<Activities> activitiesList,
                           List<Lectures> lecturesList) {
        mActivitiesList.clear();
        mLecturesList.clear();
        mWrapperList.clear();

        if (activitiesList.isEmpty() && lecturesList.isEmpty()) {
            mWrapperList.add(new ItemWrapper(ItemWrapper.TYPE_EMPTY));
            L.d(LOG_TAG, "update:all empty ");
        } else if (!activitiesList.isEmpty()) {
            for (int i = 0; i < activitiesList.size(); i++) {
                L.d(LOG_TAG, "update:activity not empty ");
                ItemWrapper iw = new ItemWrapper(ItemWrapper.TYPE_ACTIVITIES);
                iw.index = i;
                mWrapperList.add(iw);
                mActivitiesList.add(activitiesList.get(i));
            }
        } else if (!lecturesList.isEmpty()) {
            L.d(LOG_TAG, "update:lecture not empty ");
            for (int i = 0; i < lecturesList.size(); i++) {
                ItemWrapper iw = new ItemWrapper(ItemWrapper.TYPE_LECTURES);
                iw.index = i;
                mWrapperList.add(iw);
                mLecturesList.add(lecturesList.get(i));
            }
        }


        notifyDataSetChanged();
    }

    private String getPlace(int id) {
        String name = "未知";
        List<Place> list = DataSupport.where("newsId = ?", String.valueOf(id)).find(Place.class);
        L.d(LOG_TAG, "list " + list + "size " + list.size());
        for (Place place1 : list) {
            name = place1.getName();
            L.d(LOG_TAG, " name " + name);
        }
        return name;
    }
}
