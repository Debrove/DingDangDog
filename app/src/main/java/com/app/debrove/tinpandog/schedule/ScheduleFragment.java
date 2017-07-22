package com.app.debrove.tinpandog.schedule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.debrove.tinpandog.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by debrove on 2017/7/17.
 * Package Name : com.app.debrove.tinpandog.schedule
 */

public class ScheduleFragment extends Fragment implements ScheduleContract.View {
    @BindView(R.id.tv_schedule)
    TextView tvSchedule;
    Unbinder unbinder;

    public static ScheduleFragment newInstance(){
        return new ScheduleFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void setPresenter(ScheduleContract.Presenter presenter) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
