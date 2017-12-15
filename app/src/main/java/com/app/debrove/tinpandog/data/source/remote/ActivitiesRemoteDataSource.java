package com.app.debrove.tinpandog.data.source.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.app.debrove.tinpandog.data.Activities;
import com.app.debrove.tinpandog.data.BannerResponse;
import com.app.debrove.tinpandog.data.NewsResponse;
import com.app.debrove.tinpandog.data.source.datasource.ActivitiesDataSource;
import com.app.debrove.tinpandog.retrofit.RetrofitService;
import com.app.debrove.tinpandog.util.L;

import java.util.Collections;
import java.util.List;

//import cn.bmob.v3.BmobQuery;
//import cn.bmob.v3.exception.BmobException;
//import cn.bmob.v3.listener.FindListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by debrove on 2017/9/21.
 * Package Name : com.app.debrove.tinpandog.data.source.remote
 */

public class ActivitiesRemoteDataSource implements ActivitiesDataSource {

    private static final String LOG_TAG = ActivitiesRemoteDataSource.class.getSimpleName();

    @Nullable
    private static ActivitiesRemoteDataSource INSTANCE = null;

    // Prevent direct instantiation.
    private ActivitiesRemoteDataSource() {
    }

    public static ActivitiesRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ActivitiesRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getNews(boolean clearCache, @NonNull final LoadNewsCallback callback) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService.ActivitiesNewsService service = retrofit.create(RetrofitService.ActivitiesNewsService.class);

        service.getAllNewsList()
                .enqueue(new Callback<NewsResponse<Activities>>() {
                    @Override
                    public void onResponse(Call<NewsResponse<Activities>> call, Response<NewsResponse<Activities>> response) {
                        L.d(LOG_TAG, response.body().getMessage());
                        //将原有id保存起来，避免保存到数据库时id混乱
                        for (Activities item : response.body().getData()) {
                            int id = item.getId();
                            L.d(LOG_TAG, "original id" + id);
                            item.setNewsId(id);
                            L.d(LOG_TAG, "news id saved" + item.getNewsId());
                        }
                        callback.onNewsLoaded(response.body().getData());
                    }

                    @Override
                    public void onFailure(Call<NewsResponse<Activities>> call, Throwable t) {
                        callback.onDataNotAvailable();
                    }
                });
    }

    @Override
    public void getNewsByTime(long date, @NonNull LoadNewsCallback callback) {
        //在本地数据库操作(ActivitiesLocalDataSource)
    }

    @Override
    public void getNewsSignedUp(long date, @NonNull LoadNewsCallback callback) {
        //在本地数据库操作(ActivitiesLocalDataSource)
    }

    //轮播图
    @Override
    public void getImagesUrl(@NonNull final LoadBannerImagesCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService.BannerService service = retrofit.create(RetrofitService.BannerService.class);
        service.getBanner()
                .enqueue(new Callback<BannerResponse>() {
                    @Override
                    public void onResponse(Call<BannerResponse> call, Response<BannerResponse> response) {
                        L.d(LOG_TAG, response.body().getMessage());
                        L.d("data", response.body().getData().toString());
                        for (BannerResponse.DataBean item : response.body().getData()) {
                            L.d("url", "http://rehellinen.cn" + item.getPhoto_url());
                        }
                        callback.onUrlLoaded(response.body().getData());
                    }

                    @Override
                    public void onFailure(Call<BannerResponse> call, Throwable t) {
                        L.d("error", t.getMessage());
                        callback.onDataNotAvailable();
                    }
                });
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
    public void saveAll(@NonNull List<Activities> list) {

    }


}
