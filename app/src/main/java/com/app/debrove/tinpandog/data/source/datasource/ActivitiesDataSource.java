package com.app.debrove.tinpandog.data.source.datasource;

import android.support.annotation.NonNull;

import com.app.debrove.tinpandog.data.Activities;
import com.app.debrove.tinpandog.data.BannerResponse;
import com.app.debrove.tinpandog.data.NewsResponse;

import java.util.List;

/**
 * Created by debrove on 2017/9/17.
 * Package Name : com.app.debrove.tinpandog.data.source.datasource
 */

public interface ActivitiesDataSource {

    interface LoadNewsCallback {
        void onNewsLoaded(@NonNull List<Activities> activitiesList);

        void onDataNotAvailable();
    }

    interface LoadBannerImagesCallback {
        void onUrlLoaded(@NonNull List<BannerResponse.DataBean> list);

        void onDataNotAvailable();
    }

    void getNews(boolean clearCache, @NonNull LoadNewsCallback callback);

    void getNewsByTime(long date, @NonNull LoadNewsCallback callback);

    void getNewsSignedUp(long date,@NonNull LoadNewsCallback callback);//获取已经报名的活动

    void getFavorites(@NonNull LoadNewsCallback callback);

    void favoriteItem(int itemId, boolean favorite, String title);

    void signUpItem(int itemId, boolean signUp, String token);

    void saveAll(@NonNull List<Activities> list);

    void getImagesUrl(@NonNull LoadBannerImagesCallback callback);
}
