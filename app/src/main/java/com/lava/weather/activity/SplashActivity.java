package com.lava.weather.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.lava.weather.service.LocationAndWeatherService;
import com.lava.weather.util.SPUtils;


/**
 * Created by Shuo.Wang on 2016/12/14.
 */
public class SplashActivity extends BaseActivity {

    public String[] sRequiredPermissions = new String[] {
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(!checkPermissions(sRequiredPermissions)){
            requestPermission(sRequiredPermissions,0x001);
            return;
        }else{
            startActivity();
        }

    }


    @Override
    public void permissionSuccess(int requestCode) {
        super.permissionSuccess(requestCode);
       switch (requestCode){
           case 0x001:
               startActivity();
               break;
       }

    }

    @Override
    public void permissionFail(int requestCode) {
        super.permissionFail(requestCode);
        showTipsDialog();
    }

    private void startActivity() {
        String location= (String) SPUtils.get(SplashActivity.this,"location","");
        if (TextUtils.isEmpty(location)) {
            // Intent intent = new Intent(this, ChooseAreaActivity.class);
            Intent intent = new Intent(this, LavaWeatherActivity.class);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(this, LavaWeatherActivity.class);
            startActivity(intent);
            finish();
        }
        //启动service
        Intent intent=new Intent(SplashActivity.this, LocationAndWeatherService.class);
        startService(intent);
    }
}
