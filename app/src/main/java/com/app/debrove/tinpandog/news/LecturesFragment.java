package com.app.debrove.tinpandog.news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.debrove.tinpandog.R;
import com.app.debrove.tinpandog.data.BannerResponse;
import com.app.debrove.tinpandog.data.ContentType;
import com.app.debrove.tinpandog.data.Lectures;
import com.app.debrove.tinpandog.details.DetailsActivity;
import com.app.debrove.tinpandog.interfaze.OnRecyclerViewItemOnClickListener;
import com.app.debrove.tinpandog.util.L;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by debrove on 2017/10/21.
 * Package Name : com.app.debrove.tinpandog.news
 */

public class LecturesFragment extends Fragment implements LecturesContract.View {

    private static final String LOG_TAG = LecturesFragment.class.getSimpleName();

    private LecturesContract.Presenter mPresenter;

    // View references.
    private FloatingActionButton mFab;
    //@BindView(R.id.recycler_view)
    private RecyclerView mRecyclerView;
    //@BindView(R.id.empty_view)
    LinearLayout mEmptyView;
    //@BindView(R.id.refresh_layout)
    private SwipeRefreshLayout mRefreshLayout;
    Unbinder unbinder;

    private ConvenientBanner convenientBanner;//轮播图
    private LecturesAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private int mYear, mMonth, mDay;
    private boolean mIsFirstLoad = true;
    private int mListSize = 0;

    private List<String> networkImages=new ArrayList<>();

    public LecturesFragment() {
    }

    public static LecturesFragment newInstance() {
        return new LecturesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_page, container, false);

        initView(view);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void initView(View view) {
        //配置SwipeRefreshLayout
        mRefreshLayout = view.findViewById(R.id.refresh_layout);
        mRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorAccent));
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Calendar c = Calendar.getInstance();
                c.setTimeZone(TimeZone.getTimeZone("GMT+08"));
                mPresenter.loadNews(true);
                mPresenter.loadBannerUrl();
                mRefreshLayout.setRefreshing(false);
            }
        });

        //配置RecyclerView
        mEmptyView = view.findViewById(R.id.empty_view);
        mFab = getActivity().findViewById(R.id.fab);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //判断RecyclerView滑动
                if (dy > 0) {
                    mFab.hide();
                    if (mLayoutManager.findLastCompletelyVisibleItemPosition() == mListSize - 1) {
                        loadMore();
                    }
                } else {
                    mFab.show();
                }
            }
        });

        convenientBanner = (ConvenientBanner) LayoutInflater.from(getContext()).inflate(R.layout.banner_header, null);
        convenientBanner.setLayoutParams(
                new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        getActivity().getWindowManager().getDefaultDisplay().getHeight() / 4));
    }

    //获取轮播图
    public class NetWorkImageHolderView implements Holder<String> {

        private ImageView imageView;

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.banner_header_img, null);
            imageView = view.findViewById(R.id.iv_head);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return view;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            //L.d("imgUrl", "UpdateUI: " + data);
            Picasso.with(context)
                    .load(data)
                    .placeholder(R.drawable.placeholder)
                    .into(imageView);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(LecturesContract.Presenter presenter) {
        if (presenter != null) {
            mPresenter = presenter;
        }
    }

    @Override
    public void showResult(@NonNull final List<Lectures> list) {
        if (mAdapter == null) {
            mAdapter = new LecturesAdapter(list, getContext());
            mAdapter.setItemClickListener(new OnRecyclerViewItemOnClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    Intent intent = new Intent(getActivity(), DetailsActivity.class);
                    //多了Header轮播，位置-1
                    int pos = position - 1;
                    L.d(LOG_TAG, "id:" + list.get(pos).getId() + " id saved " + list.get(pos).getNewsId() + " isFavorite " + list.get(pos).isFavorite());
                    intent.putExtra(DetailsActivity.KEY_ARTICLE_ID, list.get(pos).getNewsId());
                    intent.putExtra(DetailsActivity.KEY_ARTICLE_TYPE, ContentType.TYPE_LECTURES);
                    intent.putExtra(DetailsActivity.KEY_ARTICLE_TITLE, list.get(pos).getTitle());
                    intent.putExtra(DetailsActivity.KEY_ARTICLE_TIME, list.get(pos).getTime());
                    intent.putExtra(DetailsActivity.KEY_ARTICLE_PLACE, list.get(pos).getPlace_id().getName());
                    intent.putExtra(DetailsActivity.KEY_ARTICLE_HOLDER,list.get(pos).getHolder());
                    intent.putExtra(DetailsActivity.KEY_ARTICLE_CONTENT, list.get(pos).getDetail());
                    intent.putExtra(DetailsActivity.KEY_ARTICLE_IMAGE, list.get(pos).getPhoto_url());

                    //查询对应新闻的favorite
                    List<Lectures> activities = DataSupport.select("favorite","pre_sign_up")
                            .where("newsId = ?", String.valueOf(list.get(pos).getNewsId()))
                            .find(Lectures.class);
                    boolean isFavorite = false;
                    boolean isPreSignUp = false;
                    for (Lectures item : activities) {
                        isFavorite = item.isFavorite();
                        isPreSignUp = item.isPre_sign_up();
                        L.d(LOG_TAG, "isFavorite" + isFavorite);
                    }
                    intent.putExtra(DetailsActivity.KEY_ARTICLE_IS_FAVORITE, isFavorite);
                    intent.putExtra(DetailsActivity.KEY_ARTICLE_IS_PRE_SIGN_UP, isPreSignUp);
                    startActivity(intent);
                }
            });
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.updateData(list);
        }
        mAdapter.addHeaderView(convenientBanner);
        mEmptyView.setVisibility(list.isEmpty() ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void showBannerImages(@NonNull List<BannerResponse.DataBean> list) {
        if (list.isEmpty()) {
            for (BannerResponse.DataBean url : list) {
                L.d(LOG_TAG, url.getPhoto_url());
                networkImages.add(url.getPhoto_url());
            }
        } else {
            //updateImages
            networkImages.clear();
            for (BannerResponse.DataBean url : list) {
                L.d(LOG_TAG, "update" + url.getPhoto_url());
                networkImages.add(url.getPhoto_url());
            }
        }

        convenientBanner.setPages(
                new CBViewHolderCreator<NetWorkImageHolderView>() {
                    @Override
                    public NetWorkImageHolderView createHolder() {
                        return new NetWorkImageHolderView();
                    }
                }, networkImages)
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setPageIndicator(new int[]{R.drawable.banner_header_indicator_unselected, R.drawable.banner_header_indicator_selected})
                .setScrollDuration(3000);
    }

    @Override
    public boolean isActive() {
        return isAdded() && isResumed();
    }

    @Override
    public void onResume() {
        super.onResume();
        convenientBanner.startTurning(3000);
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        c.set(mYear, mMonth, mDay);
        setLoadingIndicator(mIsFirstLoad);
        if (mIsFirstLoad) {
            mPresenter.loadNews(true);
            mIsFirstLoad = false;
        } else {
            mPresenter.loadNews(true);
        }
        mPresenter.loadBannerUrl();
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

    public void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        c.set(mYear, mMonth, mDay);
        DatePickerDialog dialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
                c.set(mYear, monthOfYear, mDay);

                mPresenter.loadNewsByTime(c.getTimeInMillis());
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        dialog.setMaxDate(Calendar.getInstance());

        Calendar minDate = Calendar.getInstance();
        minDate.set(2013, 5, 20);
        dialog.setMinDate(minDate);
        dialog.vibrate(false);
//        dialog.setAccentColor(getResources().getColor(R.color.colorPrimary));

        dialog.show(getActivity().getFragmentManager(), NewsFragment.class.getSimpleName());
    }

    //加载更多内容
    private void loadMore() {
        Calendar c = Calendar.getInstance();
        c.set(mYear, mMonth, --mDay);
        mPresenter.loadNewsByTime(c.getTimeInMillis());
    }

    @Override
    public void onPause() {
        super.onPause();
        convenientBanner.stopTurning();
    }
}
