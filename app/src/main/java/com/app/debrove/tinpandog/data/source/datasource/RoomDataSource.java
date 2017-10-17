package com.app.debrove.tinpandog.data.source.datasource;

import android.support.annotation.NonNull;

import com.app.debrove.tinpandog.data.Room;

import java.util.List;

/**
 * Created by debrove on 2017/10/5.
 * Package Name : com.app.debrove.tinpandog.data.source.datasource
 * <p>
 * Main entry point for accessing the {@link Room} data.
 */

public interface RoomDataSource {

    interface LoadRoomInfoCallback {
        void onRoomInfoLoaded(@NonNull Room room);

        void onDataNotAvailable();
    }

    void getRoomInfo(int id,@NonNull LoadRoomInfoCallback callback);
}
