package com.app.debrove.tinpandog.details;

import com.app.debrove.tinpandog.BasePresenter;
import com.app.debrove.tinpandog.BaseView;
import com.app.debrove.tinpandog.data.ContentType;

/**
 * Created by debrove on 2017/10/5.
 * Package Name : com.app.debrove.tinpandog.details
 * <p>
 * 统一管理详情页的View和Presenter
 */

public class DetailsContract {
    interface View extends BaseView<Presenter> {
        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        void favorite(ContentType type, int id, boolean favorite, String title);

    }
}
