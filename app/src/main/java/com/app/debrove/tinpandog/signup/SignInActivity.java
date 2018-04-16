package com.app.debrove.tinpandog.signup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.app.debrove.tinpandog.R;
import com.app.debrove.tinpandog.data.ContentType;

/**
 * Created by cp4yin on 2017/12/18.
 *
 * 签到
 */

public class SignInActivity extends AppCompatActivity {

    public static final String KEY_ARTICLE_TYPE = "KEY_ARTICLE_TYPE";
    public static final String KEY_ARTICLE_ID = "KEY_ARTICLE_ID";

    private SignInFragment mSignInFragment;

    private ContentType mType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame);

        if (savedInstanceState != null) {
            mSignInFragment = (SignInFragment) getSupportFragmentManager().getFragment(savedInstanceState, SignInFragment.class.getSimpleName());
        } else {
            mSignInFragment = SignInFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mSignInFragment, SignInFragment.class.getSimpleName())
                    .commit();
        }

        mType = (ContentType) getIntent().getSerializableExtra(KEY_ARTICLE_TYPE);
        if (mType == ContentType.TYPE_ACTIVITIES) {

        } else if (mType == ContentType.TYPE_LECTURES) {

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mSignInFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, SignInFragment.class.getSimpleName(), mSignInFragment);
        }
    }
}
