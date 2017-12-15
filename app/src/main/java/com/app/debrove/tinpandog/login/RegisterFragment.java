package com.app.debrove.tinpandog.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.app.debrove.tinpandog.MainActivity;
import com.app.debrove.tinpandog.R;
import com.app.debrove.tinpandog.data.User;
import com.app.debrove.tinpandog.retrofit.RetrofitService;
import com.app.debrove.tinpandog.util.L;
import com.app.debrove.tinpandog.util.ShareUtils;
import com.app.debrove.tinpandog.util.StaticClass;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.SaveCallback;

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

public class RegisterFragment extends Fragment {

    private static final String LOG_TAG = RegisterFragment.class.getSimpleName();

    @BindView(R.id.user_name)
    EditText mUserName;
    @BindView(R.id.user_password)
    EditText mUserPassword;
    Unbinder unbinder;
    @BindView(R.id.stu_number)
    EditText mStuNumber;
    @BindView(R.id.phone_number)
    EditText mPhoneNumber;
    @BindView(R.id.til_phone_number)
    TextInputLayout mTilPhoneNumber;
    @BindView(R.id.til_name)
    TextInputLayout mTilName;
    @BindView(R.id.til_password)
    TextInputLayout mTilPassword;
    @BindView(R.id.til_stu_number)
    TextInputLayout mTilStuNumber;

    public RegisterFragment() {
        // Requires the empty constructor
    }

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_register)
    public void onViewClicked() {
        final String telephone = mPhoneNumber.getText().toString().trim();
        final String password = mUserPassword.getText().toString().trim();
        final String name = mUserName.getText().toString().trim();
        final String num = mStuNumber.getText().toString().trim();

        if (!TextUtils.isEmpty(telephone)) {
            mTilPhoneNumber.setErrorEnabled(false);
            mTilPhoneNumber.setError("");
        } else if (!TextUtils.isEmpty(password)) {
            mTilPassword.setErrorEnabled(false);
            mTilPassword.setError("");
        } else if (!TextUtils.isEmpty(name)) {
            mTilName.setErrorEnabled(false);
            mTilName.setError("");
        }

        if (!TextUtils.isEmpty(telephone) & !TextUtils.isEmpty(password) & !TextUtils.isEmpty(name)) {
            mTilPhoneNumber.setErrorEnabled(false);
            mTilPassword.setErrorEnabled(false);
            mTilName.setErrorEnabled(false);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(RetrofitService.URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final RetrofitService.UserService service = retrofit.create(RetrofitService.UserService.class);

            Call<User> call = service.register(telephone, name, password, num);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    L.d("user_register", response.message() + " " + response.body());
                    Toast.makeText(getContext(), "注册成功", Toast.LENGTH_SHORT).show();

                    //保留手机号，便于查询用户信息
                    ShareUtils.putString(getContext(), StaticClass.KEY_USER_TELEPHONE, telephone);
                    //保存用户信息
                    User user = new User();
                    user.setTelephone(telephone);
                    user.setName(name);
                    user.setPassword(password);
                    user.setNumber(num);
                    user.saveAsync().listen(new SaveCallback() {
                        @Override
                        public void onFinish(boolean success) {
                            L.d(LOG_TAG, "user save " + success);
                        }
                    });

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    L.d("user_register_error", t.getMessage());
                    L.d("user_register_error", t.toString());
                }
            });
        } else if (TextUtils.isEmpty(telephone)) {
            mTilPhoneNumber.setError("手机号不能为空");
        } else if (TextUtils.isEmpty(password)) {
            mTilPassword.setError("密码不能为空");
        } else if (TextUtils.isEmpty(name)) {
            mTilName.setError("姓名不能为空");
        }


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    //调用SDK注册
//                    EMClient.getInstance().createAccount(num, password);
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            //储存用户信息
//                            ShareUtils.putString(getContext(), StaticClass.KEY_USER_NUM, num);
//                            ShareUtils.putString(getContext(), StaticClass.KEY_USER_NAME, name);
//                            Toast.makeText(getContext(), "注册成功", Toast.LENGTH_SHORT).show();
//
//                            Intent intent = new Intent(getActivity(), MainActivity.class);
//                            startActivity(intent);
//                            getActivity().finish();
//                        }
//                    });
//                } catch (HyphenateException e) {
//                    e.printStackTrace();
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getContext(), "注册失败", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }
//        }).start();
    }
}
