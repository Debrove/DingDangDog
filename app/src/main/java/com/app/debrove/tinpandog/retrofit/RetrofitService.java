package com.app.debrove.tinpandog.retrofit;

import com.app.debrove.tinpandog.data.Activities;
import com.app.debrove.tinpandog.data.BannerResponse;
import com.app.debrove.tinpandog.data.Lectures;
import com.app.debrove.tinpandog.data.LoginResponse;
import com.app.debrove.tinpandog.data.NewsResponse;
import com.app.debrove.tinpandog.data.User;
import com.app.debrove.tinpandog.data.UserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by debrove on 2017/10/6.
 * Package Name : com.app.debrove.tinpandog.retrofit
 */

public interface RetrofitService {

    String URL_BASE = "http://rehellinen.cn/project/dingdang/public/";

    interface ActivitiesNewsService {
        @GET("{date}.json")
        Call<Activities> getNewsList(@Path("date") String date);

        @GET("index.php?s=api/lecture/getAllActivity")
        Call<NewsResponse<Activities>> getAllNewsList();
    }

    interface LecturesNewsServices {
        @GET("index.php?s=api/lecture/getAllLecture")
        Call<NewsResponse<Lectures>> getAllNewsList();
    }

    interface UserService {
        @FormUrlEncoded
        @POST("index.php?s=api/user/register")
        Call<User> register(@Field("telephone") String telephone,
                            @Field("name") String name,
                            @Field("password") String password,
                            @Field("number") String number);

        @FormUrlEncoded
        @POST("index.php?s=api/login/appLogin")
        Call<LoginResponse> login(@Field("telephone") String telephone,
                                  @Field("password") String password);


        @GET("index.php?s=api/user/getinfo")
        Call<UserResponse> getInfo(@Header("token") String token);
    }

    interface SignUpService {
        @FormUrlEncoded
        @POST("index.php?s=api/enroll/enroll")
        Call<LoginResponse> signUp(@Header("token") String token,
                                   @Field("lecture_id") int id);
    }

    interface BannerService {
        @GET("index.php?s=api/banner/getBanner")
        Call<BannerResponse> getBanner();
    }

}
