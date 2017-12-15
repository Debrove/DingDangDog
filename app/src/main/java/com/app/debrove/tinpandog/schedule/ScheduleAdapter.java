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
import com.app.debrove.tinpandog.interfaze.OnRecyclerViewItemOnClickListener;
import com.ldf.calendar.model.CalendarDate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by debrove on 2017/7/24.
 * Package Name : com.app.debrove.tinpandog.schedule
 */

class ScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ACTIVITIES = 0;

    private static final int TYPE_LECTURES = 1;

    private final Context mContext;

    @NonNull
    private final LayoutInflater mLayoutInflater;

    @NonNull
    private final List<Activities> mActivitiesList;

    @NonNull
    private final List<Lectures> mLecturesList;

    private final List<ItemWrapper> mWrapperList;

    private OnRecyclerViewItemOnClickListener mListener;

    private CalendarDate currentDate;

    ScheduleAdapter(Context context,
                    @NonNull List<Activities> mActivitiesList,
                    @NonNull List<Lectures> mLecturesList) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.mActivitiesList = mActivitiesList;
        this.mLecturesList = mLecturesList;

        mWrapperList = new ArrayList<>();
        if (!mActivitiesList.isEmpty()) {
            for (int i = 0; i < mActivitiesList.size(); i++) {
                ItemWrapper iw = new ItemWrapper(ItemWrapper.TYPE_ACTIVITIES);
                iw.index = i;
                mWrapperList.add(iw);
            }
        }

        if (!mLecturesList.isEmpty()) {
            for (int i = 0; i < mLecturesList.size(); i++) {
                ItemWrapper iw = new ItemWrapper(ItemWrapper.TYPE_LECTURES);
                iw.index = i;
                mWrapperList.add(iw);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
//        if (position < getActivitiesSize()) {
//            return TYPE_ACTIVITIES;
//        }
//        return TYPE_LECTURES;
        return mWrapperList.get(position).viewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
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
        currentDate = new CalendarDate();

        ItemWrapper iw = mWrapperList.get(position);
        switch (iw.viewType) {
            case ItemWrapper.TYPE_ACTIVITIES:
                ItemViewHolder viewHolder = (ItemViewHolder) holder;
                Activities activitiesItem = mActivitiesList.get(iw.index);
                viewHolder.title.setText(activitiesItem.getTitle());
                viewHolder.place.setText(activitiesItem.getPlace_id());
                break;
            case ItemWrapper.TYPE_LECTURES:
                ItemViewHolder viewHolder1 = (ItemViewHolder) holder;
                Lectures lecturesItem = mLecturesList.get(iw.index);
                viewHolder1.title.setText(lecturesItem.getTitle());
                viewHolder1.place.setText(lecturesItem.getPlace_id());
                break;

        }

//        Activities activitiesItem = mActivitiesList.get(position);
//        ItemViewHolder viewHolder = (ItemViewHolder) holder;
//        //viewHolder.showMonthView.setText(currentDate.getMonth());
//        //viewHolder.showDayView.setText(currentDate.getDay());
//
//
//        Lectures lecturesItem = mLecturesList.get(position - mActivitiesList.size());
//        ItemViewHolder viewHolder1 = (ItemViewHolder) holder;
//        //viewHolder1.showMonthView.setText(currentDate.getMonth());
//        //viewHolder1.showDayView.setText(currentDate.getDay());

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
        private TextView title;
        private TextView place;

        ItemViewHolder(View itemView, OnRecyclerViewItemOnClickListener listener) {
            super(itemView);
            showMonthView = itemView.findViewById(R.id.show_month_view);
            showDayView = itemView.findViewById(R.id.show_day_view);
            title = itemView.findViewById(R.id.title);
            place = itemView.findViewById(R.id.tv_place);
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

        if (!mActivitiesList.isEmpty()) {
            for (int i = 0; i < activitiesList.size(); i++) {
                ItemWrapper iw = new ItemWrapper(ItemWrapper.TYPE_ACTIVITIES);
                iw.index = i;
                mWrapperList.add(iw);
                mActivitiesList.add(activitiesList.get(i));
            }
        }

        if (!mLecturesList.isEmpty()) {
            for (int i = 0; i < lecturesList.size(); i++) {
                ItemWrapper iw = new ItemWrapper(ItemWrapper.TYPE_LECTURES);
                iw.index = i;
                mWrapperList.add(iw);
                mLecturesList.add(lecturesList.get(i));
            }
        }
        notifyDataSetChanged();
    }
}
