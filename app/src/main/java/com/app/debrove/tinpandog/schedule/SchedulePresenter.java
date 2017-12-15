package com.app.debrove.tinpandog.schedule;

import android.support.annotation.NonNull;

import com.app.debrove.tinpandog.data.Activities;
import com.app.debrove.tinpandog.data.Lectures;
import com.app.debrove.tinpandog.data.source.datasource.ActivitiesDataSource;
import com.app.debrove.tinpandog.data.source.datasource.LecturesDataSource;
import com.app.debrove.tinpandog.data.source.repository.ActivitiesRepository;
import com.app.debrove.tinpandog.data.source.repository.LecturesRepository;
import com.app.debrove.tinpandog.util.L;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by debrove on 2017/7/17.
 * Package Name : com.app.debrove.tinpandog.schedule
 */

public class SchedulePresenter implements ScheduleContract.Presenter {

    private static final String LOG_TAG = SchedulePresenter.class.getSimpleName();

    @NonNull
    private final ScheduleContract.View mView;

    @NonNull
    private final ActivitiesRepository mActivitiesRepository;

    @NonNull
    private final LecturesRepository mLecturesRepository;

    public SchedulePresenter(@NonNull ScheduleContract.View view,
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
    public void loadList(final long date) {
        mActivitiesRepository.getNewsSignedUp(date, new ActivitiesDataSource.LoadNewsCallback() {
            @Override
            public void onNewsLoaded(@NonNull final List<Activities> activitiesList) {
                mLecturesRepository.getNewsSignedUp(date, new LecturesDataSource.LoadNewsCallback() {
                    @Override
                    public void onNewsLoaded(@NonNull List<Lectures> lecturesList) {
                        L.d(LOG_TAG, "loadSchedule" + activitiesList + "lectures " + lecturesList);
                        if (mView.isActive()) {
                            mView.showList(activitiesList, lecturesList);
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
