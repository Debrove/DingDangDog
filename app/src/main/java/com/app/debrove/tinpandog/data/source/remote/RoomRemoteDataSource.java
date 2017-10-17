package com.app.debrove.tinpandog.data.source.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.app.debrove.tinpandog.data.Room;
import com.app.debrove.tinpandog.data.source.datasource.RoomDataSource;
import com.app.debrove.tinpandog.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by debrove on 2017/10/5.
 * Package Name : com.app.debrove.tinpandog.data.source.remote
 */

public class RoomRemoteDataSource implements RoomDataSource {

    private static final String LOG_TAG = RoomRemoteDataSource.class.getSimpleName();

    @Nullable
    private static RoomRemoteDataSource INSTANCE = null;

    // Prevent direct instantiation.
    private RoomRemoteDataSource() {
    }

    public static RoomRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RoomRemoteDataSource();
        }
        return INSTANCE;
    }

    //从网络/数据库中获取内容
    @Override
    public void getRoomInfo(int id,@NonNull final LoadRoomInfoCallback callback) {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://10.0.2.2/api/room/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService.RoomService service=retrofit.create(RetrofitService.RoomService.class);

        service.getRoomInfo(id)
                .enqueue(new Callback<Room>() {
                    @Override
                    public void onResponse(Call<Room> call, Response<Room> response) {
                        callback.onRoomInfoLoaded(response.body());
                    }

                    @Override
                    public void onFailure(Call<Room> call, Throwable t) {
                        callback.onDataNotAvailable();
                    }
                });


    }



}
