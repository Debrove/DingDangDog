package com.app.debrove.tinpandog.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.app.debrove.tinpandog.MainActivity;
import com.app.debrove.tinpandog.R;
import com.app.debrove.tinpandog.util.ShareUtils;
import com.app.debrove.tinpandog.util.StaticClass;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by debrove on 2017/10/14.
 * Package Name : com.app.debrove.tinpandog.login
 */

public class RegisterFragment extends Fragment {
    @BindView(R.id.user_number)
    EditText mUserNumber;
    @BindView(R.id.user_name)
    EditText mUserName;
    @BindView(R.id.user_password)
    EditText mUserPassword;
    Unbinder unbinder;

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
        final String num = mUserNumber.getText().toString().trim();
        final String password = mUserPassword.getText().toString().trim();
        final String name = mUserName.getText().toString().trim();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //调用SDK注册
                    EMClient.getInstance().createAccount(num, password);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //储存用户信息
                            ShareUtils.putString(getContext(), StaticClass.KEY_USER_NUM, num);
                            ShareUtils.putString(getContext(), StaticClass.KEY_USER_NAME, name);
                            Toast.makeText(getContext(), "注册成功", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "注册失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }
}
