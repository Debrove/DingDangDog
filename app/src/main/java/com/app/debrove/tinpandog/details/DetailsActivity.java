package com.app.debrove.tinpandog.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.app.debrove.tinpandog.R;
import com.app.debrove.tinpandog.data.source.remote.NewsRemoteDataSource;
import com.app.debrove.tinpandog.data.source.repository.NewsRepository;

/**
 * Created by debrove on 2017/10/5.
 * Package Name : com.app.debrove.tinpandog.details
 */

public class DetailsActivity extends AppCompatActivity {

    public static final String KEY_ARTICLE_ID = "KEY_ARTICLE_ID";
    public static final String KEY_ARTICLE_TITLE = "KEY_ARTICLE_TITLE";
    public static final String KEY_ARTICLE_CONTENT = "KEY_ARTICLE_CONTENT";
    public static final String KEY_ARTICLE_IMAGE = "KEY_ARTICLE_IMAGE";
    public static final String KEY_COUNT_PRE_SIGN_UP = "KEY_COUNT_PRE_SIGN_UP";

    private DetailsFragment mDetailsFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame);

        if (savedInstanceState != null) {
            mDetailsFragment = (DetailsFragment) getSupportFragmentManager().getFragment(savedInstanceState, DetailsFragment.class.getSimpleName());
        } else {
            mDetailsFragment=DetailsFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container,mDetailsFragment,DetailsFragment.class.getSimpleName())
                    .commit();
        }

//        new DetailsPresenter(mDetailsFragment,
//                NewsRepository.getInstance(NewsRemoteDataSource.getInstance()))
    }


}
