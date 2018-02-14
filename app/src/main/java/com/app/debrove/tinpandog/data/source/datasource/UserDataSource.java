package com.app.debrove.tinpandog.data.source.datasource;

import android.support.annotation.NonNull;

import com.app.debrove.tinpandog.data.User;

import java.util.List;


/**
 * Created by cp4yin on 2017/12/17.
 */

public interface UserDataSource {

    interface LoadUserInfoCallback {
        void onInfoLoaded(@NonNull List<User> userInfo);

        void onDataNotAvailable();
    }

    interface LoadTokenCallback {
        void onInfoLoaded(@NonNull String token);

        void onDataNotAvailable();
    }

    void getUserInfo(String telephone, String token, LoadUserInfoCallback callback);

    void refreshToken(String telephone,LoadTokenCallback callback);
}
