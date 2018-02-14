package com.app.debrove.tinpandog.data.source.local;

import android.support.annotation.Nullable;

import com.app.debrove.tinpandog.data.User;
import com.app.debrove.tinpandog.data.source.datasource.UserDataSource;
import com.app.debrove.tinpandog.util.L;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by cp4yin on 2017/12/17.
 */

public class UserLocalDataSource implements UserDataSource {

    private static final String LOG_TAG = UserLocalDataSource.class.getSimpleName();

    @Nullable
    private static UserLocalDataSource INSTANCE = null;

    private UserLocalDataSource() {
    }

    public static UserLocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserLocalDataSource();
        }
        return INSTANCE;
    }


    @Override
    public void getUserInfo(String telephone, String token, LoadUserInfoCallback callback) {
        L.d(LOG_TAG, "telephone " + telephone);
        List<User> userList = DataSupport.where("telephone = ?", telephone).find(User.class);
        L.d(LOG_TAG, "userList" + userList);
        for (User user : userList) {
            String name = user.getName();
            L.d(LOG_TAG, "name " + name);
            //String stuNum = user.getNumber();
        }
        callback.onInfoLoaded(userList);
    }

    @Override
    public void refreshToken(String telephone, LoadTokenCallback callback) {

    }
}
