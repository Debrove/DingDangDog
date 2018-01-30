package com.app.debrove.tinpandog.news;

import android.support.annotation.NonNull;
import android.util.Log;

import com.app.debrove.tinpandog.data.Activities;
import com.app.debrove.tinpandog.data.BannerResponse;
import com.app.debrove.tinpandog.data.source.datasource.ActivitiesDataSource;
import com.app.debrove.tinpandog.data.source.repository.ActivitiesRepository;
import com.app.debrove.tinpandog.retrofit.RetrofitService;
import com.app.debrove.tinpandog.util.L;
import com.app.debrove.tinpandog.util.StaticClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by debrove on 2017/7/17.
 * Package Name : com.app.debrove.tinpandog.news
 * <p>
 * Listens to user actions from UI ({@link NewsFragment}),
 * retrieves the data and update the ui as required.
 */

public class ActivitiesPresenter implements ActivitiesContract.Presenter {

    @NonNull
    private final ActivitiesContract.View mView;

    @NonNull
    private final ActivitiesRepository mActivitiesRepository;

    public ActivitiesPresenter(@NonNull ActivitiesContract.View mView,
                               @NonNull ActivitiesRepository activitiesRepository) {
        this.mView = mView;
        this.mActivitiesRepository = activitiesRepository;
        this.mView.setPresenter(this);
    }

    @Override
    public void start() {

    }


    @Override
    public void loadNews(boolean clearCache) {
        mActivitiesRepository.getNews(clearCache, new ActivitiesDataSource.LoadNewsCallback() {
            @Override
            public void onNewsLoaded(@NonNull List<Activities> list) {
                if (mView.isActive()) {
                    mView.showResult(list);
                    mView.setLoadingIndicator(false);
                }
            }

            @Override
            public void onDataNotAvailable() {
                if (mView.isActive()) {
                    mView.setLoadingIndicator(false);
                    mView.setToast("网络问题，刷新失败");
                }
            }
        });
    }

    @Override
    public void loadNewsByTime(long date) {
        mActivitiesRepository.getNewsByTime(date, new ActivitiesDataSource.LoadNewsCallback() {
            @Override
            public void onNewsLoaded(@NonNull List<Activities> list) {
                if (mView.isActive()) {
                    mView.showResult(list);
                    mView.setLoadingIndicator(false);
                }
            }

            @Override
            public void onDataNotAvailable() {
                if (mView.isActive()) {
                    mView.setLoadingIndicator(false);
                    mView.setToast("网络问题，刷新失败");
                }
            }
        });
    }

    @Override
    public void loadBannerUrl() {
        mActivitiesRepository.getImagesUrl(new ActivitiesDataSource.LoadBannerImagesCallback() {
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
