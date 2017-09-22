package com.app.debrove.tinpandog.news;

import android.support.annotation.NonNull;

import com.app.debrove.tinpandog.BasePresenter;
import com.app.debrove.tinpandog.BaseView;
import com.app.debrove.tinpandog.data.News;

import java.util.List;

/**
 * Created by debrove on 2017/7/17.
 * Package Name : com.app.debrove.tinpandog.news
 * <p>
 * 统一管理News的View和Presenter
 */

public interface NewsContract {
    interface View extends BaseView<Presenter> {
        void showResult(@NonNull List<News> list);

        boolean isActive();

        void setLoadingIndicator(boolean active);
    }

    interface Presenter extends BasePresenter {
        void loadNews(Long date);
    }
}
