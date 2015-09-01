package com.saiwei.cardemo;

import android.app.ActionBar;
import android.content.Intent;
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

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.SupportMapFragment;
import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;

public class MainActivity extends FragmentActivity {


    private final String TAG = "chenwei.MainActivity";

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private BaiduMap mBaiduMap = null;
    SupportMapFragment map;

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
    }

    /**
     *　初始化地图
     */
    private void initMap(){
        MapStatus ms = new MapStatus.Builder().overlook(-20).zoom(15).build();
        BaiduMapOptions bo = new BaiduMapOptions().mapStatus(ms)
                .compassEnabled(false).zoomControlsEnabled(false);
        map = SupportMapFragment.newInstance(bo);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.map, map, "map_fragment").commit();
        mBaiduMap = map.getBaiduMap();

        //        mBaiduMap = ((SupportMapFragment) (getSupportFragmentManager()
//                .findFragmentById(R.id.map))).getBaiduMap();
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

                Log.i(TAG, " onItemClick()  position = "+position);

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
     * 操作界面
     * @param view
     */
    public void operaDrawer(View view){

        Log.i(TAG,"operaDrawer()");

        if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            mDrawerLayout.openDrawer(mDrawerList);
        }
    }
}
