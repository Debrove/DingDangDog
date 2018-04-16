package com.app.debrove.tinpandog.data.source.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.app.debrove.tinpandog.data.BannerResponse;
import com.app.debrove.tinpandog.data.BaseResponse;
import com.app.debrove.tinpandog.data.Lectures;
import com.app.debrove.tinpandog.data.LoginResponse;
import com.app.debrove.tinpandog.data.Place;
import com.app.debrove.tinpandog.data.User;
import com.app.debrove.tinpandog.data.source.datasource.LecturesDataSource;
import com.app.debrove.tinpandog.retrofit.RetrofitService;
import com.app.debrove.tinpandog.util.L;

import org.litepal.crud.DataSupport;

import java.util.List;
import java.util.Objects;

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
    public void getNews(boolean clearCache, @NonNull final LoadNewsCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService.LecturesNewsServices services = retrofit.create(RetrofitService.LecturesNewsServices.class);
        services.getAllNewsList()
                .enqueue(new Callback<BaseResponse<Lectures>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Lectures>> call, Response<BaseResponse<Lectures>> response) {
                        callback.onNewsLoaded(response.body().getData().getData());
                        //将原有id保存起来，避免保存到数据库时id混乱
                        for (Lectures item : response.body().getData().getData()) {

                            //处理不同消息时间
                            if (Objects.equals(item.getPlace_id().getName(), "网申")) {
                                item.setTime(item.getTime());
                            } else {
                                String date = item.getTime().substring(0, 10);
                                String time = item.getTime().substring(11);

                                L.d(LOG_TAG, "date and time " + item.getTime() + " date " + date + " time1 " + time);
                                item.setTime(date);
                                item.setTime1(time);
                            }
                            int id = item.getId();
                            L.d(LOG_TAG, "original id" + id);
                            item.setNewsId(id);
                            L.d(LOG_TAG, "news id saved" + item.getNewsId());

                            item.setDetail(item.getDetail());
                            item.setHolder(item.getHolder());
                            item.setPhoto_url(item.getPhoto_url());
                            item.setText(item.getText());
                            item.setTitle(item.getTitle());
                            item.save();
                            item.updateAll("newsId=?", String.valueOf(id));

                            //将地点与newsId关联起来,保存地点数据
                            Place place = new Place();
//                            L.d(LOG_TAG,item.getPlace_id()+"  "+item.getPlace_id().getName());
                            place.setNewsId(id);
                            place.setName(item.getPlace_id().getName());
                            place.setId(item.getPlace_id().getId());
                            place.setMax(item.getPlace_id().getMax());
                            place.setStatus(item.getPlace_id().getStatus());
                            place.save();
                            place.updateAll("newsId=?", String.valueOf(id));
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<Lectures>> call, Throwable t) {
                        callback.onDataNotAvailable();
                    }
                });
    }

    @Override
    public void getNewsByTime(long date, @NonNull LoadNewsCallback callback) {

    }

    @Override
    public void getNewsSignedUp(long date, @NonNull LoadNewsCallback callback) {
        //在本地数据库操作(LecturesLocalDataSource)
    }

    @Override
    public void getAllNewsSignedUp(@NonNull LoadNewsCallback callback) {

    }

    @Override
    public void getFavorites(@NonNull LoadNewsCallback callback) {

    }

    @Override
    public void favoriteItem(int itemId, boolean favorite, String title) {

    }

    @Override
    public void signUpItem(int itemId, boolean signUp, String token, final LoadMessageCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService.SignUpService service = retrofit.create(RetrofitService.SignUpService.class);
        service.signUp(token, itemId, 1)
                .enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.isSuccessful()) {
                            L.d(LOG_TAG, response.body().getMessage());
                            callback.onMessageLoaded(response.body().getStatus(), response.body().getMessage());
                        } else {
                            callback.onDataNotAvailable();
                            L.d(LOG_TAG, response.errorBody() + "");
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable throwable) {
                        L.d(LOG_TAG, throwable.toString());
                        callback.onDataNotAvailable();
                    }
                });
    }

    @Override
    public void saveAll(@NonNull List<Lectures> list) {

    }

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
                        L.d(LOG_TAG + "data", response.body().getData().toString());
                        for (BannerResponse.Banner item : response.body().getData().getData()) {
                            L.d(LOG_TAG, item.getPhoto_url());
                        }
                        callback.onUrlLoaded(response.body().getData().getData());
                    }

                    @Override
                    public void onFailure(Call<BannerResponse> call, Throwable t) {
                        L.d("error", t.getMessage());
                        callback.onDataNotAvailable();
                    }
                });
    }

    @Override
    public void refreshToken(String telephone, final LoadTokenCallback callback) {
        String password = "";
        List<User> userList = DataSupport.where("telephone = ?", telephone).find(User.class);
        for (User user : userList) {
            password = user.getPassword();
        }

        L.d(LOG_TAG, "telephone" + telephone + "password" + password);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService.UserService service = retrofit.create(RetrofitService.UserService.class);
        service.login(telephone, password)
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            L.d(LOG_TAG, " 新token " + response.body().getData().getToken());
                            callback.onInfoLoaded(response.body().getData().getToken());
                        } else {
                            L.d(LOG_TAG, "获取Token失败");
                            L.d(LOG_TAG, response.code() + "   " + response.errorBody());
                            callback.onDataNotAvailable();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        L.d(LOG_TAG, "获取Token失败");
                        callback.onDataNotAvailable();
                    }
                });
    }
}
