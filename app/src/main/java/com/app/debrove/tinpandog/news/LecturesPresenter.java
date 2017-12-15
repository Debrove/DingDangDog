package com.app.debrove.tinpandog.news;

import android.support.annotation.NonNull;

import com.app.debrove.tinpandog.data.Lectures;
import com.app.debrove.tinpandog.data.source.datasource.LecturesDataSource;
import com.app.debrove.tinpandog.data.source.repository.LecturesRepository;

import java.util.List;

/**
 * Created by debrove on 2017/12/2.
 * Package Name : com.app.debrove.tinpandog.news
 */

public class LecturesPresenter implements LecturesContract.Presenter {

    @NonNull
    private final LecturesContract.View mView;

    @NonNull
    private final LecturesRepository mRepository;

    public LecturesPresenter(@NonNull LecturesContract.View mView,
                             @NonNull LecturesRepository mRepository) {
        this.mView = mView;
        this.mRepository = mRepository;
        this.mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadNews(Long date, boolean clearCache) {
        mRepository.getNews(clearCache, date, new LecturesDataSource.LoadNewsCallback() {
            @Override
            public void onNewsLoaded(@NonNull List<Lectures> list) {
                if (mView.isActive()) {
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
