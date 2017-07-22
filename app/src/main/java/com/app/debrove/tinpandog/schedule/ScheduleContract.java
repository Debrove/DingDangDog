package com.app.debrove.tinpandog.schedule;

import com.app.debrove.tinpandog.BasePresenter;
import com.app.debrove.tinpandog.BaseView;

/**
 * Created by debrove on 2017/7/17.
 * Package Name : com.app.debrove.tinpandog.schedule
 *
 * 统一管理Schedule的View和Presenter
 */

public interface ScheduleContract {
    interface View extends BaseView<Presenter>{

    }

    interface Presenter extends BasePresenter{

    }
}
