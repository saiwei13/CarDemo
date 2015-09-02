package com.saiwei.cardemo;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;

public class MainActivity extends FragmentActivity {

    private final String TAG = "chenwei.MainActivity";

    private LocationClient mLocationClient;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private BaiduMap mBaiduMap = null;
    SupportMapFragment map;

    private LocationClientOption.LocationMode tempMode = LocationClientOption.LocationMode.Hight_Accuracy;
    private String tempcoor="gcj02";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView(){

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        initMap();
        initList();
        initLocation();
    }

    /**
     *　初始化地图
     */
    private void initMap(){

        Log.i(TAG, "initMap()");

//        MapStatus ms = new MapStatus.Builder().overlook(-20).zoom(15).build();
//        BaiduMapOptions bo = new BaiduMapOptions().mapStatus(ms)
//                .compassEnabled(false).zoomControlsEnabled(false);
//        map = SupportMapFragment.newInstance(bo);
//        FragmentManager manager = getSupportFragmentManager();
//        manager.beginTransaction().add(R.id.map, map, "map_fragment").commit();
//        mBaiduMap = map.getBaiduMap();

        mBaiduMap = ((SupportMapFragment) (getSupportFragmentManager()
                .findFragmentById(R.id.map))).getBaiduMap();

        Log.i(TAG, "(mBaiduMap==null) = " + (mBaiduMap == null));
    }

    /**
     * 初始化　list
     */
    private void initList(){

        mDrawerList = (ListView) findViewById(R.id.navdrawer);

        String[] values = new String[]{
                "Stop Animation (Back icon)",
                "Stop Animation (Home icon)",
                "Start Animation",
                "Change Color",
                "GitHub Page",
                "Share",
                "Rate"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Log.i(TAG, " onItemClick()  position = " + position);

                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                }
            }
        });
    }

    /**
     * 初始化定位
     */
    private void initLocation(){

        mLocationClient = ((DemoApplication)getApplication()).mLocationClient;

        LocationClientOption option = new LocationClientOption();
//        option.setLocationMode(tempMode);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
//        option.setCoorType(tempcoor);//可选，默认gcj02，设置返回的定位结果坐标系，
//        int span=1000;
//        try {
//            span = Integer.valueOf(frequence.getText().toString());
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
//        option.setIsNeedAddress(checkGeoLocation.isChecked());//可选，设置是否需要地址信息，默认不需要
//        option.setOpenGps(true);//可选，默认false,设置是否使用gps
//        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
//        option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
//        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
//        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
//        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mLocationClient.setLocOption(option);
    }

    /**
     * 开始定位
     * @param v
     */
    public void startLoc(View v){
        Log.i(TAG, "startLoc()");

        if(mLocationClient != null && !mLocationClient.isStarted()){
            mLocationClient.start();
        } else {
            mLocationClient.requestLocation();
        }
    }

    /**
     * 停止定位
     */
    private void stopLoc(){
        Log.i(TAG,"stopLoc()");

        if(mLocationClient != null && mLocationClient.isStarted()){
            mLocationClient.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopLoc();

    }

    /**
     * 操作界面
     * @param view
     */
    public void operaDrawer(View view){

        Log.i(TAG,"operaDrawer()");

        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(new LatLng(39.304486, 116.401444));
        mBaiduMap.setMapStatus(msu);

//        LatLng mMapCenter = mBaiduMap.getMapStatus().target;
//        Point tmp = mBaiduMap.getMapStatus().targetScreen;
//        Log.i(TAG, mMapCenter.latitude + ", " + mMapCenter.longitude);
//        Log.i(TAG,"screen  point =  "+tmp.toString());

//        if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
//            mDrawerLayout.closeDrawer(mDrawerList);
//        } else {
//            mDrawerLayout.openDrawer(mDrawerList);
//        }
    }
}
