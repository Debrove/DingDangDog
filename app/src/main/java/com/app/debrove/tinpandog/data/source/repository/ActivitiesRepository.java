package com.app.debrove.tinpandog.data.source.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.app.debrove.tinpandog.data.Activities;
import com.app.debrove.tinpandog.data.BannerResponse;
import com.app.debrove.tinpandog.data.source.datasource.ActivitiesDataSource;
import com.app.debrove.tinpandog.util.L;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by debrove on 2017/9/17.
 * Package Name : com.app.debrove.tinpandog.data.source.repository
 * <p>
 * Use the remote data source firstly, which is obtained from the server.
 * If the remote data was not available, then use the local data source,
 * which was from the locally persisted in database.
 */

public class ActivitiesRepository implements ActivitiesDataSource {

    private static final String LOG_TAG = ActivitiesRepository.class.getSimpleName();

    @Nullable
    private static ActivitiesRepository INSTANCE = null;

    @NonNull
    private final ActivitiesDataSource mRemoteDataSource;

    @NonNull
    private final ActivitiesDataSource mLocalDataSource;

    private Map<Integer, Activities> mCachedItems;

    // Prevent direct instantiation.
    public ActivitiesRepository(@NonNull ActivitiesDataSource mRemoteDataSource,
                                @NonNull ActivitiesDataSource mLocalDataSource) {
        this.mRemoteDataSource = mRemoteDataSource;
        this.mLocalDataSource = mLocalDataSource;
    }

    public static ActivitiesRepository getInstance(@NonNull ActivitiesDataSource remoteDataSource,
                                                   @NonNull ActivitiesDataSource localDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new ActivitiesRepository(remoteDataSource, localDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getNews(final boolean clearCache, @NonNull final LoadNewsCallback callback) {
        // Get data by accessing network first.
        mRemoteDataSource.getNews(clearCache, new LoadNewsCallback() {
            @Override
            public void onNewsLoaded(@NonNull List<Activities> list) {
                callback.onNewsLoaded(list);
                saveAll(list);
            }

            @Override
            public void onDataNotAvailable() {
                L.d(LOG_TAG, "data access error");
                mLocalDataSource.getNews(false, new LoadNewsCallback() {
                    @Override
                    public void onNewsLoaded(@NonNull List<Activities> list) {
                        refreshCache(clearCache, list);
                        callback.onNewsLoaded(new ArrayList<>(mCachedItems.values()));
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });
    }

    @Override
    public void getNewsByTime(long date, @NonNull final LoadNewsCallback callback) {
        mLocalDataSource.getNewsByTime(date, new LoadNewsCallback() {
            @Override
            public void onNewsLoaded(@NonNull List<Activities> list) {
                for (Activities item : list) {
                    L.d(LOG_TAG, "by time" + item.getNewsId() + "title" + item.getTitle() + item.isFavourite());
                }
                callback.onNewsLoaded(list);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getNewsSignedUp(long date, @NonNull final LoadNewsCallback callback) {
        mLocalDataSource.getNewsSignedUp(date, new LoadNewsCallback() {
            @Override
            public void onNewsLoaded(@NonNull List<Activities> list) {
                for (Activities item : list) {
                    L.d(LOG_TAG, "isPreSignUp " + item.isPre_sign_up());
                }
                callback.onNewsLoaded(list);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getFavorites(@NonNull final LoadNewsCallback callback) {
        mLocalDataSource.getFavorites(new LoadNewsCallback() {
            @Override
            public void onNewsLoaded(@NonNull List<Activities> list) {
                for (Activities item : list) {
                    L.d(LOG_TAG, "getFavorite" + item.getNewsId() + "title" + item.getTitle() + item.isFavourite());
                }
                callback.onNewsLoaded(list);

            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void favoriteItem(int itemId, boolean favorite, String title) {
        L.d(LOG_TAG, itemId + " " + favorite);
        mRemoteDataSource.favoriteItem(itemId, favorite, title);
        mLocalDataSource.favoriteItem(itemId, favorite, title);

        Activities cachedItem = getItemWithId(itemId);
        if (cachedItem != null) {
            cachedItem.setFavourite(favorite);
        }
    }

    @Override
    public void signUpItem(int itemId, boolean signUp, String token) {
        L.d(LOG_TAG, itemId + " " + signUp + " token " + token);
        mRemoteDataSource.signUpItem(itemId, signUp, token);
        mLocalDataSource.signUpItem(itemId, signUp, token);

    }

    @Override
    public void saveAll(@NonNull List<Activities> list) {
        mLocalDataSource.saveAll(list);
        mRemoteDataSource.saveAll(list);

        L.d(LOG_TAG, "save all" + list);

        if (mCachedItems == null) {
            mCachedItems = new LinkedHashMap<>();
        }

        for (Activities item : list) {
            mCachedItems.put(item.getNewsId(), item);
        }
    }

    @Override
    public void getImagesUrl(@NonNull final LoadBannerImagesCallback callback) {
        mRemoteDataSource.getImagesUrl(new LoadBannerImagesCallback() {
            @Override
            public void onUrlLoaded(@NonNull List<BannerResponse.DataBean> list) {
                callback.onUrlLoaded(list);
                L.d(LOG_TAG, " " + list);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private Activities getItemWithId(int itemId) {
        return (mCachedItems == null || mCachedItems.isEmpty()) ? null : mCachedItems.get(itemId);
    }

    private void refreshCache(boolean clearCache, List<Activities> list) {

        if (mCachedItems == null) {
            mCachedItems = new LinkedHashMap<>();
        }
        if (clearCache) {
            mCachedItems.clear();
        }
        for (Activities item : list) {
            mCachedItems.put(item.getNewsId(), item);
        }
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
