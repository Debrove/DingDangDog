package com.app.debrove.tinpandog.data.source.local;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.app.debrove.tinpandog.data.Activities;
import com.app.debrove.tinpandog.data.source.datasource.ActivitiesDataSource;
import com.app.debrove.tinpandog.util.DateFormatUtils;
import com.app.debrove.tinpandog.util.L;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.SaveCallback;
import org.litepal.crud.callback.UpdateOrDeleteCallback;

import java.util.List;

/**
 * Created by debrove on 2017/11/13.
 * Package Name : com.app.debrove.tinpandog.data.source.local
 */

public class ActivitiesLocalDataSource implements ActivitiesDataSource {

    private static final String LOG_TAG = ActivitiesLocalDataSource.class.getSimpleName();

    @Nullable
    private static ActivitiesLocalDataSource INSTANCE = null;

    private ActivitiesLocalDataSource() {
    }

    public static ActivitiesLocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ActivitiesLocalDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getNews(boolean clearCache, @NonNull LoadNewsCallback callback) {
        List<Activities> allActivities = DataSupport.findAll(Activities.class);
        callback.onNewsLoaded(allActivities);
        L.d(LOG_TAG, "get news in local" + allActivities);
    }

    //在本地数据库操作
    @Override
    public void getNewsByTime(long date, @NonNull LoadNewsCallback callback) {
        String time = DateFormatUtils.formatNewsDateLongToString(date);
        L.d(LOG_TAG, " time " + time + " " + date);
        List<Activities> activitiesByTime = DataSupport.where("time = ?", time).find(Activities.class);
        L.d(LOG_TAG, "by time " + activitiesByTime);
        callback.onNewsLoaded(activitiesByTime);
    }

    @Override
    public void getNewsSignedUp(long date, @NonNull LoadNewsCallback callback) {
        String time = DateFormatUtils.formatNewsDateLongToString(date);
        L.d(LOG_TAG, " time " + time + " " + date);
        List<Activities> activities = DataSupport.where("pre_sign_up = ? and time = ?", "1", time).find(Activities.class);
        callback.onNewsLoaded(activities);
        L.d(LOG_TAG, "getNewsSignedUp " + activities);
    }

    @Override
    public void getFavorites(@NonNull final LoadNewsCallback callback) {
        List<Activities> allActivities = DataSupport.where("favorite = ?", "1").find(Activities.class);
        callback.onNewsLoaded(allActivities);
        L.d(LOG_TAG, "getFavorite" + allActivities);
    }

    @Override
    public void favoriteItem(final int itemId, final boolean favorite, String title) {
        L.d(LOG_TAG, "itemId" + itemId + " " + favorite);
        Activities activitiesToUpdate = new Activities();
        //当favorite为false时，不能用setFavorite(false),而要用setToDefault("favorite")
        if (favorite) {
            activitiesToUpdate.setFavourite(true);
        } else {
            activitiesToUpdate.setToDefault("favorite");
        }
        activitiesToUpdate.updateAllAsync("newsId = ? ", String.valueOf(itemId)).listen(new UpdateOrDeleteCallback() {
            @Override
            public void onFinish(int rowsAffected) {
                L.d(LOG_TAG, "The number of updating favorite successfully:" + rowsAffected);
            }
        });

    }

    @Override
    public void signUpItem(int itemId, boolean signUp, String token) {
        L.d(LOG_TAG, "itemId" + itemId + " " + signUp + " token " + token);
        Activities activitiesToUpdate = new Activities();
        activitiesToUpdate.setPre_sign_up(true);
        activitiesToUpdate.updateAllAsync("newsId = ?", String.valueOf(itemId)).listen(new UpdateOrDeleteCallback() {
            @Override
            public void onFinish(int rowsAffected) {
                L.d(LOG_TAG, "The number of updating pre-SignUp successfully:" + rowsAffected);
            }
        });
    }

    @Override
    public void saveAll(@NonNull final List<Activities> list) {

        DataSupport.saveAllAsync(list).listen(new SaveCallback() {
            @Override
            public void onFinish(boolean success) {
                L.d(LOG_TAG, "save all success " + success);
            }
        });
    }

    @Override
    public void getImagesUrl(@NonNull LoadBannerImagesCallback callback) {

    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
