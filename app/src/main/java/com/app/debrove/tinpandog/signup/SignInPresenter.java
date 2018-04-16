package com.app.debrove.tinpandog.signup;

import android.support.annotation.NonNull;

import com.app.debrove.tinpandog.data.ContentType;
import com.app.debrove.tinpandog.data.source.datasource.ActivitiesDataSource;
import com.app.debrove.tinpandog.data.source.datasource.LecturesDataSource;
import com.app.debrove.tinpandog.data.source.repository.ActivitiesRepository;
import com.app.debrove.tinpandog.data.source.repository.LecturesRepository;
import com.app.debrove.tinpandog.util.L;

/**
 * Created by cp4yin on 2018/4/12.
 * package ：com.app.debrove.tinpandog.signup
 * description：
 */

public class SignInPresenter implements SignInContract.Presenter {

    private static final String LOG_TAG = SignInPresenter.class.getSimpleName();

    @NonNull
    private final SignInContract.View mView;

    @NonNull
    private ActivitiesRepository mActivitiesRepository;

    @NonNull
    private LecturesRepository mLecturesRepository;


    public SignInPresenter(@NonNull SignInContract.View view,
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
    public void updateSignInInfo(ContentType type, int id, String address, boolean signIn, String token) {
        if (type == ContentType.TYPE_ACTIVITIES) {
            L.d(LOG_TAG, "activities " + id + " signIn " + signIn + " token " + token);
            mActivitiesRepository.signInItem(id, address, signIn, token, new ActivitiesDataSource.LoadMessageCallback() {
                @Override
                public void onMessageLoaded(int status, @NonNull String message) {
                    switch (status) {
                        case 10004:
                            //status=10004，token过期
                            mView.refreshToken();
                            break;
                        case 1:
                            //报名成功
                            L.d(LOG_TAG, " message " + message);
                            mView.showSignInMessage(status, message);
                            break;
                        case 70003:
                            L.d(LOG_TAG, " message " + message);
                            mView.showSignInMessage(status, message);
                            break;
                        default:
                            break;
                    }
                }

                @Override
                public void onDataNotAvailable() {
                    mView.refreshToken();
                }
            });
        } else if (type == ContentType.TYPE_LECTURES) {
//            L.d(LOG_TAG, "lectures " + id + " signIn " + signIn + " token " + token);
//            mLecturesRepository.signInItem(id, address, signIn, token, new LecturesRepository().LoadMessageCallback() {
//                @Override
//                public void onMessageLoaded(int status, @NonNull String message) {
//                    switch (status) {
//                        case 10004:
//                            //status=10004，token过期
//                            mView.refreshToken();
//                            break;
//                        case 1:
//                            //报名成功
//                            L.d(LOG_TAG, " message " + message);
//                            mView.showSignInMessage(status, message);
//                            break;
//                        case 70003:
//                            L.d(LOG_TAG, " message " + message);
//                            mView.showSignInMessage(status, message);
//                            break;
//                        default:
//                            break;
//                    }
//                }
//
//                @Override
//                public void onDataNotAvailable() {
//                    mView.refreshToken();
//                }
//            });
        }
    }

    @Override
    public void refreshToken(String telephone) {

        mActivitiesRepository.refreshToken(telephone, new ActivitiesDataSource.LoadTokenCallback() {
            @Override
            public void onInfoLoaded(@NonNull String token) {
                L.d(LOG_TAG, " token  " + token);
                mView.getToken(token);
                mView.refreshSignIn(token);
            }

            @Override
            public void onDataNotAvailable() {
                L.d(LOG_TAG, "刷新token失败");
            }
        });

    }
}
