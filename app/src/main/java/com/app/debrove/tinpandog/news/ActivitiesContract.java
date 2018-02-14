package com.app.debrove.tinpandog.news;

import android.support.annotation.NonNull;

import com.app.debrove.tinpandog.BasePresenter;
import com.app.debrove.tinpandog.BaseView;
import com.app.debrove.tinpandog.data.Activities;
import com.app.debrove.tinpandog.data.BannerResponse;

import java.util.List;

/**
 * Created by debrove on 2017/7/17.
 * Package Name : com.app.debrove.tinpandog.news
 * <p>
 * 统一管理Activities的View和Presenter
 */

public interface ActivitiesContract {

    interface View extends BaseView<Presenter> {
        void showResult(@NonNull List<Activities> list);

        void showBannerImages(@NonNull List<BannerResponse.DataBean> list);

        boolean isActive();

        void setLoadingIndicator(boolean active);

        void setToast(String message);
    }

    interface Presenter extends BasePresenter {
        void loadNews(boolean clearCache);

        void loadNewsByTime(long date);

        void loadBannerUrl();

    }
}
