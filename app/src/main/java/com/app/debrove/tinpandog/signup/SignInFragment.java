package com.app.debrove.tinpandog.signup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.app.debrove.tinpandog.R;
import com.app.debrove.tinpandog.util.L;
import com.app.debrove.tinpandog.view.CustomEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by cp4yin on 2017/12/18.
 * <p>
 * 签到
 */

public class SignInFragment extends Fragment {

    private static final String LOG_TAG = SignInFragment.class.getSimpleName();


    private Toolbar mToolbar;
    private CustomEditText mCustomEditText;

    private List<Map<String, Object>> dataList;
    private SimpleAdapter mAdapter;

    public SignInFragment() {
        // Requires an empty constructor.
    }

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);

        initView(view);

        return view;
    }


    private void initView(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        mCustomEditText = view.findViewById(R.id.custom_et);

        setHasOptionsMenu(true);
        SignInActivity activity = (SignInActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCustomEditText.setOnNumFinishListener(new CustomEditText.OnNumFinishListener() {
            @Override
            public void onComplete(String content) {
                L.d(LOG_TAG, content);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
