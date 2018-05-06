package com.app.debrove.tinpandog.signup;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.app.debrove.tinpandog.R;
import com.app.debrove.tinpandog.data.ContentType;
import com.app.debrove.tinpandog.util.L;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cp4yin on 2017/12/18.
 * <p>
 * 签到
 */

public class SignInActivity extends AppCompatActivity {

    public static final String KEY_ARTICLE_TYPE = "KEY_ARTICLE_TYPE";
    public static final String KEY_ARTICLE_ID = "KEY_ARTICLE_ID";

    private SignInFragment mSignInFragment;

    private ContentType mType;

    private static final String LOG_TAG = "SignInActivity";


    private LocationClient mLocationClient;
//    private MyLocationListener myLoactionListener;
    private List<String> mPermissionList = new ArrayList<>();

    private static final int ASK_PERMISSION = 1;
    private static final int OPEN_GPS = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mLocationClient = new LocationClient(getApplicationContext());
//        myLoactionListener = new MyLocationListener();
//        mLocationClient.registerLocationListener(myLoactionListener);
//        LocationClientOption option = new LocationClientOption();
//        option.setScanSpan(5000);
//        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
//        option.setIsNeedAddress(true);
//        option.setOpenGps(true);
//        option.setCoorType("bd09ll");
//        mLocationClient.setLocOption(option);

        setContentView(R.layout.frame);

        if (savedInstanceState != null) {
            mSignInFragment = (SignInFragment) getSupportFragmentManager().getFragment(savedInstanceState, SignInFragment.class.getSimpleName());
        } else {
            mSignInFragment = SignInFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mSignInFragment, SignInFragment.class.getSimpleName())
                    .commit();
        }

        mType = (ContentType) getIntent().getSerializableExtra(KEY_ARTICLE_TYPE);
        if (mType == ContentType.TYPE_ACTIVITIES) {

        } else if (mType == ContentType.TYPE_LECTURES) {

        }

        if(!isGpsOpen())
            new AlertDialog.Builder(this).
                    setTitle("权限需要").
                    setMessage("定位功能需要开启GPS").
                    setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SignInActivity.this.finish();
                        }
                    }).
                    setPositiveButton("同意", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(intent,OPEN_GPS);
                        }
                    }).
                    setCancelable(false).show();
    }



//    private class MyLocationListener extends BDAbstractLocationListener {
//        StringBuilder currentPosition;
//
//        @Override
//        public void onReceiveLocation(BDLocation bdLocation) {
//            L.e(LOG_TAG, "onReceiveLocation");
//            currentPosition = new StringBuilder();
//            appendToString("维度：" + bdLocation.getLatitude());
//            appendToString("经度：" + bdLocation.getLongitude());
//            appendToString("省份：" + bdLocation.getProvince());
//            appendToString("城市：" + bdLocation.getCity());
//            appendToString("区  ：" + bdLocation.getDistrict());
//            appendToString("街道：" + bdLocation.getStreet());
//            currentPosition.append("定位方式：");
//            switch (bdLocation.getLocType()) {
//                case BDLocation.TypeGpsLocation:
//                    currentPosition.append("GPS");
//                    break;
//                case BDLocation.TypeNetWorkLocation:
//                    currentPosition.append("网络");
//                    break;
//                default:
//                    currentPosition.append("未知");
//                    break;
//            }
//            //mTextView.setText(currentPosition);
//            L.d(LOG_TAG, "location " + currentPosition);
//            currentPosition = null;
//        }
//
//        private void appendToString(String information) {
//            currentPosition.append(information).append("\n");
//        }
//    }

    private boolean isGpsOpen(){
        LocationManager locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

//    private void requestLocation(){
//        L.e(LOG_TAG,"requestLocation");
//        mLocationClient.start();
//        // mLocationClient.requestLocation();
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case ASK_PERMISSION:
                if(grantResults.length<=0)
                    return;
                for(int result:grantResults)
                    if(result!= PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this,"权限拒绝，无法使用！",Toast.LENGTH_SHORT).show();
                        return;
                    }
//                requestLocation();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case OPEN_GPS:
                if(!isGpsOpen())
                    Toast.makeText(this,"这将可能导致应用无法正常工作。",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mSignInFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, SignInFragment.class.getSimpleName(), mSignInFragment);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
