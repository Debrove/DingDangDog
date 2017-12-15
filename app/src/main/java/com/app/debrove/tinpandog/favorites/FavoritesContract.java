package com.app.debrove.tinpandog.favorites;

import com.app.debrove.tinpandog.BasePresenter;
import com.app.debrove.tinpandog.BaseView;
import com.app.debrove.tinpandog.data.Activities;
import com.app.debrove.tinpandog.data.Lectures;

import java.util.List;

/**
 * Created by debrove on 2017/11/21.
 * Package Name : com.app.debrove.tinpandog.favorites
 */

public interface FavoritesContract {
    interface View extends BaseView<Presenter> {

        boolean isActive();

        void setLoadingIndicator(boolean active);

        void showFavorites(List<Activities> activitiesList,
                           List<Lectures> lecturesList);

    }

    interface Presenter extends BasePresenter {

        void loadFavorites();

    }
}
