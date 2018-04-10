package com.app.debrove.tinpandog.data.source.remote;

import android.support.annotation.Nullable;
import android.widget.Toast;

import com.app.debrove.tinpandog.data.BaseResponse;
import com.app.debrove.tinpandog.data.LoginResponse;
import com.app.debrove.tinpandog.data.User;
import com.app.debrove.tinpandog.data.UserResponse;
import com.app.debrove.tinpandog.data.source.datasource.UserDataSource;
import com.app.debrove.tinpandog.retrofit.RetrofitService;
import com.app.debrove.tinpandog.util.L;
import com.app.debrove.tinpandog.util.ShareUtils;
import com.app.debrove.tinpandog.util.StaticClass;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.litepal.LitePalApplication.getContext;

/**
 * Created by cp4yin on 2017/12/17.
 */

public class UserRemoteDataSource implements UserDataSource {

    private static final String LOG_TAG = UserRemoteDataSource.class.getSimpleName();

    @Nullable
    private static UserRemoteDataSource INSTANCE = null;

    // Prevent direct instantiation.
    private UserRemoteDataSource() {
    }

    public static UserRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getUserInfo(final String telephone, final String token, final LoadUserInfoCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitService.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService.UserService service = retrofit.create(RetrofitService.UserService.class);
        service.getInfo(token)
                .enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if (response.isSuccessful()) {
                            L.d("userInfo", response.body().getData().getData().getName() + telephone + response.body().getData().getData().getNumber());

                            List<User> list=new ArrayList<>();
                            list.add(response.body().getData().getData());
                            L.d(LOG_TAG," list "+list);

                            callback.onInfoLoaded(list);

                            //通过手机号码更新用户信息
                            User user = new User();
                            user.setName(response.body().getData().getData().getName());
                            user.setNumber(String.valueOf(response.body().getData().getData().getNumber()));
                            user.updateAll("telephone = ?", String.valueOf(telephone));
                            //refreshToken();(测试刷新token)
                        } else {
                            L.d(LOG_TAG, "token过期或无效，获取用户信息失败");
                            callback.onDataNotAvailable();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        L.d(LOG_TAG, "获取用户信息失败" + t.toString());
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
