package com.app.debrove.tinpandog.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.debrove.tinpandog.R;
import com.app.debrove.tinpandog.data.LoginResponse;
import com.app.debrove.tinpandog.data.User;
import com.app.debrove.tinpandog.favorites.FavoritesActivity;
import com.app.debrove.tinpandog.retrofit.RetrofitService;
import com.app.debrove.tinpandog.util.L;
import com.app.debrove.tinpandog.util.ShareUtils;
import com.app.debrove.tinpandog.util.StaticClass;

import org.litepal.crud.DataSupport;

import java.util.List;

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
 * Created by debrove on 2017/9/28.
 * Package Name : com.app.debrove.tinpandog.user
 */

public class UserFragment extends Fragment implements UserContract.View {

    private static final String LOG_TAG = UserFragment.class.getSimpleName();

    private UserContract.Presenter mPresenter;

    Unbinder unbinder;
    @BindView(R.id.toolbar_user)
    Toolbar mToolbarUser;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.telephone)
    TextView mTelephone;

    private DrawerLayout mDrawerLayout;

    private String token;
    private String telephone;
    private String name = "";
    private String stuNum = "";

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        unbinder = ButterKnife.bind(this, view);
        initView();
        //getUserInfoFromDB();
        //获取用户信息
        mPresenter.loadUserInfo(telephone, token);

        return view;
    }


    @Override
    public void showInfo(List<User> userInfo) {

        L.d(LOG_TAG, "userinfo " + userInfo);

        for (User user : userInfo) {
            name = user.getName();
            stuNum = user.getNumber();
        }
        L.d(LOG_TAG, "name " + name);
        mTelephone.setText(telephone);
        mUserName.setText(name);
        ShareUtils.putString(getContext(), StaticClass.KEY_USERNAME, name);
        ShareUtils.putString(getContext(), StaticClass.KEY_STU_NUM, stuNum);
    }

    @Override
    public void getToken(String token) {
        //刷新token
        L.d(LOG_TAG, " new token " + token);
        ShareUtils.putString(getContext(), StaticClass.KEY_ACCESS_TOKEN, token);
    }

    @Override
    public void refreshToken() {
        mPresenter.refreshToken(telephone);
        mPresenter.loadUserInfo(telephone, token);
        L.d(LOG_TAG, "token in refreshing " + token);
    }

/*    private void getUserInfoFromDB() {
        //获取注册或登录时输入的手机号，便于查询


        //更新用户信息
        getUserInfoFromNetwork();

        List<User> userList = DataSupport.where("telephone = ?", telephone).find(User.class);
        L.d(LOG_TAG, "userList" + userList);
        for (User user : userList) {
            name = user.getName();
            L.d(LOG_TAG, "name " + name);
            stuNum = user.getNumber();
        }
        mUserName.setText(name);
        mTelephone.setText(telephone);
    }*/


    //private void getUserInfoFromNetwork() {
    //还要刷新token
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(RetrofitService.URL_BASE)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        RetrofitService.UserService service = retrofit.create(RetrofitService.UserService.class);
//        service.getInfo(token)
//                .enqueue(new Callback<UserResponse>() {
//                    @Override
//                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
//                        if (response.isSuccessful()) {
//                            String name = response.body().getData().getName();
//                            long telephone = response.body().getData().getTelephone();
//                            int stuNum = response.body().getData().getNumber();
//                            L.d("userInfo", name + telephone + stuNum);
//
//                            //通过手机号码更新用户信息
//                            User user = new User();
//                            user.setName(name);
//                            user.setNumber(String.valueOf(stuNum));
//                            user.updateAll("telephone = ?", String.valueOf(telephone));
//                            mUserName.setText(name);
//                            //refreshToken();(测试刷新token)
//                        } else {
//                            L.d(LOG_TAG, "token过期或无效，获取用户信息失败");
//                            refreshToken();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<UserResponse> call, Throwable t) {
//                        L.d(LOG_TAG, "获取用户信息失败" + t.toString());
//                        Toast.makeText(getContext(), "获取用户信息失败" + t.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                });
    // }

//    private void refreshToken() {
//        String password = "";
//        List<User> userList = DataSupport.where("telephone = ?", telephone).find(User.class);
//        for (User user : userList) {
//            password = user.getPassword();
//        }
//
//        L.d(LOG_TAG, "telephone" + telephone + "password" + password);
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(RetrofitService.URL_BASE)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        RetrofitService.UserService service = retrofit.create(RetrofitService.UserService.class);
//        service.login(telephone, password)
//                .enqueue(new Callback<LoginResponse>() {
//                    @Override
//                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                        if (response.isSuccessful()) {
//                            L.d(LOG_TAG, "旧token" + token);
//                            token = response.body().getData().getToken();
//                            L.d(LOG_TAG, "获取token成功" + token);
//                            ShareUtils.putString(getContext(), StaticClass.KEY_ACCESS_TOKEN, token);
//                        } else {
//                            L.d(LOG_TAG, "获取Token失败");
//                            L.d(LOG_TAG, response.code() + "   " + response.errorBody());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<LoginResponse> call, Throwable t) {
//                        L.d(LOG_TAG, "获取Token失败");
//                    }
//                });
//
//    }

    private void initView() {
        //Toolbar
        setHasOptionsMenu(true);
        mToolbarUser.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        mToolbarUser.setTitle("我");
        mToolbarUser.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        mDrawerLayout = getActivity().findViewById(R.id.drawer);

        token = ShareUtils.getString(getContext(), StaticClass.KEY_ACCESS_TOKEN, "0");
        telephone = ShareUtils.getString(getContext(), StaticClass.KEY_USER_TELEPHONE, "");

        L.d(LOG_TAG, "telephone " + telephone + " token " + token);
    }

    @Override
    public void setPresenter(UserContract.Presenter presenter) {
        if (presenter != null) {
            mPresenter = presenter;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ll_favorite, R.id.ll_user_info, R.id.ll_user_settings, R.id.ll_attended,
            R.id.ll_cards, R.id.ll_comment, R.id.ll_notes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_favorite:
                Intent intent = new Intent(getActivity(), FavoritesActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_user_info:
                Intent intent1 = new Intent(getActivity(), UserInfoActivity.class);
                intent1.putExtra(UserInfoActivity.KEY_USER_TELEPHONE, telephone);
                intent1.putExtra(UserInfoActivity.KEY_USERNAME, name);
                intent1.putExtra(UserInfoActivity.KEY_USER_STU_NUM, stuNum);
                startActivity(intent1);
                break;
            case R.id.ll_user_settings:
                Intent intent2 = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent2);
                break;
            case R.id.ll_attended:
                Intent intent3 = new Intent(getActivity(), AttendedActivity.class);
                startActivity(intent3);
                break;
            case R.id.ll_cards:
            case R.id.ll_comment:
            case R.id.ll_notes:
                Toast.makeText(getContext(), "敬请期待", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }


    @Override
    public boolean isActive() {
        return isAdded() && isResumed();
    }
}
