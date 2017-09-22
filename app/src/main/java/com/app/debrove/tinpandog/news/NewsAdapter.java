package com.app.debrove.tinpandog.news;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.debrove.tinpandog.R;
import com.app.debrove.tinpandog.data.News;
import com.app.debrove.tinpandog.interfaze.OnRecyclerViewItemOnClickListener;

import java.util.List;

/**
 * Created by debrove on 2017/9/21.
 * Package Name : com.app.debrove.tinpandog.news
 *
 * Adapter between the data of {@link News} and {@link RecyclerView}.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @NonNull
    private final Context mContext;

    @NonNull
    private List<News> mList;
    private OnRecyclerViewItemOnClickListener mListener;

    public NewsAdapter(@NonNull List<News> list, @NonNull Context context) {
        this.mList = list;
        this.mContext = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item, parent, false), mListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        News item=mList.get(position);
        ItemViewHolder viewHolder= (ItemViewHolder) holder;

        viewHolder.title.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return mList.isEmpty() ? 0 : mList.size();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView title;

        public ItemViewHolder(View itemView, OnRecyclerViewItemOnClickListener listener) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }

    public void updateData(@NonNull List<News> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
        notifyItemRemoved(list.size());
    }
}
