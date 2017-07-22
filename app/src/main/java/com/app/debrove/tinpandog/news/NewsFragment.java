package com.app.debrove.tinpandog.news;

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
 * Package Name : com.app.debrove.tinpandog.news
 */

public class NewsFragment extends Fragment implements NewsContract.View {
    @BindView(R.id.tv)
    TextView tv;
    Unbinder unbinder;

    public static NewsFragment newInstance(){
        return new NewsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void setPresenter(NewsContract.Presenter presenter) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
