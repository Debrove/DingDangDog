package com.app.debrove.tinpandog.signup;

import com.app.debrove.tinpandog.BasePresenter;
import com.app.debrove.tinpandog.BaseView;
import com.app.debrove.tinpandog.data.ContentType;

/**
 * Created by cp4yin on 2018/4/12.
 * package ：com.app.debrove.tinpandog.signup
 * description：
 */

public interface SignInContract {

    interface View extends BaseView<Presenter> {

        boolean isActive();

        void showSignInMessage(int status,String message);

        void getToken(String token);

        void refreshToken();

        void refreshSignIn(String token);
    }

    interface Presenter extends BasePresenter {
        void updateSignInInfo(ContentType type, int id,String address, boolean signIn, String token);

        void refreshToken(String telephone);
    }
}
