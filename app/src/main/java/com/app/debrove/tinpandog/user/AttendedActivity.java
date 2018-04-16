package com.app.debrove.tinpandog.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.debrove.tinpandog.R;
import com.app.debrove.tinpandog.data.Activities;
import com.app.debrove.tinpandog.data.ContentType;
import com.app.debrove.tinpandog.data.Lectures;
import com.app.debrove.tinpandog.details.DetailsActivity;
import com.app.debrove.tinpandog.interfaze.OnRecyclerViewItemOnClickListener;
import com.app.debrove.tinpandog.util.GetInfos;
import com.app.debrove.tinpandog.util.L;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cp4yin on 2018/4/10.
 * package ：com.app.debrove.tinpandog.user
 * description：
 */

public class AttendedActivity extends AppCompatActivity {

    private static final String LOG_TAG = AttendedActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.empty_view)
    LinearLayout mEmptyView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    private AttendedAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attended);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        //初始化Toolbar
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        List<Activities> activitiesList = getActivitiesSignInList();
        List<Lectures> lecturesList = getLecturesSignInList();
        showList(activitiesList, lecturesList);
    }

    private void showList(final List<Activities> activitiesList, final List<Lectures> lecturesList) {
        if (activitiesList == null && lecturesList == null) {
            mEmptyView.setVisibility(View.VISIBLE);
            return;
        }

        L.d(LOG_TAG, activitiesList + " " + lecturesList);

        if (mAdapter == null && activitiesList != null && lecturesList != null) {
            mAdapter = new AttendedAdapter(this, activitiesList, lecturesList);

            mAdapter.setOnItemClickListener(new OnRecyclerViewItemOnClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    int viewType = mAdapter.getItemViewType(position);
                    L.d(LOG_TAG,"name "+activitiesList.get(mAdapter.getOriginalIndex(position)).getPlace_id().getName()+
                            "id "+activitiesList.get(mAdapter.getOriginalIndex(position)).getPlace_id().getId());
                    if (viewType == AttendedAdapter.ItemWrapper.TYPE_ACTIVITIES) {

                        Intent intent = new Intent(AttendedActivity.this, DetailsActivity.class);
                        //newsId为原始Id（而id为数据库重新整理后的id）
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_ID, activitiesList.get(mAdapter.getOriginalIndex(position)).getNewsId());
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_TYPE, ContentType.TYPE_ACTIVITIES);
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_TITLE, activitiesList.get(mAdapter.getOriginalIndex(position)).getTitle());
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_TIME, activitiesList.get(mAdapter.getOriginalIndex(position)).getTime());
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_HOLDER, activitiesList.get(mAdapter.getOriginalIndex(position)).getHolder());
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_CONTENT, activitiesList.get(mAdapter.getOriginalIndex(position)).getDetail());
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_IMAGE, activitiesList.get(mAdapter.getOriginalIndex(position)).getPhoto_url());
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_IS_FAVORITE, activitiesList.get(mAdapter.getOriginalIndex(position)).isFavourite());

                        String place = GetInfos.getPlace(activitiesList.get(mAdapter.getOriginalIndex(position)).getNewsId());
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_PLACE, place);

                        startActivity(intent);
                    } else if (viewType == AttendedAdapter.ItemWrapper.TYPE_LECTURES) {
                        Intent intent = new Intent(AttendedActivity.this, DetailsActivity.class);
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_ID, lecturesList.get(mAdapter.getOriginalIndex(position)).getNewsId());
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_TYPE, ContentType.TYPE_LECTURES);
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_TITLE, lecturesList.get(mAdapter.getOriginalIndex(position)).getTitle());
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_TIME, lecturesList.get(mAdapter.getOriginalIndex(position)).getTime());
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_HOLDER, lecturesList.get(mAdapter.getOriginalIndex(position)).getHolder());
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_CONTENT, lecturesList.get(mAdapter.getOriginalIndex(position)).getDetail());
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_IMAGE, lecturesList.get(mAdapter.getOriginalIndex(position)).getPhoto_url());
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_IS_FAVORITE, lecturesList.get(mAdapter.getOriginalIndex(position)).isFavorite());

                        String place = GetInfos.getPlace(lecturesList.get(mAdapter.getOriginalIndex(position)).getNewsId());
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_PLACE, place);
                        startActivity(intent);
                    }
                }
            });
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.updateData(activitiesList, lecturesList);
            Toast.makeText(this, "已刷新", Toast.LENGTH_SHORT).show();
        }
        mRecyclerView.setVisibility(activitiesList.isEmpty() && lecturesList.isEmpty() ? View.GONE : View.VISIBLE);
        mEmptyView.setVisibility(activitiesList.isEmpty() && lecturesList.isEmpty() ? View.VISIBLE : View.GONE);
    }


    private List<Activities> getActivitiesSignInList() {
        List<Activities> allActivitiesSignIn = DataSupport.where("sign_in = ?", "1").find(Activities.class);
        L.d(LOG_TAG, " all signed in " + allActivitiesSignIn);
        return allActivitiesSignIn;
    }

    private List<Lectures> getLecturesSignInList() {
        List<Lectures> allLecturesSignIn = DataSupport.where("sign_in = ?", "1").find(Lectures.class);
        L.d(LOG_TAG, " all signed in " + allLecturesSignIn);
        return allLecturesSignIn;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return true;
    }
}
