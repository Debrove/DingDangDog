package com.app.debrove.tinpandog.favorites;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.debrove.tinpandog.R;
import com.app.debrove.tinpandog.data.Activities;
import com.app.debrove.tinpandog.data.ContentType;
import com.app.debrove.tinpandog.data.Lectures;
import com.app.debrove.tinpandog.details.DetailsActivity;
import com.app.debrove.tinpandog.interfaze.OnRecyclerViewItemOnClickListener;
import com.app.debrove.tinpandog.util.L;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by debrove on 2017/11/21.
 * Package Name : com.app.debrove.tinpandog.favorites
 */

public class FavoritesFragment extends Fragment implements FavoritesContract.View {

    private static final String LOG_TAG = FavoritesFragment.class.getSimpleName();

    private FavoritesContract.Presenter mPresenter;

    //@BindView(R.id.toolbar)
    Toolbar mToolbar;
    //    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.empty_view)
    LinearLayout mEmptyView;
    //@BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    Unbinder unbinder;

    private FavoritesAdapter mAdapter;

    public FavoritesFragment() {
        // Empty constructor is needed as a fragment
    }

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.framgent_favorites, container, false);

        initViews(view);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadFavorites();
                setLoadingIndicator(false);
            }
        });

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void initViews(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        setHasOptionsMenu(true);
        FavoritesActivity activity = (FavoritesActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRefreshLayout = view.findViewById(R.id.refresh_layout);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
        mPresenter.loadFavorites();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                L.d(LOG_TAG, "back");
                getActivity().onBackPressed();
                return true;
        }
        return true;
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(active);
            }
        });
    }

    @Override
    public void showFavorites(final List<Activities> activitiesList, final List<Lectures> lecturesList) {
        if (activitiesList == null && lecturesList == null) {
            mEmptyView.setVisibility(View.VISIBLE);
            return;
        }
        L.d(LOG_TAG, activitiesList + " " + lecturesList);

        if (mAdapter == null && activitiesList != null && lecturesList != null) {
            mAdapter = new FavoritesAdapter(getContext(), activitiesList, lecturesList);

            mAdapter.setOnItemClickListener(new OnRecyclerViewItemOnClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    int viewType = mAdapter.getItemViewType(position);
//                    L.d(LOG_TAG,"name "+activitiesList.get(mAdapter.getOriginalIndex(position)).getPlace_id().getName()+
//                            "id "+activitiesList.get(mAdapter.getOriginalIndex(position)).getPlace_id().getId());
                    if (viewType == FavoritesAdapter.ItemWrapper.TYPE_ACTIVITIES) {

                        Intent intent = new Intent(getActivity(), DetailsActivity.class);
                        //newsId为原始Id（而id为数据库重新整理后的id）
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_ID, activitiesList.get(mAdapter.getOriginalIndex(position)).getNewsId());
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_TYPE, ContentType.TYPE_ACTIVITIES);
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_TITLE, activitiesList.get(mAdapter.getOriginalIndex(position)).getTitle());
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_TIME, activitiesList.get(mAdapter.getOriginalIndex(position)).getTime());
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_HOLDER, activitiesList.get(mAdapter.getOriginalIndex(position)).getHolder());
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_CONTENT, activitiesList.get(mAdapter.getOriginalIndex(position)).getDetail());
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_IMAGE, activitiesList.get(mAdapter.getOriginalIndex(position)).getPhoto_url());
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_IS_FAVORITE, activitiesList.get(mAdapter.getOriginalIndex(position)).isFavourite());

                        String place = mAdapter.getPlace(activitiesList.get(mAdapter.getOriginalIndex(position)).getNewsId());
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_PLACE, place);

                        startActivity(intent);
                    } else if (viewType == FavoritesAdapter.ItemWrapper.TYPE_LECTURES) {
                        Intent intent = new Intent(getActivity(), DetailsActivity.class);
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_ID, lecturesList.get(mAdapter.getOriginalIndex(position)).getNewsId());
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_TYPE, ContentType.TYPE_LECTURES);
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_TITLE, lecturesList.get(mAdapter.getOriginalIndex(position)).getTitle());
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_TIME, lecturesList.get(mAdapter.getOriginalIndex(position)).getTime());
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_HOLDER, lecturesList.get(mAdapter.getOriginalIndex(position)).getHolder());
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_CONTENT, lecturesList.get(mAdapter.getOriginalIndex(position)).getDetail());
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_IMAGE, lecturesList.get(mAdapter.getOriginalIndex(position)).getPhoto_url());
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_IS_FAVORITE, lecturesList.get(mAdapter.getOriginalIndex(position)).isFavorite());

                        String place = mAdapter.getPlace(lecturesList.get(mAdapter.getOriginalIndex(position)).getNewsId());
                        intent.putExtra(DetailsActivity.KEY_ARTICLE_PLACE, place);
                        startActivity(intent);
                    }
                }
            });
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.updateData(activitiesList, lecturesList);
            Toast.makeText(getContext(), "已刷新", Toast.LENGTH_SHORT).show();
        }
        mRecyclerView.setVisibility(activitiesList.isEmpty() && lecturesList.isEmpty() ? View.GONE : View.VISIBLE);
        mEmptyView.setVisibility(activitiesList.isEmpty() && lecturesList.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(FavoritesContract.Presenter presenter) {
        if (presenter != null) {
            mPresenter = presenter;
        }
    }

    @Override
    public boolean isActive() {
        return isAdded() && isResumed();
    }
}
