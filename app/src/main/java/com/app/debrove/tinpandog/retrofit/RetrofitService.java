package com.app.debrove.tinpandog.retrofit;

import com.app.debrove.tinpandog.data.Room;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by debrove on 2017/10/6.
 * Package Name : com.app.debrove.tinpandog.retrofit
 */

public interface RetrofitService {

    interface RoomService{
        @GET("{id}.json")
        Call<Room> getRoomInfo(@Path("id") int id);
    }

}
