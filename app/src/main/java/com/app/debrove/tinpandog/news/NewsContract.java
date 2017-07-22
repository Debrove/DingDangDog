package com.app.debrove.tinpandog.news;

import com.app.debrove.tinpandog.BasePresenter;
import com.app.debrove.tinpandog.BaseView;

/**
 * Created by debrove on 2017/7/17.
 * Package Name : com.app.debrove.tinpandog.news
 * <p>
 * 统一管理News的View和Presenter
 */

public interface NewsContract {
    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

    }
}
