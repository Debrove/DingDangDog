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
//import com.app.debrove.tinpandog.data.Schedule;
//import com.ldf.calendar.model.CalendarDate;
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
//    private CalendarDate currentDate;
//
//    ScheduleAdapter(Context context) {
////        this.mList = list;
//        this.mContext = context;
//    }
//
//    private Context getContext() {
//        return mContext;
//    }
//
//    @Override
//    public ScheduleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Context context = parent.getContext();
//        LayoutInflater layoutInflater = LayoutInflater.from(context);
//        return new ViewHolder(layoutInflater.inflate(R.layout.schedule_item, parent, false));
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        Schedule item = mList.get(position);
//        holder.showMonthView.setText(currentDate.getMonth());
//        holder.showDayView.setText(currentDate.getDay());
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return mList == null ? 0 : mList.size();
//    }
//
//    class ViewHolder extends RecyclerView.ViewHolder {
//        private TextView showMonthView;
//        private TextView showDayView;
//
//        ViewHolder(View itemView) {
//            super(itemView);
//            showMonthView = itemView.findViewById(R.id.show_month_view);
//            showDayView = itemView.findViewById(R.id.show_day_view);
//        }
//    }
//}
