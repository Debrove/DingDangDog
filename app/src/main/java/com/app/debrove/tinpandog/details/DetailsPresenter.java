package com.app.debrove.tinpandog.details;

import android.support.annotation.NonNull;

import com.app.debrove.tinpandog.data.Room;
import com.app.debrove.tinpandog.data.source.datasource.RoomDataSource;
import com.app.debrove.tinpandog.data.source.repository.RoomRepository;
import com.app.debrove.tinpandog.data.source.repository.NewsRepository;

/**
 * Created by debrove on 2017/10/5.
 * Package Name : com.app.debrove.tinpandog.details
 */

public class DetailsPresenter implements DetailsContract.Presenter {

    @NonNull
    private final DetailsContract.View mView;

    private NewsRepository mNewsRepository;
    private RoomRepository mRoomRepository;

    private String mRoomInfo;

    public DetailsPresenter(@NonNull DetailsContract.View view,
                            @NonNull NewsRepository newsRepository,
                            @NonNull RoomRepository roomRepository) {
        this.mView = view;
        this.mNewsRepository = newsRepository;
        this.mRoomRepository = roomRepository;
    }


    @Override
    public void start() {

    }

    @Override
    public String loadRoomInfo(int id) {
        mRoomRepository.getRoomInfo(id, new RoomDataSource.LoadRoomInfoCallback() {
            @Override
            public void onRoomInfoLoaded(@NonNull Room room) {
                mRoomInfo=room.getName();
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
        return mRoomInfo;
    }
}
