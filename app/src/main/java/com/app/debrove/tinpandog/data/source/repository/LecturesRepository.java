package com.app.debrove.tinpandog.data.source.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.app.debrove.tinpandog.data.BannerResponse;
import com.app.debrove.tinpandog.data.Lectures;
import com.app.debrove.tinpandog.data.source.datasource.LecturesDataSource;
import com.app.debrove.tinpandog.util.L;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by debrove on 2017/12/2.
 * Package Name : com.app.debrove.tinpandog.data.source.repository
 */

public class LecturesRepository implements LecturesDataSource {

    private static final String LOG_TAG = LecturesRepository.class.getSimpleName();

    @Nullable
    private static LecturesRepository INSTANCE = null;

    @NonNull
    private final LecturesDataSource mRemoteDataSource;

    @NonNull
    private final LecturesDataSource mLocalDataSource;

    private Map<Integer, Lectures> mCachedItems;

    // Prevent direct instantiation.
    public LecturesRepository(@NonNull LecturesDataSource mRemoteDataSource,
                              @NonNull LecturesDataSource mLocalDataSource) {
        this.mRemoteDataSource = mRemoteDataSource;
        this.mLocalDataSource = mLocalDataSource;
    }

    public static LecturesRepository getInstance(@NonNull LecturesDataSource remoteDataSource,
                                                 @NonNull LecturesDataSource localDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new LecturesRepository(remoteDataSource, localDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getNews(final boolean clearCache, @NonNull final LoadNewsCallback callback) {
        // Get data by accessing network first.
        mRemoteDataSource.getNews(clearCache, new LoadNewsCallback() {
            @Override
            public void onNewsLoaded(@NonNull List<Lectures> list) {
                callback.onNewsLoaded(list);
                saveAll(list);
            }

            @Override
            public void onDataNotAvailable() {
                L.d(LOG_TAG, "remote data access error");
                mLocalDataSource.getNews(false, new LoadNewsCallback() {
                    @Override
                    public void onNewsLoaded(@NonNull List<Lectures> list) {
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
            public void onNewsLoaded(@NonNull List<Lectures> list) {
                for (Lectures item : list) {
                    L.d(LOG_TAG, "by time" + item.getNewsId() + "title" + item.getTitle() + item.isFavorite());
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
            public void onNewsLoaded(@NonNull List<Lectures> list) {
                for (Lectures item : list) {
                    L.d(LOG_TAG, "isPreSignUp " + item.isPre_sign_up());
                }
                callback.onNewsLoaded(list);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void getAllNewsSignedUp(@NonNull final LoadNewsCallback callback) {
        mLocalDataSource.getAllNewsSignedUp(new LoadNewsCallback() {
            @Override
            public void onNewsLoaded(@NonNull List<Lectures> lecturesList) {
                callback.onNewsLoaded(lecturesList);
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
            public void onNewsLoaded(@NonNull List<Lectures> list) {
                for (Lectures item : list) {
                    L.d(LOG_TAG, "getFavorite" + item.getNewsId() + "title" + item.getTitle() + item.isFavorite());
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

        Lectures cachedItem = getItemWithId(itemId);
        if (cachedItem != null) {
            cachedItem.setFavorite(favorite);
        }
    }

    @Override
    public void signUpItem(final int itemId, final boolean signUp, final String token, final LoadMessageCallback callback) {
        L.d(LOG_TAG, itemId + " " + signUp + " token " + token);
        mRemoteDataSource.signUpItem(itemId, signUp, token, new LoadMessageCallback() {
            @Override
            public void onMessageLoaded(@NonNull String message) {
                callback.onMessageLoaded(message);
                mLocalDataSource.signUpItem(itemId, signUp, token, callback);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });

    }

    @Override
    public void saveAll(@NonNull List<Lectures> list) {
        mLocalDataSource.saveAll(list);
        mRemoteDataSource.saveAll(list);

        L.d(LOG_TAG, "save all" + list);

        if (mCachedItems == null) {
            mCachedItems = new LinkedHashMap<>();
        }

        for (Lectures item : list) {
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

    @Override
    public void refreshToken(String telephone, final LoadTokenCallback callback) {
        mRemoteDataSource.refreshToken(telephone, new LoadTokenCallback() {
            @Override
            public void onInfoLoaded(@NonNull String token) {
                callback.onInfoLoaded(token);
                L.d(LOG_TAG, " token " + token);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
                L.d(LOG_TAG, "token error");
            }
        });
    }

    private Lectures getItemWithId(int itemId) {
        return (mCachedItems == null || mCachedItems.isEmpty()) ? null : mCachedItems.get(itemId);
    }

    private void refreshCache(boolean clearCache, List<Lectures> list) {

        if (mCachedItems == null) {
            mCachedItems = new LinkedHashMap<>();
        }
        if (clearCache) {
            mCachedItems.clear();
        }
        for (Lectures item : list) {
            mCachedItems.put(item.getNewsId(), item);
        }
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
