//package com.app.debrove.tinpandog.location;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.app.debrove.tinpandog.R;
//import com.baidu.location.LocationClient;
//import com.baidu.location.LocationClientOption;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by NameTooLong on 2017/11/19.
// *
// * 1.使用百度地图API实现的定位功能，简单获取经纬度、所在地点等信息
// * 2.无法在虚拟机上测试
// * 3.根据打包所使用的密钥不同，AndroidManifest.xml中填入的value值也会不同
// */
//
//public class BdLocationActivity extends Activity implements View.OnClickListener{
//    private static final String TAG = "BdLocationActivity";
//    private TextView mTextView;
//    private Button mButton_requestLocation;
//
//    private LocationClient mLocationClient;
//    private MyLocationListener myLoactionListener;
//    private List<String> mPermissionList=new ArrayList<>();
//
//    private static  final int ASK_PERMISSION=1;
//    private static final int OPEN_GPS=2;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mLocationClient = new LocationClient(getApplicationContext());
//        myLoactionListener = new MyLocationListener();
//        mLocationClient.registerLocationListener(myLoactionListener);
//        LocationClientOption option=new LocationClientOption();
//        option.setScanSpan(5000);
//        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
//        option.setIsNeedAddress(true);
//        option.setOpenGps(true);
//        option.setCoorType("bd09ll");
//        mLocationClient.setLocOption(option);
//
//        setContentView(R.layout.activity_bd_location);
//
//        mTextView=(TextView)findViewById(R.id.bdLocation_activity_textView);
//        mButton_requestLocation=(Button)findViewById(R.id.bdLocation_activity_button);
//        mButton_requestLocation.setOnClickListener(this);
//
//        if(!isGpsOpen())
//            new AlertDialog.Builder(this).
//                    setTitle("权限需要").
//                    setMessage("定位功能需要开启GPS").
//                    setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            BdLocationActivity.this.finish();
//                        }
//                    }).
//                    setPositiveButton("同意", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                            startActivityForResult(intent,OPEN_GPS);
//                        }
//                    }).
//                    setCancelable(false).show();
//
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode){
//            case OPEN_GPS:
//                if(!isGpsOpen())
//                    Toast.makeText(this,"这将可能导致应用无法正常工作。",Toast.LENGTH_SHORT).show();
//                break;
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.bdLocation_activity_button:
//                checkPermission(Manifest.permission.ACCESS_FINE_LOCATION);
//                checkPermission(Manifest.permission.READ_PHONE_STATE);
//                checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//                checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
//                if (mPermissionList!=null&&!mPermissionList.isEmpty()) {
//                    String permissions[] = mPermissionList.toArray(new String[mPermissionList.size()]);
//                    ActivityCompat.requestPermissions(this, permissions, ASK_PERMISSION);
//                    mPermissionList=null;
//                }
//                else
//                    requestLocation();
//                break;
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch (requestCode){
//            case ASK_PERMISSION:
//                if(grantResults.length<=0)
//                    return;
//                for(int result:grantResults)
//                    if(result!=PackageManager.PERMISSION_GRANTED){
//                        Toast.makeText(this,"权限拒绝，无法使用！",Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                requestLocation();
//                break;
//        }
//    }
//
//    private boolean isGpsOpen(){
//        LocationManager locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mLocationClient.unRegisterLocationListener(myLoactionListener);
//        mLocationClient.stop();
//    }
//
//    private void requestLocation(){
//        Log.e(TAG,"requestLocation");
//        mLocationClient.start();
//        // mLocationClient.requestLocation();
//    }
//
//    private void checkPermission(String permission){
//        if(ContextCompat.checkSelfPermission(this,permission)!= PackageManager.PERMISSION_GRANTED)
//            mPermissionList.add(permission);
//    }
//
//    private class MyLocationListener extends BDAbstractLocationListener{
//        StringBuilder currentPosition;
//
//        @Override
//        public void onReceiveLocation(BDLocation bdLocation) {
//            Log.e(TAG,"onReceiveLocation");
//            currentPosition=new StringBuilder();
//            appendToString("维度："+bdLocation.getLatitude());
//            appendToString("经度："+bdLocation.getLongitude());
//            appendToString("省份："+bdLocation.getProvince());
//            appendToString("城市："+bdLocation.getCity());
//            appendToString("区  ："+bdLocation.getDistrict());
//            appendToString("街道："+bdLocation.getStreet());
//            currentPosition.append("定位方式：");
//            switch (bdLocation.getLocType()){
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
//            mTextView.setText(currentPosition);
//            currentPosition=null;
//        }
//
//        private void appendToString(String information){
//            currentPosition.append(information).append("\n");
//        }
//    }
//}
//
