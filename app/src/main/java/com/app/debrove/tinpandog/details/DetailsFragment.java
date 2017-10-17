package com.app.debrove.tinpandog.details;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.debrove.tinpandog.R;
import com.app.debrove.tinpandog.util.ShareUtils;
import com.app.debrove.tinpandog.util.StaticClass;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by debrove on 2017/10/5.
 * Package Name : com.app.debrove.tinpandog.details
 */

public class DetailsFragment extends Fragment implements DetailsContract.View {

    private DetailsContract.Presenter mPresenter;

    @BindView(R.id.image_view)
    ImageView mImageView;
    @BindView(R.id.toolbar_details)
    Toolbar mToolbarDetails;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.nested_scroll_view)
    NestedScrollView mNestedScrollView;
    Unbinder unbinder;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;

    private int mId;
    private String mTitle;
    private String mContent;
    private String mImage;
    private int mCount;

    private boolean isPreSignUp = false;

    public DetailsFragment() {
        // Requires an empty constructor.
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mId = getActivity().getIntent().getIntExtra(DetailsActivity.KEY_ARTICLE_ID, -1);
        mTitle = getActivity().getIntent().getStringExtra(DetailsActivity.KEY_ARTICLE_TITLE);
        mContent = getActivity().getIntent().getStringExtra(DetailsActivity.KEY_ARTICLE_CONTENT);
        mImage = getActivity().getIntent().getStringExtra(DetailsActivity.KEY_ARTICLE_IMAGE);
        mCount = getActivity().getIntent().getIntExtra(DetailsActivity.KEY_COUNT_PRE_SIGN_UP, 0);
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

        return view;
    }

    //设置图片
    private void setCover() {
        Picasso.with(getContext())
                .load(mImage)
                .placeholder(R.drawable.placeholder)
                .into(mImageView);
    }

    private void initView() {
        //初始化Toolbar
        setHasOptionsMenu(true);
        DetailsActivity activity = (DetailsActivity) getActivity();
        activity.setSupportActionBar(mToolbarDetails);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);

        setCollapsingToolbarLayoutTitle(mTitle);
        mTvContent.setText(mContent);
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

    @OnClick(R.id.btn_sign_up)
    public void onViewClicked() {
        //弹出Dialog
        new AlertDialog.Builder(getContext())
                .setTitle("确认报名信息")
                .setMessage(StaticClass.INFO_PRE_SIGN_UP_NAME
                        + ShareUtils.getString(getContext(), StaticClass.KEY_USER_NAME , "用户名")+"\n"
                        + StaticClass.INFO_PRE_SIGN_UP_NUM
                        + ShareUtils.getString(getContext(), StaticClass.KEY_USER_NUM, "学号")
                        + "\n当前报名人数：" + mCount)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!isPreSignUp) {
                            Toast.makeText(getContext(), "预报名成功", Toast.LENGTH_SHORT).show();
                            isPreSignUp = true;
                            mCount++;
                        } else {
                            Toast.makeText(getContext(), "您已经成功报名了，无需再报", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    // to change the title's font size of toolbar layout
    private void setCollapsingToolbarLayoutTitle(String title) {
        mToolbarLayout.setTitle(title);
        mToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        mToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        mToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBarPlus1);
        mToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarPlus1);
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
}
