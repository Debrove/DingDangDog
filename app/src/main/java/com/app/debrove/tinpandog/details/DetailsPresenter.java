package com.app.debrove.tinpandog.details;

import android.support.annotation.NonNull;

import com.app.debrove.tinpandog.data.ContentType;
import com.app.debrove.tinpandog.data.source.repository.ActivitiesRepository;
import com.app.debrove.tinpandog.data.source.repository.LecturesRepository;
import com.app.debrove.tinpandog.util.L;

/**
 * Created by debrove on 2017/10/5.
 * Package Name : com.app.debrove.tinpandog.details
 */

public class DetailsPresenter implements DetailsContract.Presenter {

    private static final String LOG_TAG = DetailsPresenter.class.getSimpleName();

    @NonNull
    private final DetailsContract.View mView;

    @NonNull
    private ActivitiesRepository mActivitiesRepository;

    @NonNull
    private LecturesRepository mLecturesRepository;


    public DetailsPresenter(@NonNull DetailsContract.View view,
                            @NonNull ActivitiesRepository activitiesRepository) {
        this.mView = view;
        mView.setPresenter(this);
        this.mActivitiesRepository = activitiesRepository;

    }

    public DetailsPresenter(@NonNull DetailsContract.View view,
                            @NonNull LecturesRepository lecturesRepository) {
        this.mView = view;
        mView.setPresenter(this);
        this.mLecturesRepository = lecturesRepository;
    }


    @Override
    public void start() {

    }

    @Override
    public void favorite(ContentType type, int id, boolean favorite, String title) {
        if (type == ContentType.TYPE_ACTIVITIES) {
            L.d(LOG_TAG, "activities " + id + "  " + favorite);
            mActivitiesRepository.favoriteItem(id, favorite, title);
        } else if (type == ContentType.TYPE_LECTURES) {
            L.d(LOG_TAG, "lectures " + id + "  " + favorite);
            mLecturesRepository.favoriteItem(id, favorite, title);
        }
    }

}
