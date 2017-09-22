package com.app.debrove.tinpandog.data.source.datasource;

import android.support.annotation.NonNull;

import com.app.debrove.tinpandog.data.News;

import java.util.List;

/**
 * Created by debrove on 2017/9/17.
 * Package Name : com.app.debrove.tinpandog.data.source.datasource
 */

public interface NewsDataSource {
    interface loadNewsCallback{
        void onNewsLoaded(@NonNull List<News> list);

        void onDataNotAvailable();
    }

    interface GetNewsItemCallback {

        void onItemLoaded(@NonNull News item);

        void onDataNotAvailable();

    }

    void getNews(long date,@NonNull loadNewsCallback callback);
}
