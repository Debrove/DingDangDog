package com.app.debrove.tinpandog.data.source.local;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.app.debrove.tinpandog.data.Lectures;
import com.app.debrove.tinpandog.data.source.datasource.LecturesDataSource;
import com.app.debrove.tinpandog.util.DateFormatUtils;
import com.app.debrove.tinpandog.util.L;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.SaveCallback;
import org.litepal.crud.callback.UpdateOrDeleteCallback;

import java.util.List;

/**
 * Created by debrove on 2017/12/2.
 * Package Name : com.app.debrove.tinpandog.data.source.local
 */

public class LecturesLocalDataSource implements LecturesDataSource {

    private static final String LOG_TAG = LecturesLocalDataSource.class.getSimpleName();

    @Nullable
    private static LecturesLocalDataSource INSTANCE = null;

    private LecturesLocalDataSource() {

    }

    public static LecturesLocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LecturesLocalDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getNewsByTime(long date, @NonNull LoadNewsCallback callback) {
        String time = DateFormatUtils.formatNewsDateLongToString(date);
        L.d(LOG_TAG, " time " + time + " " + date);
        List<Lectures> lecturesByTime = DataSupport.where("time = ?", time).find(Lectures.class);
        L.d(LOG_TAG, "by time " + lecturesByTime);
        callback.onNewsLoaded(lecturesByTime);
    }

    @Override
    public void getNews(boolean clearCache, @NonNull LoadNewsCallback callback) {
        List<Lectures> allLectures = DataSupport.findAll(Lectures.class);
        callback.onNewsLoaded(allLectures);
        L.d(LOG_TAG, "get news in local" + allLectures);
    }

    @Override
    public void getNewsSignedUp(long date, @NonNull LoadNewsCallback callback) {
        String time = DateFormatUtils.formatNewsDateLongToString(date);
        L.d(LOG_TAG, " time " + time + " " + date);
        List<Lectures> lectures = DataSupport.where("pre_sign_up = ? and time = ?", "1", time).find(Lectures.class);
        callback.onNewsLoaded(lectures);
        L.d(LOG_TAG, "getNewsSignedUp " + lectures);
    }

    @Override
    public void getAllNewsSignedUp(@NonNull LoadNewsCallback callback) {
        List<Lectures> allLecturesSignedUp=DataSupport.where("pre_sign_up = ?","1").find(Lectures.class);
        callback.onNewsLoaded(allLecturesSignedUp);
        L.d(LOG_TAG," all signed up "+allLecturesSignedUp);
    }

    @Override
    public void getFavorites(@NonNull LoadNewsCallback callback) {
        List<Lectures> allLectures = DataSupport.where("favorite = ?", "1").find(Lectures.class);
        callback.onNewsLoaded(allLectures);
        L.d(LOG_TAG, "getFavorite" + allLectures);
    }

    @Override
    public void favoriteItem(int itemId, boolean favorite, String title) {
        L.d(LOG_TAG, "itemId" + itemId + " " + favorite);
        Lectures lecturesToUpdate = new Lectures();
        //当favorite为false时，不能用setFavorite(false),而要用setToDefault("favorite")
        if (favorite) {
            lecturesToUpdate.setFavorite(true);
        } else {
            lecturesToUpdate.setToDefault("favorite");
        }
        lecturesToUpdate.updateAllAsync("newsId = ? ", String.valueOf(itemId)).listen(new UpdateOrDeleteCallback() {
            @Override
            public void onFinish(int rowsAffected) {
                L.d(LOG_TAG, "The number of updating successfully:" + rowsAffected);
            }
        });
    }

    @Override
    public void signUpItem(int itemId, boolean signUp, String token, LoadMessageCallback callback) {
        L.d(LOG_TAG, "itemId" + itemId + " " + signUp + " token " + token);
        Lectures lecturesToUpdate = new Lectures();
        lecturesToUpdate.setPre_sign_up(true);
        lecturesToUpdate.updateAllAsync("newsId = ?", String.valueOf(itemId)).listen(new UpdateOrDeleteCallback() {
            @Override
            public void onFinish(int rowsAffected) {
                L.d(LOG_TAG, "The number of updating pre-SignUp successfully:" + rowsAffected);
            }
        });
    }

    @Override
    public void saveAll(@NonNull List<Lectures> list) {
        DataSupport.saveAllAsync(list).listen(new SaveCallback() {
            @Override
            public void onFinish(boolean success) {
                L.d(LOG_TAG, "save all success" + success);
            }
        });
    }

    @Override
    public void getImagesUrl(@NonNull LoadBannerImagesCallback callback) {

    }

    @Override
    public void refreshToken(String telephone, LoadTokenCallback callback) {

    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
