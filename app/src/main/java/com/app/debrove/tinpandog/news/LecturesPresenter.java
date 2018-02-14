package com.app.debrove.tinpandog.news;

import android.support.annotation.NonNull;
import android.util.Log;

import com.app.debrove.tinpandog.data.BannerResponse;
import com.app.debrove.tinpandog.data.Lectures;
import com.app.debrove.tinpandog.data.source.datasource.LecturesDataSource;
import com.app.debrove.tinpandog.data.source.repository.LecturesRepository;
import com.app.debrove.tinpandog.util.L;

import java.util.List;

/**
 * Created by debrove on 2017/12/2.
 * Package Name : com.app.debrove.tinpandog.news
 */

public class LecturesPresenter implements LecturesContract.Presenter {

    @NonNull
    private final LecturesContract.View mView;

    @NonNull
    private final LecturesRepository mLecturesRepository;

    public LecturesPresenter(@NonNull LecturesContract.View mView,
                             @NonNull LecturesRepository lecturesRepository) {
        this.mView = mView;
        this.mLecturesRepository = lecturesRepository;
        this.mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadNews(boolean clearCache) {
        mLecturesRepository.getNews(clearCache, new LecturesDataSource.LoadNewsCallback() {
            @Override
            public void onNewsLoaded(@NonNull List<Lectures> list) {
                if (mView.isActive()) {
                    mView.showResult(list);
                    mView.setLoadingIndicator(false);
                }
            }

            @Override
            public void onDataNotAvailable() {
                if (mView.isActive()) {
                    mView.setLoadingIndicator(false);
                }
            }
        });
    }

    @Override
    public void loadNewsByTime(long date) {
        mLecturesRepository.getNewsByTime(date, new LecturesDataSource.LoadNewsCallback() {
            @Override
            public void onNewsLoaded(@NonNull List<Lectures> lecturesList) {
                if (mView.isActive()) {
                    mView.showResult(lecturesList);
                    mView.setLoadingIndicator(false);
                }
            }

            @Override
            public void onDataNotAvailable() {
                if (mView.isActive()) {
                    mView.setLoadingIndicator(false);
                }
            }
        });
    }

    @Override
    public void loadBannerUrl() {
        mLecturesRepository.getImagesUrl(new LecturesDataSource.LoadBannerImagesCallback() {
            @Override
            public void onUrlLoaded(@NonNull List<BannerResponse.DataBean> list) {
                if (mView.isActive()) {
                    mView.showBannerImages(list);
                    mView.setLoadingIndicator(false);
                }
            }

            @Override
            public void onDataNotAvailable() {
                if (mView.isActive()) {
                    mView.setLoadingIndicator(false);
                }
            }
        });
    }
}
