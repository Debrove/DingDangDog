package com.app.debrove.tinpandog.user;

import android.support.annotation.NonNull;

import com.app.debrove.tinpandog.data.User;
import com.app.debrove.tinpandog.data.source.datasource.UserDataSource;
import com.app.debrove.tinpandog.data.source.repository.UserRepository;
import com.app.debrove.tinpandog.util.L;

import java.util.List;

/**
 * Created by cp4yin on 2017/12/17.
 */

public class UserPresenter implements UserContract.Presenter {

    private static final String LOG_TAG = UserPresenter.class.getSimpleName();
    @NonNull
    private final UserContract.View mView;

    @NonNull
    private final UserRepository mUserRepository;

    public UserPresenter(@NonNull UserContract.View mView,
                               @NonNull UserRepository userRepository) {
        this.mView = mView;
        this.mUserRepository = userRepository;
        this.mView.setPresenter(this);
    }

    @Override
    public void start() {

    }


    @Override
    public void loadUserInfo(final String telephone, final String token) {
        mUserRepository.getUserInfo(telephone, token, new UserDataSource.LoadUserInfoCallback() {
            @Override
            public void onInfoLoaded(@NonNull List<User> userInfo) {
                    L.d(LOG_TAG," user info "+userInfo);
                    mView.showInfo(userInfo);
            }

            @Override
            public void onDataNotAvailable() {
                //刷新token
                mView.refreshToken();
            }
        });
    }

    @Override
    public void refreshToken(String telephone) {
        mUserRepository.refreshToken(telephone, new UserDataSource.LoadTokenCallback() {
            @Override
            public void onInfoLoaded(@NonNull String token) {
                L.d(LOG_TAG," token  "+token);
                    mView.getToken(token);
            }

            @Override
            public void onDataNotAvailable() {
                L.d(LOG_TAG,"刷新token失败");
            }
        });
    }
}
