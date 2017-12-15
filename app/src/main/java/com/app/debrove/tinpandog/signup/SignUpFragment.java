package com.app.debrove.tinpandog.signup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.debrove.tinpandog.R;
import com.app.debrove.tinpandog.data.ContentType;
import com.app.debrove.tinpandog.util.L;
import com.app.debrove.tinpandog.util.ShareUtils;
import com.app.debrove.tinpandog.util.StaticClass;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by debrove on 2017/12/13.
 * Package Name : com.app.debrove.tinpandog.signup
 */

public class SignUpFragment extends Fragment implements SignUpContract.View {

    private static final String LOG_TAG = SignUpFragment.class.getSimpleName();

    //@BindView(R.id.toolbar)
    private Toolbar mToolbar;
    //@BindView(R.id.tv_title)
    private TextView mTvTitle;
    //@BindView(R.id.tv_time)
    private TextView mTvTime;
    @BindView(R.id.et_username)
    EditText mEtUsername;
    @BindView(R.id.et_telephone)
    EditText mEtTelephone;
    @BindView(R.id.et_stu_num)
    EditText mEtStuNum;
    Unbinder unbinder;
    //@BindView(R.id.btn_confirm_info)
    private Button mBtnConfirmInfo;

    private SignUpContract.Presenter mPresenter;

    private ContentType mType;
    private String token;
    private String mTitle;
    private String mTime;
    private int mId;

    private boolean isPreSignUp;

    public SignUpFragment() {
        // Requires an empty constructor.
    }

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        token = ShareUtils.getString(getContext(), StaticClass.KEY_ACCESS_TOKEN, "0");
        mType = (ContentType) getActivity().getIntent().getSerializableExtra(SignUpActivity.KEY_ARTICLE_TYPE);
        mTitle = getActivity().getIntent().getStringExtra(SignUpActivity.KEY_ARTICLE_TITLE);
        mTime = getActivity().getIntent().getStringExtra(SignUpActivity.KEY_ARTICLE_TIME);
        mId = getActivity().getIntent().getIntExtra(SignUpActivity.KEY_ARTICLE_ID, -1);
        isPreSignUp = getActivity().getIntent().getBooleanExtra(SignUpActivity.KEY_ARTICLE_IS_PRE_SIGN_UP, false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        initView(view);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void initView(View v) {
        mToolbar = v.findViewById(R.id.toolbar);
        mTvTitle = v.findViewById(R.id.tv_title);
        mTvTime = v.findViewById(R.id.tv_time);
        mBtnConfirmInfo = v.findViewById(R.id.btn_confirm_info);

        setHasOptionsMenu(true);
        SignUpActivity activity = (SignUpActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTvTitle.setText(mTitle);
        mTvTime.setText(mTime);
        if (isPreSignUp) {
            mBtnConfirmInfo.setEnabled(false);
            mBtnConfirmInfo.setText("预报名成功");
        }
    }

    @OnClick({R.id.btn_back, R.id.btn_confirm_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                getActivity().onBackPressed();
                break;
            case R.id.btn_confirm_info:
                L.d(LOG_TAG, mType + " id " + mId + " signedUp " + isPreSignUp + " token " + token);
                if (!isPreSignUp) {
                    isPreSignUp = true;
                    mPresenter.updateSignUpInfo(mType, mId, isPreSignUp, token);
                    Toast.makeText(getContext(), "预报名成功", Toast.LENGTH_SHORT).show();
                    mBtnConfirmInfo.setEnabled(false);
                    mBtnConfirmInfo.setText("预报名成功");
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                L.d(LOG_TAG, "back");
                getActivity().onBackPressed();
                return true;
        }
        return true;
    }

    @Override
    public void setPresenter(SignUpContract.Presenter presenter) {
        if (presenter != null) {
            mPresenter = presenter;
        }
    }

    @Override
    public boolean isActive() {
        return isAdded() && isResumed();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
