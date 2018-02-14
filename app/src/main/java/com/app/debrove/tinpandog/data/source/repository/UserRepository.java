package com.app.debrove.tinpandog.data.source.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.app.debrove.tinpandog.data.User;
import com.app.debrove.tinpandog.data.source.datasource.UserDataSource;
import com.app.debrove.tinpandog.util.L;
import com.app.debrove.tinpandog.util.ShareUtils;

import java.util.List;

/**
 * Created by cp4yin on 2017/12/17.
 */

public class UserRepository implements UserDataSource {

    private static final String LOG_TAG = UserRepository.class.getSimpleName();

    @Nullable
    private static UserRepository INSTANCE = null;

    @NonNull
    private final UserDataSource mRemoteDataSource;

    @NonNull
    private final UserDataSource mLocalDataSource;


    // Prevent direct instantiation.
    public UserRepository(@NonNull UserDataSource mRemoteDataSource,
                          @NonNull UserDataSource mLocalDataSource) {
        this.mRemoteDataSource = mRemoteDataSource;
        this.mLocalDataSource = mLocalDataSource;
    }

    public static UserRepository getInstance(@NonNull UserDataSource remoteDataSource,
                                             @NonNull UserDataSource localDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new UserRepository(remoteDataSource, localDataSource);
        }
        return INSTANCE;
    }


    @Override
    public void getUserInfo(final String telephone, final String token, final LoadUserInfoCallback callback) {
        //Get data by accessing local DB first.
        mLocalDataSource.getUserInfo(telephone, token, new LoadUserInfoCallback() {
            @Override
            public void onInfoLoaded(@NonNull List<User> userInfo) {
                String name="";
                L.d(LOG_TAG, "user " + userInfo);
                for (User user : userInfo) {
                    name = user.getName();
                    L.d(LOG_TAG," name "+name);
                }
                if (name!=null){
                    callback.onInfoLoaded(userInfo);
                }else{
                    mRemoteDataSource.getUserInfo(telephone, token, new LoadUserInfoCallback() {
                        @Override
                        public void onInfoLoaded(@NonNull List<User> userInfo) {
                            L.d(LOG_TAG,"remote user info"+userInfo);
                            for (User user : userInfo) {
                                L.d(LOG_TAG," name "+user.getName());
                            }
                            callback.onInfoLoaded(userInfo);
                        }

                        @Override
                        public void onDataNotAvailable() {
                            //refresh token
                            callback.onDataNotAvailable();
                        }
                    });
                }
            }

            @Override
            public void onDataNotAvailable() {
                //From network
                mRemoteDataSource.getUserInfo(telephone, token, new LoadUserInfoCallback() {
                    @Override
                    public void onInfoLoaded(@NonNull List<User> userInfo) {
                        callback.onInfoLoaded(userInfo);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        //刷新token
                        callback.onDataNotAvailable();
                    }
                });
            }
        });
    }

    @Override
    public void refreshToken(String telephone, final LoadTokenCallback callback) {
        mRemoteDataSource.refreshToken(telephone, new LoadTokenCallback() {
            @Override
            public void onInfoLoaded(@NonNull String token) {
                callback.onInfoLoaded(token);
                L.d(LOG_TAG, " token " + token);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
                L.d(LOG_TAG, "token error");
            }
        });

    }
}
