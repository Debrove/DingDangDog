package com.app.debrove.tinpandog.schedule;

import com.app.debrove.tinpandog.BasePresenter;
import com.app.debrove.tinpandog.BaseView;
import com.app.debrove.tinpandog.data.Activities;
import com.app.debrove.tinpandog.data.Lectures;

import java.util.List;

/**
 * Created by debrove on 2017/7/17.
 * Package Name : com.app.debrove.tinpandog.schedule
 * <p>
 * 统一管理Schedule的View和Presenter
 */

public interface ScheduleContract {
    interface View extends BaseView<Presenter> {

        boolean isActive();

        void setLoadingIndicator(boolean active);

        void showList(List<Activities> activitiesList,
                      List<Lectures> lecturesList);
    }

    interface Presenter extends BasePresenter {

        void loadList(long date);
    }
}
