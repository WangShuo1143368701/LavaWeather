# LavaWeather
LavaWeather-一个集成各种天气相关View的项目
====

### 本来是公司的一个天气项目，我正在预研的时候告诉我不做了。现在就把它用于集成各种Weather View的项目。

#### 全部基于Android6.0全新API及material design设计。重写Android SwipeRefreshLayout---->WsRefreshLayout
#### DrawerLayout,NavigationView等。最简集成百度定位。。。

![](https://github.com/WangShuo1143368701/LavaWeather/raw/master/screenshot/GIF.gif) 

![](https://github.com/WangShuo1143368701/LavaWeather/raw/master/screenshot/GIF3.gif)

![](https://github.com/WangShuo1143368701/LavaWeather/raw/master/screenshot/GIF4.gif)

![](https://github.com/WangShuo1143368701/LavaWeather/raw/master/screenshot/hw.png)
 

添加一个Weather View的方式 加 <include layout="@layout/weather_hw_hourly"/>
```java
    <include layout="@layout/weather_hourly" />

   <include layout="@layout/weather_detailed_information" />

   <include layout="@layout/weather_air_quality" />

   <include layout="@layout/weather_sun_wind" />

   <include layout="@layout/weather_moji_hourly"/>

   <include layout="@layout/weather_qq_hourly"/>

   <include layout="@layout/weather_hw_hourly"/>
   
   
   // <include layout="@layout/weather_hw_hourly"/>中代码如下
   <?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical" android:layout_width="match_parent"
    android:layout_height="match_parent">

    <TextView
        style="@style/section_title"
        android:text="仿华为" />

    <com.lava.weather.view.NowHwWeatherView
        android:id="@+id/now_hw_weather"
        android:layout_gravity="center"
        android:padding="20dp"
        android:layout_width="300dp"
        android:layout_height="300dp" />

    <View
        android:layout_width="match_parent"
        android:layout_height="@dimen/w_divider_size"
        android:background="@color/w_divider" />

</LinearLayout>
 ```
仿QQ天气预报UI ：http://blog.csdn.net/king1425/article/details/57084245
仿华为天气预报UI ：http://blog.csdn.net/king1425/article/details/57083778

各位客官如果觉得还不错，就双击666点亮star
