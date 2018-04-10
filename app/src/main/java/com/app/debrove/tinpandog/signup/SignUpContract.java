package com.app.debrove.tinpandog.signup;

import com.app.debrove.tinpandog.BasePresenter;
import com.app.debrove.tinpandog.BaseView;
import com.app.debrove.tinpandog.data.ContentType;

/**
 * Created by debrove on 2017/12/13.
 * Package Name : com.app.debrove.tinpandog.signup
 */

public interface SignUpContract {

    interface View extends BaseView<Presenter> {

        boolean isActive();

        void showSignUpMessage(int status,String message);

        void getToken(String token);

        void refreshToken();

        void refreshSignUp(String token);
    }

    interface Presenter extends BasePresenter {
        void updateSignUpInfo(ContentType type, int id, boolean signUp, String token);

        void refreshToken(ContentType type,String telephone);
    }
}
