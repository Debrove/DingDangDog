package com.app.debrove.tinpandog.user;

import com.app.debrove.tinpandog.BasePresenter;
import com.app.debrove.tinpandog.BaseView;
import com.app.debrove.tinpandog.data.User;

import java.util.List;

/**
 * Created by debrove on 2017/9/28.
 * Package Name : com.app.debrove.tinpandog.user
 * <p>
 * 统一管理User的View和Presenter
 */

public interface UserContract {

    interface View extends BaseView<Presenter> {
        boolean isActive();

        void showInfo(List<User> userInfo);

        void getToken(String token);

        void refreshToken();

    }

    interface Presenter extends BasePresenter {
        void loadUserInfo(String telephone,String token);

        void refreshToken(String telephone);

    }
}
