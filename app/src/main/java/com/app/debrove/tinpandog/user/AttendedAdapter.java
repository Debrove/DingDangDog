package com.app.debrove.tinpandog.user;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;


/**
 * Created by cp4yin on 2018/4/10.
 * package ：com.app.debrove.tinpandog.user
 * description：
 */

class AttendedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final String LOG_TAG = AttendedAdapter.class.getSimpleName();
    @NonNull
    private final Context mContext;

    AttendedAdapter(@NonNull Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
