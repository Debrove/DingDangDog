package com.app.debrove.tinpandog.user;

import com.app.debrove.tinpandog.BasePresenter;
import com.app.debrove.tinpandog.BaseView;

/**
 * Created by debrove on 2017/9/28.
 * Package Name : com.app.debrove.tinpandog.user
 * <p>
 * 统一管理User的View和Presenter
 */

public interface UserContract {
    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

    }
}
