package com.app.debrove.tinpandog.data.source.datasource;

import android.support.annotation.NonNull;

import com.app.debrove.tinpandog.data.Lectures;

import java.util.List;

/**
 * Created by debrove on 2017/12/2.
 * Package Name : com.app.debrove.tinpandog.data.source.datasource
 */

public interface LecturesDataSource {

    interface LoadNewsCallback {
        void onNewsLoaded(@NonNull List<Lectures> lecturesList);

        void onDataNotAvailable();
    }

    void getNews(boolean clearCache, long date, @NonNull LoadNewsCallback callback);

    void getNewsSignedUp(long date,@NonNull LoadNewsCallback callback);//获取已经报名的讲座

    void getFavorites(@NonNull LoadNewsCallback callback);

    void favoriteItem(int itemId, boolean favorite, String title);

    void signUpItem(int itemId, boolean signUp, String token);

    void saveAll(@NonNull List<Lectures> list);
}
