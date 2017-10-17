package com.app.debrove.tinpandog.news;

import android.support.annotation.NonNull;
import android.util.Log;

import com.app.debrove.tinpandog.data.News;
import com.app.debrove.tinpandog.data.source.datasource.NewsDataSource;
import com.app.debrove.tinpandog.data.source.repository.NewsRepository;
import com.app.debrove.tinpandog.util.L;

import java.util.List;

/**
 * Created by debrove on 2017/7/17.
 * Package Name : com.app.debrove.tinpandog.news
 *
 * Listens to user actions from UI ({@link NewsFragment}),
 * retrieves the data and update the ui as required.
 */

public class NewsPresenter implements NewsContract.Presenter {

    @NonNull
    private final NewsContract.View mView;

    @NonNull
    private final NewsRepository mRepository;

    public NewsPresenter(@NonNull NewsContract.View mView,
                         @NonNull NewsRepository mRepository) {
        this.mView = mView;
        this.mRepository = mRepository;
        this.mView.setPresenter(this);
    }

    @Override
    public void start() {

    }


    @Override
    public void loadNews(Long date) {
        mRepository.getNews(date, new NewsDataSource.loadNewsCallback() {
            @Override
            public void onNewsLoaded(@NonNull List<News> list) {
                if (mView.isActive()){
                    mView.showResult(list);
                    mView.setLoadingIndicator(false);
                }
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }
}
