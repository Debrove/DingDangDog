package com.app.debrove.tinpandog.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.app.debrove.tinpandog.util.L;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by debrove on 2017/10/14.
 * Package Name : com.app.debrove.tinpandog.login
 */

public class LoginFragment extends Fragment {

    private final static String LOG_TAG = LoginFragment.class.getSimpleName();

    @BindView(R.id.user_number)
    EditText mUserNumber;
    @BindView(R.id.user_password)
    EditText mUserPassword;
    Unbinder unbinder;

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
        String num = mUserNumber.getText().toString().trim();
        String password = mUserPassword.getText().toString().trim();

        if (!TextUtils.isEmpty(num) & !TextUtils.isEmpty(password)) {
            //处理登录事件
            EMClient.getInstance().login(num, password, new EMCallBack() {
                @Override
                public void onSuccess() {
                    EMClient.getInstance().groupManager().loadAllGroups();

                    L.d(LOG_TAG, "登陆成功");
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                        }
                    });

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }

                @Override
                public void onError(int i, String s) {
                    L.d(LOG_TAG, "登陆失败");
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "登陆失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onProgress(int i, String s) {

                }
            });
        }
    }
}
