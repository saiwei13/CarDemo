package com.saiwei.cardemo;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements
        OnGetSuggestionResultListener,
        BaiduMap.OnMapStatusChangeListener,
        OnGetPoiSearchResultListener{

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

    private PoiSearch mPoiSearch = null;
    private SuggestionSearch mSuggestionSearch = null;
    /**
     * 搜索关键字输入窗口
     */
    private AutoCompleteTextView keyWorldsView = null;
    private ArrayAdapter<String> sugAdapter = null;

    /**
     * 当前位置
     */
    private LatLng mCurLoc=null;
    /**
     * 当前城市
     */
    private String mCurCity = "";

    private PoiOverlay overlay;

    private static final String APP_FOLDER_NAME = "BNSDKDemo_saiwei";
    private String mSDCardPath = null;
    public static final String ROUTE_PLAN_NODE = "routePlanNode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private static int i = 0;

    private void initView(){

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        initMap();
        initList();
        initLocation();
        initMarker();

        mBaiduMap.setOnMapStatusChangeListener(this);

        initSearch();
        initBaiduNavi();
    }

    /**
     * 初始化百度导航
     */
    private void initBaiduNavi(){
        if ( initDirs() ) {
            initNavi();
        }
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
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);

        startLoc();
    }

    LatLng mMapCenter = null;

    private void initMarker(){
        mMapCenter = new LatLng(28.169348, 118.194487);

//        OverlayOptions ooA = new MarkerOptions().position(mMapCenter).icon(bdA)
//                .zIndex(9);
//        mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
//
//        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
//            public boolean onMarkerClick(final Marker marker) {
//
//                if(marker == mMarkerA){
//                    Toast.makeText(getApplicationContext(),"A　被点击",Toast.LENGTH_SHORT).show();
//                }
//                return true;
//            }
//        });
    }

    /**
     * 初始化搜索
     */
    private void initSearch(){

        overlay = new MyPoiOverlay(mBaiduMap);
        mBaiduMap.setOnMarkerClickListener(overlay);

        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);
        keyWorldsView = (AutoCompleteTextView) findViewById(R.id.searchkey);
        sugAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line);
        keyWorldsView.setAdapter(sugAdapter);

        /**
         * 当输入关键字变化时，动态更新建议列表
         */
        keyWorldsView.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

            }

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2,
                                      int arg3) {
                if (cs.length() <= 0 || mCurCity == null) {
                    return;
                }
//                String city = ((EditText) findViewById(R.id.city)).getText()
//                        .toString();
                /**
                 * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
                 */
                mSuggestionSearch
                        .requestSuggestion((new SuggestionSearchOption())
                                .keyword(cs.toString()).location(mMapCenter).city(mCurCity)); //.city(city)
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

        routeplanToNavi(BNRoutePlanNode.CoordinateType.WGS84);

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


    /**
     * 影响搜索按钮点击事件
     *
     * @param v
     */
    public void searchButtonProcess(View v) {
        EditText editSearchKey = (EditText) findViewById(R.id.searchkey);
        mPoiSearch.searchInCity((new PoiCitySearchOption())
                .city(mCurCity)
                .keyword(editSearchKey.getText().toString())
                .pageNum(0));
    }


    //---------------------------------------------------------------------
    //---------------------------------------------------------------------

    @Override
    public void onGetSuggestionResult(SuggestionResult res) {

//        Log.i(TAG,"onGetSuggestionResult() "+suggestionResult.toString());

        if (res == null || res.getAllSuggestions() == null) {
            return;
        }
        sugAdapter.clear();
        for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
            if (info.key != null){

                Log.i(TAG,"key = "+info.key+" , "+info.pt);
                sugAdapter.add(info.key);
            }
        }
        sugAdapter.notifyDataSetChanged();
    }

    //---------------地图状态变化---------------------------------------

    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChange(MapStatus mapStatus) {
        i++;
        if (i >= 10) {
            Log.i(TAG, "i=" + i);
            i = 0;
            mMapCenter = mapStatus.target;
//            mMarkerA.setPosition(mMapCenter);

        }
    }

    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {
        mMapCenter = mapStatus.target;
//        mMarkerA.setPosition(mMapCenter);
    }

    //-------------poi search callback------------------------------------

    @Override
    public void onGetPoiResult(PoiResult result) {
        Log.i(TAG,"onGetPoiResult() num="+result.getCurrentPageNum());

        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
//            mBaiduMap.clear();

            overlay.setData(result);
            overlay.addToMap();
            overlay.zoomToSpan();
            return;
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult result) {
        Log.i(TAG, "onGetPoiDetailResult() " + result.getAddress()+" , "+result.getLocation());
    }

    /**
     * 实现实时位置回调监听
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            if(location == null) return;

            Log.i(TAG, "onReceiveLocation()  location.getLatitude()=" + location.getLatitude() + " , location.getLongitude()=" + location.getLongitude());
//            Log.i(TAG,"city = "+location.getCity()+" , citycode= "+location.getCityCode());

            mCurCity = location.getCity();

            mCurLoc = new LatLng(
                    location.getLatitude(),
                    location.getLongitude());

            MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(mCurLoc);
            mBaiduMap.setMapStatus(msu);
            mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(16.0f));

            mMapCenter = mCurLoc;
//            mMarkerA.setPosition(mMapCenter);

//            （mBaiduMap.getMapStatus().zoom;MapStatusUpdateFactory.zoomBy(float f))
        }
    }

    private class MyPoiOverlay extends PoiOverlay {

        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            PoiInfo poi = getPoiResult().getAllPoi().get(index);
            // if (poi.hasCaterDetails) {
            mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
                    .poiUid(poi.uid));
            // }
            return true;
        }
    }

    //---------------------导航模块-------------------------

    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if ( mSDCardPath == null ) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if ( !f.exists() ) {
            try {
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(
                Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    String authinfo = null;

    private void initNavi() {

        Log.i(TAG,"initNavi()  mSDCardPath="+mSDCardPath+" , APP_FOLDER_NAME="+APP_FOLDER_NAME);

        BaiduNaviManager.getInstance().setNativeLibraryPath(mSDCardPath + "/BaiduNaviSDK_SO");
        BaiduNaviManager.getInstance().init(this, mSDCardPath, APP_FOLDER_NAME,
                new BaiduNaviManager.NaviInitListener() {
                    @Override
                    public void onAuthResult(int status, String msg) {
                        if (0 == status) {
                            authinfo = "key校验成功!";
                        } else {
                            authinfo = "key校验失败, " + msg;
                        }
                        MainActivity.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, authinfo, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    public void initSuccess() {
                        Toast.makeText(MainActivity.this, "百度导航引擎初始化成功", Toast.LENGTH_SHORT).show();
                    }

                    public void initStart() {
                        Toast.makeText(MainActivity.this, "百度导航引擎初始化开始", Toast.LENGTH_SHORT).show();
                    }

                    public void initFailed() {
                        Toast.makeText(MainActivity.this, "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
                    }
                }, null /*mTTSCallback*/);
    }



    private void routeplanToNavi(BNRoutePlanNode.CoordinateType coType) {
        BNRoutePlanNode sNode = null;
        BNRoutePlanNode eNode = null;
        switch(coType) {
            case GCJ02: {

                sNode = new BNRoutePlanNode(118.097483, 24.435572,
                        "厦门起点", null, coType);
                eNode = new BNRoutePlanNode(118.181295,24.491452,
                        "厦门终点", null, coType);

//				sNode = new BNRoutePlanNode(116.30142, 40.05087,
//			    		"百度大厦", null, coType);
//				eNode = new BNRoutePlanNode(116.39750, 39.90882,
//			    		"北京天安门", null, coType);
                break;
            }
            case WGS84: {

                sNode = new BNRoutePlanNode(118.097483, 24.435572,
                        "厦门起点", null, coType);
                eNode = new BNRoutePlanNode(118.181295,24.491452,
                        "厦门终点", null, coType);

//				sNode = new BNRoutePlanNode(116.300821,40.050969,
//			    		"百度大厦", null, coType);
//				eNode = new BNRoutePlanNode(116.397491,39.908749,
//			    		"北京天安门", null, coType);
                break;
            }
            case BD09_MC: {
                sNode = new BNRoutePlanNode(12947471,4846474,
                        "百度大厦", null, coType);
                eNode = new BNRoutePlanNode(12958160,4825947,
                        "北京天安门", null, coType);
                break;
            }
            default : ;
        }
        if (sNode != null && eNode != null) {
            List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
            list.add(sNode);
            list.add(eNode);
            BaiduNaviManager.getInstance().launchNavigator(this, list, 1, true, new DemoRoutePlanListener(sNode));
        }
    }

    public class DemoRoutePlanListener implements BaiduNaviManager.RoutePlanListener {

        private BNRoutePlanNode mBNRoutePlanNode = null;
        public DemoRoutePlanListener(BNRoutePlanNode node){
            mBNRoutePlanNode = node;
        }

        @Override
        public void onJumpToNavigator() {
            Intent intent = new Intent(MainActivity.this, BNDemoGuideActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ROUTE_PLAN_NODE, (BNRoutePlanNode) mBNRoutePlanNode);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        @Override
        public void onRoutePlanFailed() {
            // TODO Auto-generated method stub

        }
    }
}
