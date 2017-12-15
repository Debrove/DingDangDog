package com.app.debrove.tinpandog.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.app.debrove.tinpandog.R;
import com.app.debrove.tinpandog.data.ContentType;
import com.app.debrove.tinpandog.data.source.local.ActivitiesLocalDataSource;
import com.app.debrove.tinpandog.data.source.local.LecturesLocalDataSource;
import com.app.debrove.tinpandog.data.source.remote.ActivitiesRemoteDataSource;
import com.app.debrove.tinpandog.data.source.remote.LecturesRemoteDataSource;
import com.app.debrove.tinpandog.data.source.repository.ActivitiesRepository;
import com.app.debrove.tinpandog.data.source.repository.LecturesRepository;

/**
 * Created by debrove on 2017/10/5.
 * Package Name : com.app.debrove.tinpandog.details
 */

public class DetailsActivity extends AppCompatActivity {

    public static final String KEY_ARTICLE_ID = "KEY_ARTICLE_ID";
    public static final String KEY_ARTICLE_TYPE = "KEY_ARTICLE_TYPE";
    public static final String KEY_ARTICLE_TITLE = "KEY_ARTICLE_TITLE";
    public static final String KEY_ARTICLE_TIME = "KEY_ARTICLE_TIME";
    public static final String KEY_ARTICLE_PLACE = "KEY_ARTICLE_PLACE";
    public static final String KEY_ARTICLE_CONTENT = "KEY_ARTICLE_CONTENT";
    public static final String KEY_ARTICLE_IMAGE = "KEY_ARTICLE_IMAGE";
    public static final String KEY_COUNT_PRE_SIGN_UP = "KEY_COUNT_PRE_SIGN_UP";
    public static final String KEY_ARTICLE_IS_FAVORITE = "KEY_ARTICLE_IS_FAVORITE";
    public static final String KEY_ARTICLE_IS_PRE_SIGN_UP = "KEY_ARTICLE_IS_PRE_SIGN_UP";

    private DetailsFragment mDetailsFragment;

    private ContentType mType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame);

        if (savedInstanceState != null) {
            mDetailsFragment = (DetailsFragment) getSupportFragmentManager().getFragment(savedInstanceState, DetailsFragment.class.getSimpleName());
        } else {
            mDetailsFragment = DetailsFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mDetailsFragment, DetailsFragment.class.getSimpleName())
                    .commit();
        }

        mType = (ContentType) getIntent().getSerializableExtra(KEY_ARTICLE_TYPE);
        if (mType == ContentType.TYPE_ACTIVITIES) {
            new DetailsPresenter(mDetailsFragment,
                    ActivitiesRepository.getInstance(ActivitiesRemoteDataSource.getInstance(),
                            ActivitiesLocalDataSource.getInstance()));
        } else if (mType == ContentType.TYPE_LECTURES) {
            new DetailsPresenter(mDetailsFragment,
                    LecturesRepository.getInstance(LecturesRemoteDataSource.getInstance(),
                            LecturesLocalDataSource.getInstance()));
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivitiesRepository.destroyInstance();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mDetailsFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, DetailsFragment.class.getSimpleName(), mDetailsFragment);
        }
    }
}
