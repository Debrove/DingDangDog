package com.app.debrove.tinpandog.data.source.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.app.debrove.tinpandog.data.Lectures;
import com.app.debrove.tinpandog.data.NewsResponse;
import com.app.debrove.tinpandog.data.source.datasource.LecturesDataSource;
import com.app.debrove.tinpandog.retrofit.RetrofitService;
import com.app.debrove.tinpandog.util.L;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by debrove on 2017/12/2.
 * Package Name : com.app.debrove.tinpandog.data.source.remote
 */

public class LecturesRemoteDataSource implements LecturesDataSource {

    private static final String LOG_TAG = LecturesRemoteDataSource.class.getSimpleName();

    @Nullable
    private static LecturesRemoteDataSource INSTANCE = null;

    // Prevent direct instantiation.
    private LecturesRemoteDataSource() {
    }

    public static LecturesRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LecturesRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getNews(boolean clearCache, long date, @NonNull final LoadNewsCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService.LecturesNewsServices services=retrofit.create(RetrofitService.LecturesNewsServices.class);
        services.getAllNewsList()
                .enqueue(new Callback<NewsResponse<Lectures>>() {
                    @Override
                    public void onResponse(Call<NewsResponse<Lectures>> call, Response<NewsResponse<Lectures>> response) {
                        callback.onNewsLoaded(response.body().getData());
                        //将原有id保存起来，避免保存到数据库时id混乱
                        for (Lectures item : response.body().getData()) {
                            int id = item.getId();
                            L.d(LOG_TAG, "original id" + id);
                            item.setNewsId(id);
                            L.d(LOG_TAG, "news id saved" + item.getNewsId());
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsResponse<Lectures>> call, Throwable t) {
                        callback.onDataNotAvailable();
                    }
                });
    }

    @Override
    public void getNewsSignedUp(long date,@NonNull LoadNewsCallback callback) {
        //在本地数据库操作(LecturesLocalDataSource)
    }

    @Override
    public void getFavorites(@NonNull LoadNewsCallback callback) {

    }

    @Override
    public void favoriteItem(int itemId, boolean favorite, String title) {

    }

    @Override
    public void signUpItem(int itemId, boolean signUp, String token) {

    }

    @Override
    public void saveAll(@NonNull List<Lectures> list) {

    }
}
