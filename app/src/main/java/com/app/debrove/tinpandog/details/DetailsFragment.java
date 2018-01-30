package com.app.debrove.tinpandog.details;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.debrove.tinpandog.R;
import com.app.debrove.tinpandog.data.ContentType;
import com.app.debrove.tinpandog.data.LoginResponse;
import com.app.debrove.tinpandog.retrofit.RetrofitService;
import com.app.debrove.tinpandog.signup.SignUpActivity;
import com.app.debrove.tinpandog.util.L;
import com.app.debrove.tinpandog.util.ShareUtils;
import com.app.debrove.tinpandog.util.StaticClass;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by debrove on 2017/10/5.
 * Package Name : com.app.debrove.tinpandog.details
 */

public class DetailsFragment extends Fragment implements DetailsContract.View {

    private static final String LOG_TAG = DetailsFragment.class.getSimpleName();

    @BindView(R.id.btn_sign_up)
    Button mBtnSignUp;
    @BindView(R.id.iv_favourite)
    ImageView mIvFavourite;
    @BindView(R.id.favourite)
    TextView mFavourite;
    @BindView(R.id.iv_details_header)
    ImageView mHeaderImage;
    @BindView(R.id.tv_details_title)
    TextView mTvDetailsTitle;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.toolbar_details)
    Toolbar mToolbarDetails;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.nested_scroll_view)
    NestedScrollView mNestedScrollView;
    Unbinder unbinder;
    @BindView(R.id.tv_details_time)
    TextView mTvDetailsTime;
    @BindView(R.id.tv_details_place)
    TextView mTvDetailsPlace;
    @BindView(R.id.tv_details_organizer)
    TextView mTvDetailsOrganizer;
    @BindView(R.id.ll_details_bottom_nav)
    LinearLayout mLlDetailsBottomNav;

    private DetailsContract.Presenter mPresenter;

    private int mId;
    private ContentType mType;
    private String mTitle;
    private String mTime;
    private String mPlace;
    private String mHolder;
    private String mContent;
    private String mImage;
    private String imageUrl;

    private boolean isPreSignUp;
    private boolean isFavorite;

    private String token;

    public DetailsFragment() {
        // Requires an empty constructor.
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mId = getActivity().getIntent().getIntExtra(DetailsActivity.KEY_ARTICLE_ID, -1);
        mType = (ContentType) getActivity().getIntent().getSerializableExtra(DetailsActivity.KEY_ARTICLE_TYPE);
        mTitle = getActivity().getIntent().getStringExtra(DetailsActivity.KEY_ARTICLE_TITLE);
        mTime = getActivity().getIntent().getStringExtra(DetailsActivity.KEY_ARTICLE_TIME);
        mPlace = getActivity().getIntent().getStringExtra(DetailsActivity.KEY_ARTICLE_PLACE);
        mHolder=getActivity().getIntent().getStringExtra(DetailsActivity.KEY_ARTICLE_HOLDER);
        mContent = getActivity().getIntent().getStringExtra(DetailsActivity.KEY_ARTICLE_CONTENT);
        isFavorite = getActivity().getIntent().getBooleanExtra(DetailsActivity.KEY_ARTICLE_IS_FAVORITE, false);
        isPreSignUp = getActivity().getIntent().getBooleanExtra(DetailsActivity.KEY_ARTICLE_IS_PRE_SIGN_UP, false);
        mImage = getActivity().getIntent().getStringExtra(DetailsActivity.KEY_ARTICLE_IMAGE);
//        imageUrl = StaticClass.HEADER_IMG_URL + mImage;
        L.d(LOG_TAG, "id " + mId + " title " + mTitle + " content " + mContent + " imgUrl " + mImage + " isFavorite " + isFavorite);
    }

    public static DetailsFragment newInstance() {
        return new DetailsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();

        setCover();
        showFavoriteButton(isFavorite);

        return view;
    }

    //设置图片
    private void setCover() {
        Picasso.with(getContext())
                .load(mImage)
                .placeholder(R.drawable.placeholder)
                .into(mHeaderImage);
        mHeaderImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    private void initView() {
        //初始化Toolbar
        setHasOptionsMenu(true);
        DetailsActivity activity = (DetailsActivity) getActivity();
        activity.setSupportActionBar(mToolbarDetails);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);

        mCollapsingToolbar.setTitleEnabled(false);
        mTvContent.setText(mContent);
        mTvDetailsTitle.setText(mTitle);
        mTvDetailsTime.setText(mTime);
        mTvDetailsPlace.setText(mPlace);
        mTvDetailsOrganizer.setText(mHolder);

        token = ShareUtils.getString(getContext(), StaticClass.KEY_ACCESS_TOKEN, "0");
//        isPreSignUp = ShareUtils.getBoolean(getContext(), StaticClass.KEY_IS_PRE_SIGN_UP, false);

        //设置底部栏高度
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int height = dm.heightPixels / 13;
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mLlDetailsBottomNav.getLayoutParams();
        params.height = height;
        mLlDetailsBottomNav.setLayoutParams(params);
        //设置NestedScrollView的margins
        CoordinatorLayout.LayoutParams params1 = (CoordinatorLayout.LayoutParams) mNestedScrollView.getLayoutParams();
        params1.setMargins(0, 0, 0, height);
        mNestedScrollView.setLayoutParams(params1);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_details, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(DetailsContract.Presenter presenter) {
        if (presenter != null) {
            mPresenter = presenter;
        }
    }

    @Override
    public boolean isActive() {
        return isAdded() && isResumed();
    }

    @OnClick({R.id.ll_favourite, R.id.btn_sign_up})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_favourite:
                isFavorite = !isFavorite;
                //收藏
                showFavoriteButton(isFavorite);
                L.d(LOG_TAG, mType + "   " + mId + "  " + isFavorite);

                mPresenter.favorite(mType, mId, isFavorite, mTitle);
                break;
            case R.id.btn_sign_up:
                //进入预报名界面
                Intent intent = new Intent(getContext(), SignUpActivity.class);
                intent.putExtra(SignUpActivity.KEY_ARTICLE_TYPE, mType);
                intent.putExtra(SignUpActivity.KEY_ARTICLE_TITLE, mTitle);
                intent.putExtra(SignUpActivity.KEY_ARTICLE_TIME, mTime);
                intent.putExtra(SignUpActivity.KEY_ARTICLE_ID, mId);
                intent.putExtra(SignUpActivity.KEY_ARTICLE_IS_PRE_SIGN_UP, isPreSignUp);
                startActivity(intent);

                //弹出Dialog
//                new AlertDialog.Builder(getContext())
//                        .setTitle("确认报名信息")
//                        .setMessage(StaticClass.INFO_PRE_SIGN_UP_NAME
//                                + ShareUtils.getString(getContext(), StaticClass.KEY_USER_NAME, "用户名") + "\n"
//                                + StaticClass.INFO_PRE_SIGN_UP_NUM
//                                + ShareUtils.getLong(getContext(), StaticClass.KEY_USER_TELEPHONE_NUM, 0))
//                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                if (!isPreSignUp) {
//                                    isPreSignUp = true;
//                                    //L.d(LOG_TAG, token + "  " + mId);
//                                    signUp(token, mId);
//                                    Toast.makeText(getContext(), "预报名成功", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(getContext(), "您已经成功报名了，无需再报", Toast.LENGTH_SHORT).show();
//                                }
//                                dialog.dismiss();
//                            }
//                        })
//                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        }).show();
                break;
        }
    }

    private void showFavoriteButton(boolean favorite) {
        if (favorite) {
            mFavourite.setText("取消收藏");
            mIvFavourite.setImageResource(R.drawable.ic_favorite_red_600_24dp);
        } else {
            mFavourite.setText("收藏");
            mIvFavourite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }
    }
}
