package com.lava.weather.activity;


import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lava.weather.bean.Weather;
import com.lava.weather.dynamicweather.BaseDrawer;
import com.lava.weather.refreshlayout.WeatherProgressDrawable;
import com.lava.weather.refreshlayout.WsRefreshLayout;
import com.lava.weather.util.HttpUtil;
import com.lava.weather.util.SPUtils;
import com.lava.weather.util.WeatherUtil;
import com.lava.weather.view.AqiView;
import com.lava.weather.view.AstroView;
import com.lava.weather.view.DailyQQWeatherView;
import com.lava.weather.view.DailyWeatherView;
import com.lava.weather.view.HourlyForecastView;
import com.lava.weather.view.IndexHorizontalScrollView;
import com.lava.weather.view.Today24HourView;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Shuo.Wang on 2016/12/15.
 */
public class LavaWeatherFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
    private Weather mWeather;
    private View mRootView;
    private WsRefreshLayout mSwipeRefreshLayout;

    private TextView nowCityName;
    private TextView nowTmp;
    private TextView nowCloudyText;
    private TextView nowApiText;
    private TextView nowUpdateLoc;
    private DailyWeatherView mDailyWeatherView;
    private HourlyForecastView mHourlyForecastView;

    private TextView detailTmp;
    private TextView detailBottomline;
    private TextView detailFl;
    private TextView detailHum;
    private TextView detailVis;
    private TextView detailPcpn;

    private TextView aqiPm25;
    private TextView aqiPm10;
    private TextView aqiSo2;
    private TextView aqiNo2;
    private AqiView mAqiView;

    private AstroView mAstroView;

    private IndexHorizontalScrollView mIndexHSView;
    private Today24HourView mToday24HView;

    private DailyQQWeatherView mDailyQQView;

    //private DynamicWeatherView mDynamicWeatherView;

    public LavaWeatherFragment() {
        // Required empty public constructor
    }

    public static LavaWeatherFragment newInstance(String param1) {
        LavaWeatherFragment fragment = new LavaWeatherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
        //根据WeatherID去数据库中取Weather数据，如果没有则请求网络；
        String weatherString= (String) SPUtils.get(getActivity(),mParam1,"");
        Log.d("wangshuo","weatherString--->"+weatherString);
        if(!TextUtils.isEmpty(weatherString)){
            mWeather = WeatherUtil.handleWeatherResponse(weatherString);
        }else {
            requestWeather(mParam1, WeatherUtil.LANGUAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(mRootView==null) {
            mRootView = inflater.inflate(R.layout.fragment_lava_weather, container, false);
            mSwipeRefreshLayout= (WsRefreshLayout) mRootView.findViewById(R.id.swipe_refresh);
            mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
            mSwipeRefreshLayout.setCircleBackground(Color.parseColor("#00000000"));
            Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.w0);
            mSwipeRefreshLayout.setProgressView(new WeatherProgressDrawable(getActivity(),mSwipeRefreshLayout,bitmap));
            mSwipeRefreshLayout.setOnRefreshListener(new WsRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    requestWeather(mParam1, WeatherUtil.LANGUAGE);
                }
            });

            nowCityName= (TextView) mRootView.findViewById(R.id.now_city_name);
            nowTmp= (TextView) mRootView.findViewById(R.id.now_tmp);
            nowCloudyText= (TextView) mRootView.findViewById(R.id.now_cloudy_text);
            nowApiText= (TextView) mRootView.findViewById(R.id.now_aqi_text);
            nowUpdateLoc= (TextView) mRootView.findViewById(R.id.now_basic_update_loc );

            mDailyWeatherView= (DailyWeatherView) mRootView.findViewById(R.id.DailyWeatherView);
            mHourlyForecastView= (HourlyForecastView) mRootView.findViewById(R.id.hourlyForecastView);

            detailTmp= (TextView) mRootView.findViewById(R.id.detail_temp);
            detailBottomline= (TextView) mRootView.findViewById(R.id.detail_bottomline);
            detailFl= (TextView) mRootView.findViewById(R.id.detailed_fl);
            detailHum= (TextView) mRootView.findViewById(R.id.detailed_hum);
            detailVis= (TextView) mRootView.findViewById(R.id.detailed_vis);
            detailPcpn= (TextView) mRootView.findViewById(R.id.detailed_pcpn);

            aqiPm25= (TextView) mRootView.findViewById(R.id.aqi_pm25);
            aqiPm10= (TextView) mRootView.findViewById(R.id.aqi_pm10);
            aqiSo2= (TextView) mRootView.findViewById(R.id.aqi_so2);
            aqiNo2= (TextView) mRootView.findViewById(R.id.aqi_no2);
            mAqiView= (AqiView) mRootView.findViewById(R.id.aqi_view);

            mAstroView= (AstroView) mRootView.findViewById(R.id.astroView);

            mIndexHSView = (IndexHorizontalScrollView)mRootView.findViewById(R.id.indexHorizontalScrollView);
            mToday24HView = (Today24HourView)mRootView.findViewById(R.id.today24HourView);

            mDailyQQView= (DailyQQWeatherView) mRootView.findViewById(R.id.dailyQQView);

            //mDynamicWeatherView= (DynamicWeatherView) mRootView.findViewById(R.id.dynamic_weather_view);
            //testBgView();//测试动态背景
        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
             Log.d("wangshuo", "onActivityCreated");
             updateWeatherUI(mWeather);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("wangshuo", "onResume");
        //mDynamicWeatherView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("wangshuo", "onPause");
        //mDynamicWeatherView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("wangshuo", "onDestroy");
       // mDynamicWeatherView.onDestroy();
    }

    private void requestWeather(final String weatherId, String language) {
        if(weatherId==null){
            Log.d("wangshuo","requestWeather--->weatherId==null");
            return;
        }
        if(language==null){
            language="zh-cn";
        }
        String weatherUrl= WeatherUtil.WEATHER_URL+"?city="+ weatherId +"&key="+WeatherUtil.WEATHER_KEY+"&lang="+language;
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("wangshuo","onFailure--->fatal");
                        Toast.makeText(getActivity(), "刷新失败", Toast.LENGTH_SHORT).show();
                        if(mSwipeRefreshLayout!=null){
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                mWeather = WeatherUtil.handleWeatherResponse(responseText);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(mWeather !=null && "ok".equals(mWeather.getStatus())){
                            Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                            Log.d("wangshuo","onResponse--->success--->"+mWeather.getStatus()+"responseText= "+responseText);
                            updateWeatherUI(mWeather);
                            SPUtils.put(getActivity(),weatherId,responseText);
                        }else{
                            Toast.makeText(getActivity(), "刷新失败", Toast.LENGTH_SHORT).show();
                            Log.d("wangshuo","onResponse--->fatal--->"+(mWeather==null));
                        }
                        if(mSwipeRefreshLayout!=null){
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                });
            }
        });
    }

    private void updateWeatherUI(Weather mWeather) {
        if(mRootView==null || mWeather == null || (mWeather!=null && !"ok".equals(mWeather.getStatus()))){
            Log.d("wangshuo","updateWeatherUI--->fatal-->"+(mRootView==null));
            return;
        }
        //updateBgSurfaceView(mWeather);

        nowCityName.setText(mWeather.getBasic().getCity());
        nowTmp.setText(mWeather.getNow().getTmp()+getActivity().getResources().getString(R.string.now_degree));
        nowCloudyText.setText(mWeather.getNow().getCond().getTxt());
        nowApiText.setText(mWeather.getAqi().getCity().getQlty());
        nowUpdateLoc.setText(mWeather.getBasic().getUpdate().getLoc().split(" ")[1]+" "+getActivity().getResources().getString(R.string.now_time_publish));

        mDailyWeatherView.setData(mWeather);
        mHourlyForecastView.setData(mWeather);

        detailTmp.setText(mWeather.getNow().getTmp()+getActivity().getResources().getString(R.string.now_degree));
        detailBottomline.setText(mWeather.getNow().getCond().getTxt());
        detailFl.setText(mWeather.getNow().getFl()+getActivity().getResources().getString(R.string.now_degree));
        detailHum.setText(mWeather.getNow().getHum()+"%");
        detailVis.setText(mWeather.getNow().getVis()+"Km");
        detailPcpn.setText(mWeather.getNow().getPcpn()+"mm");

        aqiPm25.setText(mWeather.getAqi().getCity().getPm25()+ "μg/m³");
        aqiPm10.setText(mWeather.getAqi().getCity().getPm10()+ "μg/m³");
        aqiSo2.setText(mWeather.getAqi().getCity().getSo2()+ "μg/m³");
        aqiNo2.setText(mWeather.getAqi().getCity().getNo2()+ "μg/m³");
        mAqiView.setData(mWeather);

        mAstroView.setData(mWeather);

        updateTextViewTf();
    }

    private void updateBgSurfaceView(Weather mWeather) {

            //final BaseDrawer.Type curType = WeatherUtil.convertWeatherType(mWeather);
            //mDynamicWeatherView.setDrawerType(curType);//ws

    }

    private void updateTextViewTf() {
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), WeatherUtil.xmTf);
        nowTmp.setTypeface(tf);
        detailTmp.setTypeface(tf);
        detailBottomline.setTypeface(tf);
        detailFl.setTypeface(tf);
        detailHum.setTypeface(tf);
        detailVis.setTypeface(tf);
        detailPcpn.setTypeface(tf);
        aqiPm25.setTypeface(tf);
        aqiPm10.setTypeface(tf);
        aqiSo2.setTypeface(tf);
        aqiNo2.setTypeface(tf);

    }
    private void testBgView() {
        mRootView.findViewById(R.id.first_lin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                ArrayList<String> strs = new ArrayList<String>();
                for (BaseDrawer.Type t : BaseDrawer.Type.values()) {
                    strs.add(t.toString());
                }
            /*    int index = 0;
                for (int i = 0; i < BaseDrawer.Type.values().length; i++) {
                    if (BaseDrawer.Type.values()[i] == mDrawerType) {
                        index = i;
                        break;
                    }
                }*/
                builder.setSingleChoiceItems(strs.toArray(new String[] {}), 0,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                BaseDrawer.Type mDrawerType = BaseDrawer.Type.values()[which];
                                //mDynamicWeatherView.setDrawerType(mDrawerType);//ws
                                dialog.dismiss();
                                // Toast.makeText(getActivity(), "onClick->"
                                // + which, Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, null);
                builder.create().show();
            }
        });
    }

}
