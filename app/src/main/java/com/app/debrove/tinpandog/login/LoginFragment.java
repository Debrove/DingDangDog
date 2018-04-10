package com.app.debrove.tinpandog.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.app.debrove.tinpandog.MainActivity;
import com.app.debrove.tinpandog.R;
import com.app.debrove.tinpandog.data.LoginResponse;
import com.app.debrove.tinpandog.data.User;
import com.app.debrove.tinpandog.retrofit.RetrofitService;
import com.app.debrove.tinpandog.util.L;
import com.app.debrove.tinpandog.util.ShareUtils;
import com.app.debrove.tinpandog.util.StaticClass;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.SaveCallback;

import java.util.Objects;

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
 * Created by debrove on 2017/10/14.
 * Package Name : com.app.debrove.tinpandog.login
 */

public class LoginFragment extends Fragment {

    private final static String LOG_TAG = LoginFragment.class.getSimpleName();

    @BindView(R.id.user_password)
    EditText mUserPassword;
    @BindView(R.id.user_telephone)
    EditText mUserTelephone;
    Unbinder unbinder;
    @BindView(R.id.til_telephone)
    TextInputLayout mTilTelephone;
    @BindView(R.id.til_password)
    TextInputLayout mTilPassword;

    public LoginFragment() {
        // Requires the empty constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //登录点击事件
    @OnClick(R.id.btn_login)
    public void onViewClicked() {

        final String telephone = mUserTelephone.getText().toString().trim();
        final String password = mUserPassword.getText().toString().trim();

        if (!TextUtils.isEmpty(telephone)) {
            mTilTelephone.setErrorEnabled(false);
            mTilTelephone.setError("");
        } else if (!TextUtils.isEmpty(password)) {
            mTilPassword.setErrorEnabled(false);
            mTilPassword.setError("");
        }

        if (!TextUtils.isEmpty(telephone) & !TextUtils.isEmpty(password)) {
            mTilTelephone.setErrorEnabled(false);
            mTilPassword.setErrorEnabled(false);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(RetrofitService.URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RetrofitService.UserService service = retrofit.create(RetrofitService.UserService.class);
            Call<LoginResponse> call = service.login(telephone, password);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        if (Objects.equals(response.body().getStatus(), "1")) {
                            String token = response.body().getData().getToken();
                            L.d("user_login_success", response.body().getMessage() + "  ");
                            //Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            //保存登陆状态
                            ShareUtils.putBoolean(getContext(), StaticClass.SHARE_IS_LOG_IN, true);

                            //保留手机号，便于查询用户信息
                            ShareUtils.putString(getContext(), StaticClass.KEY_USER_TELEPHONE, telephone);
                            ShareUtils.putString(getContext(), StaticClass.KEY_ACCESS_TOKEN, token);

                            //保存新用户信息，剩余信息在UserFragment中保存
                            //若为老用户(本地数据库中存在)，则保存失败
                            User user = new User();
                            user.setTelephone(telephone);
                            user.setPassword(password);
                            user.saveAsync().listen(new SaveCallback() {
                                @Override
                                public void onFinish(boolean success) {
                                    L.d(LOG_TAG, " save in login " + success);
                                }
                            });

                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    } else if (response.code() == 403) {
                        //用户名或者密码错误时，服务器返回403。其他暂未发现
                        Toast.makeText(getContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
                        L.d(LOG_TAG, "用户名或密码错误");
                        L.d(LOG_TAG, "登录失败" + response.errorBody() + " " + response.code() + " " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    L.d("user_login_error", t.getMessage());
                    L.d("user_login_error", t.toString());
                }
            });
        } else if (TextUtils.isEmpty(telephone)) {
            mTilTelephone.setErrorEnabled(true);
            mTilTelephone.setError("手机号不能为空");
        } else if (TextUtils.isEmpty(password)) {
            mTilPassword.setErrorEnabled(true);
            mTilPassword.setError("密码不能为空");
        }


    }
}
