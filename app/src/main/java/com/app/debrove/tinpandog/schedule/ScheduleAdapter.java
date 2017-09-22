//package com.app.debrove.tinpandog.schedule;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.app.debrove.tinpandog.R;
//
//import java.util.List;
//
///**
// * Created by debrove on 2017/7/24.
// * Package Name : com.app.debrove.tinpandog.schedule
// */
//
//class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
//
//    private final Context mContext;
//    private List<Schedule> mList;
//
//    ScheduleAdapter(Context context, List<Schedule> list) {
//        this.mList = list;
//        this.mContext = context;
//    }
//
//    private Context getContext() {
//        return mContext;
//    }
//
//    @Override
//    public ScheduleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Context context=parent.getContext();
//        LayoutInflater layoutInflater = LayoutInflater.from(context);
//        return new ViewHolder(layoutInflater.inflate(R.layout.item, parent, false));
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        Schedule item= mList.get(position);
//        holder.textView.setText(item.getEvent());
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return mList == null ? 0 : mList.size();
//    }
//
//    class ViewHolder extends RecyclerView.ViewHolder {
//        private TextView textView;
//
//        ViewHolder(View itemView) {
//            super(itemView);
//            textView = (TextView) itemView.findViewById(R.id.text_view);
//        }
//    }
//}
