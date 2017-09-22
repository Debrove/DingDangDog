package com.app.debrove.tinpandog.data.source.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.app.debrove.tinpandog.data.News;
import com.app.debrove.tinpandog.data.source.datasource.NewsDataSource;
import com.app.debrove.tinpandog.util.L;

import java.util.List;

/**
 * Created by debrove on 2017/9/17.
 * Package Name : com.app.debrove.tinpandog.data.source.repository
 * <p>
 * Use the remote data source firstly, which is obtained from the server.(Bmob)
 * If the remote data was not available, then use the local data source,
 * which was from the locally persisted in database.
 */

public class NewsRepository implements NewsDataSource {

    private static final String LOG_TAG = NewsRepository.class.getSimpleName();

    @Nullable
    private static NewsRepository INSTANCE = null;

    @NonNull
    private final NewsDataSource mRemoteDataSource;

    // Prevent direct instantiation.
    public NewsRepository(@NonNull NewsDataSource mRemoteDataSource) {
        this.mRemoteDataSource = mRemoteDataSource;
    }

    public static NewsRepository getInstance(@NonNull NewsDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new NewsRepository(remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getNews(long date, @NonNull final loadNewsCallback callback) {
        // Get data by accessing network first.
        mRemoteDataSource.getNews(date, new loadNewsCallback() {
            @Override
            public void onNewsLoaded(@NonNull List<News> list) {
                callback.onNewsLoaded(list);
            }

            @Override
            public void onDataNotAvailable() {
                L.d(LOG_TAG, "data access error");
            }
        });
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
