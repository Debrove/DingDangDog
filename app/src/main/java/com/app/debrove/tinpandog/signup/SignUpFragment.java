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


/**
 * Created by debrove on 2017/12/13.
 * Package Name : com.app.debrove.tinpandog.signup
 */

public class SignUpFragment extends Fragment implements SignUpContract.View, View.OnClickListener {

    private static final String LOG_TAG = SignUpFragment.class.getSimpleName();

    private Toolbar mToolbar;
    private TextView mTvTitle;
    private TextView mTvTime;
    private EditText mEtUsername;
    private EditText mEtTelephone;
    private EditText mEtStuNum;
    private Button mBtnConfirmInfo;
    private Button mBtnBack;

    private SignUpContract.Presenter mPresenter;

    private ContentType mType;
    private String token;
    private String mTitle;
    private String mTime;
    private String mTelephone;
    private String mUsername;
    private String mStuNum;
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

        mTelephone = ShareUtils.getString(getContext(), StaticClass.KEY_USER_TELEPHONE, "");
        mUsername = ShareUtils.getString(getContext(), StaticClass.KEY_USERNAME, "");
        mStuNum = ShareUtils.getString(getContext(), StaticClass.KEY_STU_NUM, "");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        initView(view);

        return view;
    }

    private void initView(View v) {
        mToolbar = v.findViewById(R.id.toolbar);
        mTvTitle = v.findViewById(R.id.tv_title);
        mTvTime = v.findViewById(R.id.tv_time);
        mBtnConfirmInfo = v.findViewById(R.id.btn_confirm_info);
        mBtnBack = v.findViewById(R.id.btn_back);
        mEtTelephone = v.findViewById(R.id.et_telephone);
        mEtUsername = v.findViewById(R.id.et_username);
        mEtStuNum = v.findViewById(R.id.et_stu_num);

        setHasOptionsMenu(true);
        SignUpActivity activity = (SignUpActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        L.d(LOG_TAG, "telephone " + mTelephone + "username " + mUsername + "num " + mStuNum);
        mTvTitle.setText(mTitle);
        mTvTime.setText(mTime);
        mEtTelephone.setText(mTelephone);
        mEtUsername.setText(mUsername);
        mEtTelephone.setEnabled(false);

        //学号可为空
        if (mStuNum != null) {
            mEtStuNum.setText(mStuNum);
        }

        if (isPreSignUp) {
            mBtnConfirmInfo.setEnabled(false);
            mBtnConfirmInfo.setText("预报名成功");
        }

        mBtnConfirmInfo.setOnClickListener(this);
        mBtnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                getActivity().onBackPressed();
                break;
            case R.id.btn_confirm_info:
                L.d(LOG_TAG, mType + " id " + mId + " signedUp " + isPreSignUp + " token " + token);
                if (!isPreSignUp) {
                    isPreSignUp = true;
                    mPresenter.updateSignUpInfo(mType, mId, isPreSignUp, token);
                }
                break;
        }
    }

    @Override
    public void showSignUpMessage(String message) {
        if (message.equals("报名成功")) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            mBtnConfirmInfo.setEnabled(false);
            mBtnConfirmInfo.setText("预报名成功");
        } else {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void getToken(String token) {
        //刷新token
        L.d(LOG_TAG, " new token " + token);
        ShareUtils.putString(getContext(), StaticClass.KEY_ACCESS_TOKEN, token);
    }

    @Override
    public void refreshToken() {
        mPresenter.refreshToken(mType, mTelephone);
        L.d(LOG_TAG, "token in refreshing " + token);
    }

    @Override
    public void refreshSignUp(String token) {
        L.d(LOG_TAG, " token " + token);
        mPresenter.updateSignUpInfo(mType, mId, isPreSignUp, token);
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
    }


}
