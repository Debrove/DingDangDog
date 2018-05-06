package com.app.debrove.tinpandog.user;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.debrove.tinpandog.R;
import com.app.debrove.tinpandog.data.Activities;
import com.app.debrove.tinpandog.data.Lectures;
import com.app.debrove.tinpandog.interfaze.OnRecyclerViewItemOnClickListener;
import com.app.debrove.tinpandog.util.L;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by cp4yin on 2018/4/10.
 * package ：com.app.debrove.tinpandog.user
 * description：
 */

class AttendedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String LOG_TAG = AttendedAdapter.class.getSimpleName();
    @NonNull
    private final Context mContext;

    @NonNull
    private final LayoutInflater mLayoutInflater;

    @NonNull
    private final List<Activities> mActivitiesList;

    @NonNull
    private final List<Lectures> mLecturesList;

    private final List<ItemWrapper> mWrapperList;

    private OnRecyclerViewItemOnClickListener mListener;

    AttendedAdapter(@NonNull Context mContext,
                    @NonNull List<Activities> activitiesList,
                    @NonNull List<Lectures> lecturesList) {
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.mActivitiesList = activitiesList;
        this.mLecturesList = lecturesList;

        mWrapperList = new ArrayList<>();

        L.d(LOG_TAG, "activitiesList " + activitiesList + " lecturesList" + lecturesList);

        mWrapperList.add(new ItemWrapper(ItemWrapper.TYPE_ACTIVITIES_CATEGORY));
        if (activitiesList.isEmpty()) {
            mWrapperList.add(new ItemWrapper(ItemWrapper.TYPE_EMPTY));
        } else {
            for (int i = 0; i < mActivitiesList.size(); i++) {
                ItemWrapper iw = new ItemWrapper(ItemWrapper.TYPE_ACTIVITIES);
                iw.index = i;
                mWrapperList.add(iw);
            }
        }

        mWrapperList.add(new ItemWrapper(ItemWrapper.TYPE_LECTURES_CATEGORY));
        if (lecturesList.isEmpty()) {
            mWrapperList.add(new ItemWrapper(ItemWrapper.TYPE_EMPTY));
        } else {
            for (int i = 0; i < mLecturesList.size(); i++) {
                ItemWrapper iw = new ItemWrapper(ItemWrapper.TYPE_LECTURES);
                iw.index = i;
                mWrapperList.add(iw);
            }
        }
        L.d(LOG_TAG, mWrapperList + " ");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case ItemWrapper.TYPE_EMPTY:
                viewHolder = new EmptyViewHolder(mLayoutInflater.inflate(R.layout.item_empty, parent, false));
                break;
            case ItemWrapper.TYPE_ACTIVITIES:
                viewHolder = new ActivitiesViewHolder(mLayoutInflater.inflate(R.layout.item_universal_layout, parent, false), mListener);
                break;
            case ItemWrapper.TYPE_LECTURES:
                viewHolder = new LecturesViewHolder(mLayoutInflater.inflate(R.layout.item_universal_layout, parent, false), mListener);
                break;
            case ItemWrapper.TYPE_ACTIVITIES_CATEGORY:
            case ItemWrapper.TYPE_LECTURES_CATEGORY:
                viewHolder = new CategoryViewHolder(mLayoutInflater.inflate(R.layout.item_category, parent, false));
                break;
            default:
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemWrapper iw = mWrapperList.get(position);
        switch (iw.viewType) {
            case ItemWrapper.TYPE_ACTIVITIES:
                ActivitiesViewHolder viewHolder = (ActivitiesViewHolder) holder;
                Activities item = mActivitiesList.get(iw.index);
                //L.d(LOG_TAG, "position" + position + "index" + iw.index);

                viewHolder.textView.setText(item.getTitle());
//                String imgUrl = StaticClass.HEADER_IMG_URL + item.getPhoto_url();
                L.d(LOG_TAG, item.getPhoto_url());

                Picasso.with(mContext)
                        .load(item.getPhoto_url())
                        .placeholder(R.drawable.placeholder)
                        .into(viewHolder.imageView);
                break;

            case ItemWrapper.TYPE_LECTURES:
                LecturesViewHolder viewHolder1 = (LecturesViewHolder) holder;
                Lectures item1 = mLecturesList.get(iw.index);

                viewHolder1.textView.setText(item1.getTitle());
//                String imgUrl1 = StaticClass.HEADER_IMG_URL + item1.getPhoto_url();
                L.d(LOG_TAG, item1.getPhoto_url());

                Picasso.with(mContext)
                        .load(item1.getPhoto_url())
                        .placeholder(R.drawable.placeholder)
                        .into(viewHolder1.imageView);
                break;
            case ItemWrapper.TYPE_ACTIVITIES_CATEGORY:
                CategoryViewHolder cvh1 = (CategoryViewHolder) holder;
                cvh1.textViewCategory.setText(mContext.getString(R.string.activities_news));
                break;
            case ItemWrapper.TYPE_LECTURES_CATEGORY:
                CategoryViewHolder cvh2 = (CategoryViewHolder) holder;
                cvh2.textViewCategory.setText(mContext.getString(R.string.lectures_news));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mWrapperList == null ? 0 : mWrapperList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mWrapperList.get(position).viewType;
    }

    public void setOnItemClickListener(OnRecyclerViewItemOnClickListener listener) {
        this.mListener = listener;
    }

    public int getOriginalIndex(int position) {
        return mWrapperList.get(position).index;
    }

    public void updateData(List<Activities> activitiesList,
                           List<Lectures> lecturesList) {
        mActivitiesList.clear();
        mLecturesList.clear();
        mWrapperList.clear();


        mWrapperList.add(new ItemWrapper(ItemWrapper.TYPE_ACTIVITIES_CATEGORY));
        if (activitiesList.isEmpty()) {
            mWrapperList.add(new ItemWrapper(ItemWrapper.TYPE_EMPTY));
        } else {
            for (int i = 0; i < activitiesList.size(); i++) {
                ItemWrapper iw = new ItemWrapper(ItemWrapper.TYPE_ACTIVITIES);
                iw.index = i;
                mWrapperList.add(iw);
                mActivitiesList.add(activitiesList.get(i));
            }
        }

        mWrapperList.add(new ItemWrapper(ItemWrapper.TYPE_LECTURES_CATEGORY));
        if (lecturesList.isEmpty()) {
            mWrapperList.add(new ItemWrapper(ItemWrapper.TYPE_EMPTY));
        } else {
            for (int i = 0; i < lecturesList.size(); i++) {
                ItemWrapper iw = new ItemWrapper(ItemWrapper.TYPE_LECTURES);
                iw.index = i;
                mWrapperList.add(iw);
                mLecturesList.add(lecturesList.get(i));
            }
        }
        //L.d(LOG_TAG, " updateData " + "wrapperList " + mWrapperList + "mActivitiesList " + mActivitiesList + " activitiesList " + activitiesList + " mLecturesList " + mLecturesList + " lecturesList " + lecturesList);
        notifyDataSetChanged();
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

    private class EmptyViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView textViewEmpty;

        EmptyViewHolder(View itemView) {
            super(itemView);
            textViewEmpty = itemView.findViewById(R.id.text_view_empty);
        }
    }

    private class ActivitiesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView textView;
        OnRecyclerViewItemOnClickListener listener;

        ActivitiesViewHolder(View itemView, OnRecyclerViewItemOnClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_cover);
            textView = itemView.findViewById(R.id.text_view_title);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onItemClick(view, getLayoutPosition());
            }
        }
    }


    private class CategoryViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView textViewCategory;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            textViewCategory = itemView.findViewById(R.id.text_view_category);
        }
    }

    private class LecturesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView textView;
        OnRecyclerViewItemOnClickListener listener;

        public LecturesViewHolder(View itemView, OnRecyclerViewItemOnClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_cover);
            textView = itemView.findViewById(R.id.text_view_title);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClick(itemView, getLayoutPosition());
            }
        }
    }
}
