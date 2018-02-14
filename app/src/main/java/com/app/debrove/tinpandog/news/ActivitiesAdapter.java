package com.app.debrove.tinpandog.news;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.debrove.tinpandog.R;
import com.app.debrove.tinpandog.data.Activities;
import com.app.debrove.tinpandog.interfaze.OnRecyclerViewItemOnClickListener;
import com.app.debrove.tinpandog.util.DateFormatUtils;
import com.app.debrove.tinpandog.util.L;
import com.app.debrove.tinpandog.util.StaticClass;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by debrove on 2017/9/21.
 * Package Name : com.app.debrove.tinpandog.news
 * <p>
 * Adapter between the data of {@link Activities} and {@link RecyclerView}.
 */

public class ActivitiesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;

    private static final int TYPE_ITEM = 1;

    private View mHeaderView;

    @NonNull
    private final Context mContext;

    @NonNull
    private List<Activities> mList;
    private OnRecyclerViewItemOnClickListener mListener;

    public ActivitiesAdapter(@NonNull List<Activities> list, @NonNull Context context) {
        this.mList = list;
        this.mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getHeadViewSize()) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
            return new ItemViewHolder(itemView, mListener);
        } else if (viewType == TYPE_HEADER) {
            View headerView = mHeaderView;
            return new HeaderViewHolder(headerView);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
        //return new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.news_item, parent, false), mListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String time;
        long time1;

        if (holder instanceof ItemViewHolder) {
            Activities item = getItem(position);
            ItemViewHolder viewHolder = (ItemViewHolder) holder;

            viewHolder.title.setText(item.getTitle());

            time = item.getTime();
            time1 = DateFormatUtils.formatNewsDateStringToLong(time);
            viewHolder.date.setText(DateFormatUtils.formatNewsDateLongToString(time1));

//            String imgUrl = StaticClass.HEADER_IMG_URL + item.getPhoto_url();
            L.d("imgurl activities ", item.getPhoto_url());

            Picasso.with(mContext)
                    .load(item.getPhoto_url())
                    .placeholder(R.drawable.placeholder)
                    .into(viewHolder.itemImg);
            viewHolder.itemImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else if (holder instanceof HeaderViewHolder) {
        }
    }

    /**
     * 添加头部布局后的位置
     * headerView 不为空则 position - 1
     */
    private Activities getItem(int position) {
        return mList.get(position - getHeadViewSize());
    }

    private int getHeadViewSize() {
        return mHeaderView == null ? 0 : 1;
    }

    @Override
    public int getItemCount() {
        return mList.isEmpty() ? 0 : mList.size() + getHeadViewSize();
    }

    public void setItemClickListener(OnRecyclerViewItemOnClickListener listener) {
        this.mListener = listener;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private OnRecyclerViewItemOnClickListener listener;
        private TextView title;
        private TextView date;
        private ImageView itemImg;

        public ItemViewHolder(View itemView, OnRecyclerViewItemOnClickListener listener) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            date = itemView.findViewById(R.id.tv_date);
            itemImg = itemView.findViewById(R.id.iv_news_cover);
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

    private class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void updateData(@NonNull List<Activities> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
        notifyItemRemoved(list.size());
    }

    public View getmHeaderView() {
        return mHeaderView;
    }

    //add a header from the adapter
    public void addHeaderView(View header) {
        this.mHeaderView = header;
        notifyItemInserted(0);//插入下标0位置
    }

    //remove a header from the adapter
    public void removeHeader(View header) {
        notifyItemRemoved(0);
        mHeaderView = null;
    }

}
