package com.app.debrove.tinpandog.data.source.datasource;

import android.support.annotation.NonNull;

import com.app.debrove.tinpandog.data.Activities;
import com.app.debrove.tinpandog.data.BannerResponse;

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
        void onUrlLoaded(@NonNull List<BannerResponse.Banner> list);

        void onDataNotAvailable();
    }

    interface LoadMessageCallback {
        void onMessageLoaded(int status,@NonNull String message);

        void onDataNotAvailable();
    }

    interface LoadTokenCallback {
        void onInfoLoaded(@NonNull String token);

        void onDataNotAvailable();
    }

    void getNews(boolean clearCache, @NonNull LoadNewsCallback callback);

    void getNewsByTime(long date, @NonNull LoadNewsCallback callback);

    void getNewsSignedUp(long date,@NonNull LoadNewsCallback callback);//获取已经报名的活动

    void getAllNewsSignedUp(@NonNull LoadNewsCallback callback);//获取所有已经报名的活动

    void getFavorites(@NonNull LoadNewsCallback callback);

    void favoriteItem(int itemId, boolean favorite, String title);

    void signUpItem(int itemId, boolean signUp, String token,LoadMessageCallback callback);

    void signInItem(int itemId, String address, boolean signIn, String token,LoadMessageCallback callback);

    void saveAll(@NonNull List<Activities> list);

    void updateAll(@NonNull List<Activities> list);

    void getImagesUrl(@NonNull LoadBannerImagesCallback callback);

    void refreshToken(String telephone, LoadTokenCallback callback);
}
