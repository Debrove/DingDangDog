package com.app.debrove.tinpandog.signup;

import android.support.annotation.NonNull;

import com.app.debrove.tinpandog.data.ContentType;
import com.app.debrove.tinpandog.data.source.repository.ActivitiesRepository;
import com.app.debrove.tinpandog.data.source.repository.LecturesRepository;
import com.app.debrove.tinpandog.util.L;

/**
 * Created by debrove on 2017/12/13.
 * Package Name : com.app.debrove.tinpandog.signup
 */

public class SignUpPresenter implements SignUpContract.Presenter {

    private static final String LOG_TAG = SignUpPresenter.class.getSimpleName();

    @NonNull
    private final SignUpContract.View mView;

    @NonNull
    private ActivitiesRepository mActivitiesRepository;

    @NonNull
    private LecturesRepository mLecturesRepository;


    public SignUpPresenter(@NonNull SignUpContract.View view,
                           @NonNull ActivitiesRepository activitiesRepository) {
        this.mView = view;
        mView.setPresenter(this);
        this.mActivitiesRepository = activitiesRepository;

    }

    public SignUpPresenter(@NonNull SignUpContract.View view,
                           @NonNull LecturesRepository lecturesRepository) {
        this.mView = view;
        mView.setPresenter(this);
        this.mLecturesRepository = lecturesRepository;
    }

    @Override
    public void start() {

    }


    @Override
    public void updateSignUpInfo(ContentType type, int id, boolean signUp, String token) {
        if (type == ContentType.TYPE_ACTIVITIES) {
            L.d(LOG_TAG, "activities " + id + " signUp " + signUp + " token " + token);
            mActivitiesRepository.signUpItem(id, signUp, token);
        } else if (type == ContentType.TYPE_LECTURES) {
            L.d(LOG_TAG, "lectures " + id + " signUp " + signUp + " token " + token);
            mLecturesRepository.signUpItem(id, signUp, token);
        }
    }
}
