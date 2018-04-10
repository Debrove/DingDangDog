package com.app.debrove.tinpandog.retrofit;

import com.app.debrove.tinpandog.data.Activities;
import com.app.debrove.tinpandog.data.BannerResponse;
import com.app.debrove.tinpandog.data.BaseResponse;
import com.app.debrove.tinpandog.data.Lectures;
import com.app.debrove.tinpandog.data.LoginResponse;
import com.app.debrove.tinpandog.data.User;
import com.app.debrove.tinpandog.data.UserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by debrove on 2017/10/6.
 * Package Name : com.app.debrove.tinpandog.retrofit
 *
 * :version 为api版本号，目前为v1;
 */

public interface RetrofitService {

    String URL_BASE = "https://20298479.rehellinen.cn/dingdang/public/";

    interface ActivitiesNewsService {
        @GET("api/v1/allActivities")
        Call<BaseResponse<Activities>> getAllNewsList();
    }

    interface LecturesNewsServices {
        @GET("api/v1/allLectures")
        Call<BaseResponse<Lectures>> getAllNewsList();
    }

    interface UserService {
        @FormUrlEncoded
        @POST("api/v1/user")
        Call<User> register(@Field("telephone") String telephone,
                            @Field("name") String name,
                            @Field("password") String password,
                            @Field("number") String number);

        @FormUrlEncoded
        @POST("api/v1/login")
        Call<LoginResponse> login(@Field("telephone") String telephone,
                                  @Field("password") String password);


        @GET("api/v1/user")
        Call<UserResponse> getInfo(@Header("token") String token);
    }

    //预报名
    interface SignUpService {
        @FormUrlEncoded
        @POST("api/v1/enroll")
        Call<BaseResponse> signUp(@Header("token") String token,
                                  @Field("lecture_id") int id,
                                  @Field("status") int status);
    }

    //签到
    interface SignInService{
        @FormUrlEncoded
        @POST("api/v1/Attendance")
        Call<BaseResponse> signIn(@Header("token") String token,
                                  @Field("lecture_id") int id,
                                  @Field("address") String address);
    }

    interface BannerService {
        @GET("api/v1/banner")
        Call<BannerResponse> getBanner();
    }

}