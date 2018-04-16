package com.app.debrove.tinpandog.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.app.debrove.tinpandog.R;
import com.app.debrove.tinpandog.data.Activities;
import com.app.debrove.tinpandog.data.BaseResponse;
import com.app.debrove.tinpandog.data.ContentType;
import com.app.debrove.tinpandog.data.Lectures;
import com.app.debrove.tinpandog.data.Place;
import com.app.debrove.tinpandog.retrofit.RetrofitService;
import com.app.debrove.tinpandog.util.GetInfos;
import com.app.debrove.tinpandog.util.L;
import com.app.debrove.tinpandog.util.ShareUtils;
import com.app.debrove.tinpandog.util.StaticClass;
import com.app.debrove.tinpandog.view.CustomEditText;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.UpdateOrDeleteCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cp4yin on 2017/12/18.
 * <p>
 * 签到
 */

public class SignInFragment extends Fragment implements SignInContract.View {

    private static final String LOG_TAG = SignInFragment.class.getSimpleName();

    private SignInContract.Presenter mPresenter;

    private Toolbar mToolbar;
    private CustomEditText mCustomEditText;
    private TextView mTvNumber;

    private List<Map<String, Object>> dataList;
    private SimpleAdapter mAdapter;

    private int mId;
    private ContentType mType;
    private String mAddress;
    private String token;
    private String mTelephone;

    public SignInFragment() {
        // Requires an empty constructor.
    }

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);

        initView(view);

        return view;
    }


    private void initView(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        mCustomEditText = view.findViewById(R.id.custom_et);
        mTvNumber = view.findViewById(R.id.tv_number);

        setHasOptionsMenu(true);
        SignInActivity activity = (SignInActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mId = getActivity().getIntent().getIntExtra(SignInActivity.KEY_ARTICLE_ID, -1);
        mType = (ContentType) getActivity().getIntent().getSerializableExtra(SignInActivity.KEY_ARTICLE_TYPE);
        mAddress = GetInfos.getPlace(mId);
        token = ShareUtils.getString(getContext(), StaticClass.KEY_ACCESS_TOKEN, "0");
        mTelephone = ShareUtils.getString(getContext(), StaticClass.KEY_USER_TELEPHONE, "");

        L.d(LOG_TAG, "id " + mId + " type " + mType + " add " + mAddress + " token " + token);

        SignIn(mId, mType, mAddress, token);
    }

    private void SignIn(final int itemId, final ContentType type, final String address, final String token) {

        final Integer a = (int) (Math.random() * (9999 - 1000 + 1)) + 1000;//产生1000-9999的随机数
        final String num = a.toString();
        mTvNumber.setText(num);
        L.d("random", a + " ");

        //输入完成后的监听
        mCustomEditText.setOnNumFinishListener(new CustomEditText.OnNumFinishListener() {
            @Override
            public void onComplete(String content) {
                L.d(LOG_TAG, num + " " + content);
                if (Objects.equals(content, num)) {

                    //这里直接调用接口，没有写在RemoteRepository
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(RetrofitService.URL_BASE)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    RetrofitService.SignInService service = retrofit.create(RetrofitService.SignInService.class);
                    service.signIn(token, itemId, address)
                            .enqueue(new Callback<BaseResponse>() {
                                @Override
                                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                                    if (response.isSuccessful()) {
                                        L.d(LOG_TAG, response.body().getMessage());
                                        Toast.makeText(getContext(), "签到成功", Toast.LENGTH_SHORT).show();
                                        updateSignInInfos(type, itemId);//更新本地数据库
                                        getActivity().finish();
//                                        callback.onMessageLoaded(response.body().getStatus(),response.body().getMessage());
                                    } else {
//                                        callback.onDataNotAvailable();
                                        L.d(LOG_TAG, " error " + response.errorBody());
                                        Toast.makeText(getContext(), "签到失败", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<BaseResponse> call, Throwable t) {
//                                    callback.onDataNotAvailable();
                                    Toast.makeText(getContext(), "签到失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                    L.d(LOG_TAG, "签到成功");
                }
            }
        });
    }

    private void updateSignInInfos(ContentType type, int itemId) {
        if (type == ContentType.TYPE_ACTIVITIES) {
            L.d(LOG_TAG, "itemId" + itemId);
            Activities activitiesToUpdate = new Activities();
            activitiesToUpdate.setSign_in(true);
            activitiesToUpdate.updateAllAsync("newsId = ?", String.valueOf(itemId)).listen(new UpdateOrDeleteCallback() {
                @Override
                public void onFinish(int rowsAffected) {
                    L.d(LOG_TAG, "The number of updating SignIn successfully:" + rowsAffected);
                }
            });
        } else if (type == ContentType.TYPE_LECTURES) {
            L.d(LOG_TAG, "itemId" + itemId);
            Lectures lecturesToUpdate = new Lectures();
            lecturesToUpdate.setSign_in(true);
            lecturesToUpdate.updateAllAsync("newsId = ?", String.valueOf(itemId)).listen(new UpdateOrDeleteCallback() {
                @Override
                public void onFinish(int rowsAffected) {
                    L.d(LOG_TAG, "The number of updating SignIn successfully:" + rowsAffected);
                }
            });
        }
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
    }

    @Override
    public void setPresenter(SignInContract.Presenter presenter) {
        if (presenter != null) {
            mPresenter = presenter;
        }
    }

    @Override
    public boolean isActive() {
        return isAdded() && isResumed();
    }

    @Override
    public void showSignInMessage(int status, String message) {
        if (status == 1) {
            Toast.makeText(getContext(), "签到成功", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getToken(String token) {
        //刷新token
        L.d(LOG_TAG, " new token " + token);
        ShareUtils.putString(getContext(), StaticClass.KEY_ACCESS_TOKEN, token);
    }

    @Override
    public void refreshToken() {
        mPresenter.refreshToken(mTelephone);
        L.d(LOG_TAG, "token in refreshing " + token);
    }

    @Override
    public void refreshSignIn(String token) {
//        L.d(LOG_TAG, " token " + token);
//        mPresenter.updateSignInInfo(mType, mId, isPreSignUp, token);
    }

//    private String getPlace(int id) {
//        String name = "未知";
//        List<Place> list = DataSupport.where("newsId = ?", String.valueOf(id)).find(Place.class);
//        L.d(LOG_TAG, "list " + list + "size " + list.size());
//        for (Place place1 : list) {
//            name = place1.getName();
//            L.d(LOG_TAG, " name " + name);
//        }
//        return name;
//    }
}
