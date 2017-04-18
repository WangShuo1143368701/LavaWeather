package com.lava.weather.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import static android.R.attr.offset;

/**
 * Created by Shuo.Wang on 2016/12/14.
 */
public class LavaWeatherActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,ViewPager.OnPageChangeListener{

    private DrawerLayout mDrawerLayout;
    private ViewPager mViewPager;
    private NavigationView mNavigationView;
    private FragmentPagerAdapter mFragmentPagerAdapter;
    private List<Fragment> mDatas;
    private List<String> mLocationList;

    private TextView barCityName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_lava_weather);

        initView();
        initData();

    }

    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mViewPager= (ViewPager) findViewById(R.id.mViewPager);
        mViewPager.addOnPageChangeListener(this);

        barCityName= (TextView) findViewById(R.id.bar_city_name);
    }

    private void initData() {
        // 实际上数据库中获取有几个位置信息 模拟start
        mLocationList=new ArrayList<String>();
        mLocationList.add("深圳");
        mLocationList.add("北京");
        mLocationList.add("天津");
        // 数据库中获取有几个位置信息 模拟end
        mDatas=new ArrayList<Fragment>();
        for(String mLocation:mLocationList){
            LavaWeatherFragment mFragment = LavaWeatherFragment.newInstance(mLocation);
            mDatas.add(mFragment);
        }

        mFragmentPagerAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {

                return mDatas.size();
            }

            @Override
            public Fragment getItem(int location) {

                return mDatas.get(location);
            }
        };
        mViewPager.setAdapter(mFragmentPagerAdapter);


    }



    @Override
    public void onBackPressed() {
        if (mDrawerLayout!=null && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lava_weather, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.e("wangshuo","onPageScrolled--->"+position+" offset= "+offset+" offsetPixels ="+positionOffsetPixels);
        if(barCityName!=null &&mLocationList!=null && mLocationList.size()>0) {
            barCityName.setText(/*mLocationList.get(position)+" "+*/(position+1)+"/"+mLocationList.size());
        }
    }

    @Override
    public void onPageSelected(int position) {
        Log.e("wangshuo","onPageSelected ===="+position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
