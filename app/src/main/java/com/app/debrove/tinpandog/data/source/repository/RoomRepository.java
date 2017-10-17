package com.app.debrove.tinpandog.data.source.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.app.debrove.tinpandog.data.News;
import com.app.debrove.tinpandog.data.Room;
import com.app.debrove.tinpandog.data.source.datasource.RoomDataSource;

import java.util.List;

/**
 * Created by debrove on 2017/10/5.
 * Package Name : com.app.debrove.tinpandog.data.source.repository
 */

public class RoomRepository implements RoomDataSource {

    @Nullable
    public static RoomRepository INSTANCE = null;

    @NonNull
    private final RoomDataSource mRemoteDataSource;

    @Nullable
    private Room mRoom;

    public RoomRepository(@NonNull RoomDataSource remoteDataSource) {
        this.mRemoteDataSource = remoteDataSource;
    }

    public static RoomRepository getInstance(@NonNull RoomDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new RoomRepository(remoteDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


    @Override
    public void getRoomInfo(int id, @NonNull final LoadRoomInfoCallback callback) {
        if (mRoom != null) {
            callback.onRoomInfoLoaded(mRoom);
        }
        mRemoteDataSource.getRoomInfo(id, new LoadRoomInfoCallback() {
            @Override
            public void onRoomInfoLoaded(@NonNull Room room) {
                if (mRoom == null) {
                    mRoom = room;
                }
                callback.onRoomInfoLoaded(room);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }
}
