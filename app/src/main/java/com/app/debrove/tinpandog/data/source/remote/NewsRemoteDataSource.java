package com.app.debrove.tinpandog.data.source.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.app.debrove.tinpandog.data.News;
import com.app.debrove.tinpandog.data.source.datasource.NewsDataSource;
import com.app.debrove.tinpandog.util.L;

import java.util.Collections;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by debrove on 2017/9/21.
 * Package Name : com.app.debrove.tinpandog.data.source.remote
 */

public class NewsRemoteDataSource implements NewsDataSource {

    private static final String LOG_TAG = NewsRemoteDataSource.class.getSimpleName();

    @Nullable
    private static NewsRemoteDataSource INSTANCE = null;

    // Prevent direct instantiation.
    private NewsRemoteDataSource() {
    }

    public static NewsRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NewsRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getNews(long date, @NonNull final loadNewsCallback callback) {
        BmobQuery<News> bmobQuery = new BmobQuery<>();
        //根据列名来查找数据
        bmobQuery.addQueryKeys("title,date,text,imgUrl,content,count");
        bmobQuery.findObjects(new FindListener<News>() {
            @Override
            public void done(List<News> list, BmobException e) {
                if (e == null) {
                    //按时间最新排序
                    Collections.sort(list);
                    Collections.reverse(list);
                    callback.onNewsLoaded(list);
                } else {
                    L.i(LOG_TAG,"查询失败"+e.toString());
                }
            }
        });
    }
}
