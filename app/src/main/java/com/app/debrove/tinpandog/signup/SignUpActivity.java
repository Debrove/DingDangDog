package com.app.debrove.tinpandog.signup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.debrove.tinpandog.R;
import com.app.debrove.tinpandog.data.Activities;
import com.app.debrove.tinpandog.data.ContentType;
import com.app.debrove.tinpandog.data.LoginResponse;
import com.app.debrove.tinpandog.data.source.local.ActivitiesLocalDataSource;
import com.app.debrove.tinpandog.data.source.local.LecturesLocalDataSource;
import com.app.debrove.tinpandog.data.source.remote.ActivitiesRemoteDataSource;
import com.app.debrove.tinpandog.data.source.remote.LecturesRemoteDataSource;
import com.app.debrove.tinpandog.data.source.repository.ActivitiesRepository;
import com.app.debrove.tinpandog.data.source.repository.LecturesRepository;
import com.app.debrove.tinpandog.retrofit.RetrofitService;
import com.app.debrove.tinpandog.util.L;
import com.app.debrove.tinpandog.util.ShareUtils;
import com.app.debrove.tinpandog.util.StaticClass;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by debrove on 2017/12/10.
 * Package Name : com.app.debrove.tinpandog.signup
 * <p>
 * 预报名
 */

public class SignUpActivity extends AppCompatActivity {

    public static final String KEY_ARTICLE_TYPE = "KEY_ARTICLE_TYPE";
    public static final String KEY_ARTICLE_ID = "KEY_ARTICLE_ID";
    public static final String KEY_ARTICLE_TIME = "KEY_ARTICLE_TIME";
    public static final String KEY_ARTICLE_TITLE = "KEY_ARTICLE_TITLE";
    public static final String KEY_ARTICLE_IS_PRE_SIGN_UP = "KEY_ARTICLE_IS_PRE_SIGN_UP";
    public static final String KEY_USERNAME = "KEY_USERNAME";
    public static final String KEY_USER_TELEPHONE = "KEY_USER_TELEPHONE";
    public static final String KEY_USER_STU_NUM = "KEY_USER_STU_NUM";

    private SignUpFragment mSignUpFragment;

    private ContentType mType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame);
        if (savedInstanceState != null) {
            mSignUpFragment = (SignUpFragment) getSupportFragmentManager().getFragment(savedInstanceState, SignUpFragment.class.getSimpleName());
            //getSupportFragmentManager().beginTransaction().show(mFavoritesFragment).commit();
        } else {
            mSignUpFragment = SignUpFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mSignUpFragment, SignUpFragment.class.getSimpleName())
                    .commit();
        }

        mType = (ContentType) getIntent().getSerializableExtra(KEY_ARTICLE_TYPE);
        if (mType == ContentType.TYPE_ACTIVITIES) {
            new SignUpPresenter(mSignUpFragment,
                    ActivitiesRepository.getInstance(ActivitiesRemoteDataSource.getInstance(),
                            ActivitiesLocalDataSource.getInstance()));
        } else if (mType == ContentType.TYPE_LECTURES) {
            new SignUpPresenter(mSignUpFragment,
                    LecturesRepository.getInstance(LecturesRemoteDataSource.getInstance(),
                            LecturesLocalDataSource.getInstance()));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mSignUpFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, SignUpFragment.class.getSimpleName(), mSignUpFragment);
        }
    }
}
