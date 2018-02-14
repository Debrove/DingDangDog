package com.app.debrove.tinpandog.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.debrove.tinpandog.R;

import java.lang.reflect.Field;

/**
 * Created by cp4yin on 2018/1/24.
 */

public class CustomEditText extends LinearLayout implements TextWatcher, View.OnKeyListener, View.OnFocusChangeListener{

    private Context mContext;
    private long endTime = 0;
    private OnNumFinishListener onNumFinishListener;

    private int mEtNumber;//输入框数量
    private int mEtWidth;//宽
    private int mEtTextColor;//颜色
    private float mEtTextSize;//文字大小
    private int mEtTextBg;//背景
    private int mCursorDrawable;//光标


    public OnNumFinishListener getOnNumFinishListener(){
        return onNumFinishListener;
    }

    public void setOnNumFinishListener(OnNumFinishListener onNumFinishListener) {
        this.onNumFinishListener = onNumFinishListener;
    }

    public int getmEtNumber() {
        return mEtNumber;
    }

    public void setmEtNumber(int mEtNumber) {
        this.mEtNumber = mEtNumber;
    }

    public int getmEtWidth() {
        return mEtWidth;
    }

    public void setmEtWidth(int mEtWidth) {
        this.mEtWidth = mEtWidth;
    }

    public int getmEtTextColor() {
        return mEtTextColor;
    }

    public void setmEtTextColor(int mEtTextColor) {
        this.mEtTextColor = mEtTextColor;
    }

    public float getmEtTextSize() {
        return mEtTextSize;
    }

    public void setmEtTextSize(float mEtTextSize) {
        this.mEtTextSize = mEtTextSize;
    }

    public int getmEtTextBg() {
        return mEtTextBg;
    }

    public void setmEtTextBg(int mEtTextBg) {
        this.mEtTextBg = mEtTextBg;
    }

    public int getmCursorDrawable() {
        return mCursorDrawable;
    }

    public void setmCursorDrawable(int mCursorDrawable) {
        this.mCursorDrawable = mCursorDrawable;
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        //@SuppressLint({"Recycle", "CustomViewStyleable"})
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.customEditText);
        mEtNumber = typedArray.getInteger(R.styleable.customEditText_et_number, 4);
        //int inputType = typedArray.getInt(R.styleable.vericationCodeView_vcv_et_inputType, VCInputType.NUMBER.ordinal());
        //mEtInputType = VCInputType.values()[inputType];
        mEtWidth = typedArray.getDimensionPixelSize(R.styleable.customEditText_et_width, 120);
        mEtTextColor = typedArray.getColor(R.styleable.customEditText_et_text_color, Color.BLACK);
        mEtTextSize = typedArray.getDimensionPixelSize(R.styleable.customEditText_et_text_size, 16);
        mEtTextBg = typedArray.getResourceId(R.styleable.customEditText_et_bg, R.drawable.et_login_code);
        mCursorDrawable = typedArray.getResourceId(R.styleable.customEditText_et_cursor, R.drawable.et_cursor);

        //释放资源
        typedArray.recycle();
        initView();
    }

    @SuppressLint("ResourceAsColor")
    private void initView() {
        for (int i = 0; i < mEtNumber; i++) {
            EditText editText = new EditText(mContext);
            initEditText(editText, i);
            addView(editText);
            if (i == 0) { //设置第一个editText获取焦点
                editText.setFocusable(true);
            }
        }
    }

    private void initEditText(EditText editText, int i) {
        int childHPadding = 14;
        int childVPadding = 14;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mEtWidth, mEtWidth);
        layoutParams.bottomMargin = childVPadding;
        layoutParams.topMargin = childVPadding;
        layoutParams.leftMargin = childHPadding;
        layoutParams.rightMargin = childHPadding;
        layoutParams.gravity = Gravity.CENTER;
        editText.setLayoutParams(layoutParams);
        editText.setGravity(Gravity.CENTER);
        editText.setId(i);
        editText.setCursorVisible(true);
        editText.setMaxEms(1);
        editText.setTextColor(mEtTextColor);
        editText.setTextSize(mEtTextSize);
        editText.setMaxLines(1);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
        editText.setPadding(0, 0, 0, 0);
        editText.setOnKeyListener(this);
        editText.setBackgroundResource(mEtTextBg);

        //修改光标的颜色（反射）
        try {
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(editText, mCursorDrawable);
        } catch (Exception ignored) {
        }
        editText.addTextChangedListener(this);
        editText.setOnFocusChangeListener(this);
        editText.setOnKeyListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() != 0) {
            focus();
        }
    }



    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            focus();
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.setEnabled(enabled);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            backFocus();
        }
        return false;
    }

    private void focus() {
        int count = getChildCount();
        EditText editText;
        //利用for循环找出还最前面那个还没被输入字符的EditText，并把焦点移交给它。
        for (int i = 0; i < count; i++) {
            editText = (EditText) getChildAt(i);
            if (editText.getText().length() < 1) {
                editText.setCursorVisible(true);
                editText.requestFocus();
                return;
            } else {
                editText.setCursorVisible(false);
            }
        }
        //如果最后一个输入框有字符，则返回结果
        EditText lastEditText = (EditText) getChildAt(mEtNumber - 1);
        if (lastEditText.getText().length() > 0) {
            getResult();
        }
    }

    private void backFocus() {
        //设置了一个防止多点击，间隔100毫秒。
        long startTime = System.currentTimeMillis();
        EditText editText;
        //循环检测有字符的`editText`，把其置空，并获取焦点。
        for (int i = mEtNumber - 1; i >= 0; i--) {
            editText = (EditText) getChildAt(i);
            if (editText.getText().length() >= 1 && startTime - endTime > 100) {
                editText.setText("");
                editText.setCursorVisible(true);
                editText.requestFocus();
                endTime = startTime;
                return;
            }
        }
    }

    private void getResult() {
        StringBuffer stringBuffer = new StringBuffer();
        EditText editText;
        for (int i = 0; i < mEtNumber; i++) {
            editText = (EditText) getChildAt(i);
            stringBuffer.append(editText.getText());
        }
        if (onNumFinishListener != null) {
            onNumFinishListener.onComplete(stringBuffer.toString());
        }
    }


    public interface OnNumFinishListener {
        void onComplete(String content);
    }
}
