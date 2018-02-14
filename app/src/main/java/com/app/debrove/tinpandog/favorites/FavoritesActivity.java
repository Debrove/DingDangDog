package com.app.debrove.tinpandog.favorites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.app.debrove.tinpandog.R;
import com.app.debrove.tinpandog.data.source.local.ActivitiesLocalDataSource;
import com.app.debrove.tinpandog.data.source.local.LecturesLocalDataSource;
import com.app.debrove.tinpandog.data.source.remote.ActivitiesRemoteDataSource;
import com.app.debrove.tinpandog.data.source.remote.LecturesRemoteDataSource;
import com.app.debrove.tinpandog.data.source.repository.ActivitiesRepository;
import com.app.debrove.tinpandog.data.source.repository.LecturesRepository;

/**
 * Created by debrove on 2017/11/21.
 * Package Name : com.app.debrove.tinpandog.favorites
 */

public class FavoritesActivity extends AppCompatActivity {

    private FavoritesFragment mFavoritesFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame);

        if (savedInstanceState != null) {
            mFavoritesFragment = (FavoritesFragment) getSupportFragmentManager().getFragment(savedInstanceState, FavoritesFragment.class.getSimpleName());
            //getSupportFragmentManager().beginTransaction().show(mFavoritesFragment).commit();
        } else {
            mFavoritesFragment = FavoritesFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mFavoritesFragment, FavoritesFragment.class.getSimpleName())
                    .commit();
        }
        new FavoritesPresenter(mFavoritesFragment,
                ActivitiesRepository.getInstance(ActivitiesRemoteDataSource.getInstance(),
                        ActivitiesLocalDataSource.getInstance()),
                LecturesRepository.getInstance(LecturesRemoteDataSource.getInstance(),
                        LecturesLocalDataSource.getInstance()));
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mFavoritesFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, FavoritesFragment.class.getSimpleName(), mFavoritesFragment);
        }
    }
}
