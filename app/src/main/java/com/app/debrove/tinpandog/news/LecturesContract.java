package com.app.debrove.tinpandog.news;

import android.support.annotation.NonNull;

import com.app.debrove.tinpandog.BasePresenter;
import com.app.debrove.tinpandog.BaseView;
import com.app.debrove.tinpandog.data.BannerResponse;
import com.app.debrove.tinpandog.data.Lectures;

import java.util.List;

/**
 * Created by debrove on 2017/12/2.
 * Package Name : com.app.debrove.tinpandog.news
 */

public interface LecturesContract {

    interface View extends BaseView<Presenter> {
        void showResult(@NonNull List<Lectures> list);

        void showBannerImages(@NonNull List<BannerResponse.Banner> list);

        boolean isActive();

        void setLoadingIndicator(boolean active);
    }

    interface Presenter extends BasePresenter {
        void loadNews(boolean clearCache);

        void loadNewsByTime(long date);

        void loadBannerUrl();
    }

}
