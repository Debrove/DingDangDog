package com.app.debrove.tinpandog.favorites;

import android.support.annotation.NonNull;

import com.app.debrove.tinpandog.data.Activities;
import com.app.debrove.tinpandog.data.Lectures;
import com.app.debrove.tinpandog.data.source.datasource.ActivitiesDataSource;
import com.app.debrove.tinpandog.data.source.datasource.LecturesDataSource;
import com.app.debrove.tinpandog.data.source.repository.ActivitiesRepository;
import com.app.debrove.tinpandog.data.source.repository.LecturesRepository;
import com.app.debrove.tinpandog.util.L;

import java.util.List;

/**
 * Created by debrove on 2017/11/21.
 * Package Name : com.app.debrove.tinpandog.favorites
 */

public class FavoritesPresenter implements FavoritesContract.Presenter {

    private static final String LOG_TAG = FavoritesPresenter.class.getSimpleName();
    @NonNull
    private final FavoritesContract.View mView;

    @NonNull
    private final ActivitiesRepository mActivitiesRepository;

    @NonNull
    private final LecturesRepository mLecturesRepository;

    public FavoritesPresenter(@NonNull FavoritesContract.View view,
                              @NonNull ActivitiesRepository activitiesRepository,
                              @NonNull LecturesRepository lecturesRepository) {
        this.mView = view;
        mView.setPresenter(this);
        this.mActivitiesRepository = activitiesRepository;
        this.mLecturesRepository = lecturesRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void loadFavorites() {
        mActivitiesRepository.getFavorites(new ActivitiesDataSource.LoadNewsCallback() {
            @Override
            public void onNewsLoaded(@NonNull final List<Activities> activitiesList) {

                mLecturesRepository.getFavorites(new LecturesDataSource.LoadNewsCallback() {
                    @Override
                    public void onNewsLoaded(@NonNull List<Lectures> lecturesList) {
                        L.d(LOG_TAG, "loadFavorites" + activitiesList + "lectures " + lecturesList);
                        if (mView.isActive()) {
                            mView.showFavorites(activitiesList, lecturesList);
                            mView.setLoadingIndicator(false);
                        }
                    }

                    @Override
                    public void onDataNotAvailable() {
                        mView.setLoadingIndicator(false);
                    }
                });
            }

            @Override
            public void onDataNotAvailable() {
            }
        });
    }
}
