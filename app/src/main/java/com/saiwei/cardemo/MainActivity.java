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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;

import java.util.List;

public class MainActivity extends FragmentActivity {

    private final String TAG = "chenwei.MainActivity";

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private BaiduMap mBaiduMap = null;
    private SupportMapFragment map;

    private LocationClientOption.LocationMode tempMode = LocationClientOption.LocationMode.Hight_Accuracy;
    private String tempcoor="gcj02";

    public LocationClient mLocationClient;
    public MyLocationListener mMyLocationListener;

    private Marker mMarkerA;

    // 初始化全局 bitmap 信息，不用时及时 recycle
    BitmapDescriptor bdA = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_marka);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    static int i = 0;

    private void initView(){

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        initMap();
        initList();
        initLocation();
        initMarker();



        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            public void onMapStatusChangeStart(MapStatus status) {
                Log.i(TAG, "onMapStatusChangeStart()   ");
            }

            public void onMapStatusChangeFinish(MapStatus status) {
                Log.i(TAG, "onMapStatusChangeFinish()   " );

                mMapCenter = status.target;
                mMarkerA.setPosition(mMapCenter);
            }

            public void onMapStatusChange(MapStatus status) {
//                Log.i(TAG, "onMapStatusChange()   " + status.toString());
                Log.i(TAG, "onMapStatusChange()   i="+i );
//                updateMapState();

//                mMapCenter = status.target;
//                mMarkerA.setPosition(mMapCenter);

                i++;

                if(i>=10){
                    Log.i(TAG,"i="+i);
                    i=0;
                    mMapCenter = status.target;
                    mMarkerA.setPosition(mMapCenter);

                }
            }
        });

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

        mLocationClient = new LocationClient(this);
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);

        LocationClientOption option = new LocationClientOption();
        mLocationClient.setLocOption(option);

        startLoc();
    }

    LatLng mMapCenter = null;

    private void initMarker(){
        mMapCenter = new LatLng(28.169348, 118.194487);

        OverlayOptions ooA = new MarkerOptions().position(mMapCenter).icon(bdA)
                .zIndex(9);
        mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            public boolean onMarkerClick(final Marker marker) {

                if(marker == mMarkerA){
                    Toast.makeText(getApplicationContext(),"A　被点击",Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    /**
     * 开始定位
     * @param v
     */
    public void startLoc(View v){
        Log.i(TAG, "startLoc()");

        startLoc();
    }

    private void startLoc(){
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

        Log.i(TAG, "operaDrawer()");

//        float zoom = mBaiduMap.getMapStatus().zoom;
//        Toast.makeText(this,"zoom = "+zoom,Toast.LENGTH_SHORT).show();

//        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(new LatLng(39.304486, 116.401444));
//        mBaiduMap.setMapStatus(msu);

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

    LatLng mCurLoc=null;

    /**
     * 实现实时位置回调监听
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
//            location.getLatitude()
//            location.getLongitude()

            Log.i(TAG, "onReceiveLocation()  location.getLatitude()=" + location.getLatitude() + " , location.getLongitude()=" + location.getLongitude());

            if(location == null) return;

            mCurLoc = new LatLng(
                    location.getLatitude(),
                    location.getLongitude());

            MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(mCurLoc);
            mBaiduMap.setMapStatus(msu);
            mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(16.0f));


            mMapCenter = mCurLoc;
            mMarkerA.setPosition(mMapCenter);

//            （mBaiduMap.getMapStatus().zoom;MapStatusUpdateFactory.zoomBy(float f))
        }
    }
}
