package com.lava.weather.util;


import android.text.TextUtils;

import com.google.gson.Gson;
import com.lava.weather.bean.Weather;
import com.lava.weather.dynamicweather.BaseDrawer;
import com.lava.weather.dynamicweather.BaseDrawer.Type;

import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Shuo.Wang on 2016/12/15.
 */
public class WeatherUtil {

    public static final String WEATHER_URL="https://free-api.heweather.com/v5/weather";
    public static final String WEATHER_KEY="1501577504b6421e97823d836c34fde5";
    /**
     *简体中文 zh-cn
    * 繁体中文 zh-tw
    * 英文    en
    * 印度语  in
    * 泰语    th
    * 默认null为中文，使用和风X5最新接口
    */
    public static final String LANGUAGE=null;
    /**
     * 字体
     */
    public final static String xmTf = "fonts/xm_font2.ttf";



    /**
     * 将返回的JSON数据解析成Weather实体类
     */
    public static Weather handleWeatherResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather5");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent, Weather.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 转换日期2016-12-16为今天、明天、昨天，或者是星期几
     *
     * @param date
     * @return
     */
    public static String changeDate(String date) {
        try {
            final String[] strs = date.split("-");
            final int year = Integer.valueOf(strs[0]);
            final int month = Integer.valueOf(strs[1]);
            final int day = Integer.valueOf(strs[2]);
            Calendar c = Calendar.getInstance();
            int curYear = c.get(Calendar.YEAR);
            int curMonth = c.get(Calendar.MONTH) + 1;// Java月份从0月开始
            int curDay = c.get(Calendar.DAY_OF_MONTH);
            if (curYear == year && curMonth == month) {
                if (curDay == day) {
                    return "今天";
                } else if ((curDay + 1) == day) {
                    return "明天";
                } else if ((curDay - 1) == day) {
                    return "昨天";
                }
            }
            c.set(year, month - 1, day);
            // 一周第一天是否为星期天
            boolean isFirstSunday = (c.getFirstDayOfWeek() == Calendar.SUNDAY);
            // 获取周几
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            // 若一周第一天为星期天，则-1
            if (isFirstSunday) {
                dayOfWeek = dayOfWeek - 1;
                if (dayOfWeek == 0) {
                    dayOfWeek = 7;
                }
            }
            // 打印周几
            // System.out.println(weekDay);

            // 若当天为2016年12月13日（星期一），则打印输出：1
            // 若当天为2016年12月17日（星期五），则打印输出：5
            // 若当天为2016年12月19日（星期日），则打印输出：7
            switch (dayOfWeek) {
                case 1:
                    return "周一";
                case 2:
                    return "周二";
                case 3:
                    return "周三";
                case 4:
                    return "周四";
                case 5:
                    return "周五";
                case 6:
                    return "周六";
                case 7:
                    return "周日";
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 是否是今天2016-12-19 04:00 合法data格式： 2016-12-19 04:00 或者2016-12-19
     *
     * @param date
     * @return
     */
    public static boolean isToday(String date) {
        if (TextUtils.isEmpty(date) || date.length() < 10) {// 2016-12-19
            // length=10
            return false;
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String today = format.format(new Date());
            if (TextUtils.equals(today, date.substring(0, 10))) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     * 出错返回-1
     * @returnTodayDailyForecastIndex
     */
    public static int getTodayDailyForecastIndex(Weather weather){
        int todayIndex = -1;
        if(!"ok".equals(weather.getStatus())){
            return todayIndex;
        }
        for(int i = 0;i< weather.getDaily_forecast().size() ; i++){
            if(isToday(weather.getDaily_forecast().get(i).getDate())){
                todayIndex = i;
                break;
            }
        }
        return todayIndex;
    }
    /**
     * 出错返回null
     * @return Today DailyForecast
     */
    public static Weather.DailyForecastBean getTodayDailyForecast(Weather weather){
        final int todayIndex = getTodayDailyForecastIndex(weather);
        if(todayIndex != -1){
            Weather.DailyForecastBean forecast = weather.getDaily_forecast().get(todayIndex);
            return forecast;
        }
        return null;
    }
    /**
     * 今天的气温 23~16°
     * @return
     */
    public static String getTodayTempDescription(Weather weather){
        final int todayIndex = getTodayDailyForecastIndex(weather);
        if(todayIndex != -1){
            Weather.DailyForecastBean forecast = weather.getDaily_forecast().get(todayIndex);
            return forecast.getTmp().getMin() + "~" + forecast.getTmp().getMax() + "°";
        }
        return "";
    }

    public static boolean isNight(Weather weather) {
        if (weather == null || (weather!=null && !"ok".equals(weather.getStatus()))) {
            return false;
        }
        // SimpleDateFormat time=new SimpleDateFormat("yyyy MM dd HH mm ss");
        try {
            final Date date = new Date();
            String todaydate = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
            String todaydate1 = (new SimpleDateFormat("yyyy-M-d")).format(date);
            Weather.DailyForecastBean todayForecast = null;
            for ( Weather.DailyForecastBean forecast : weather.getDaily_forecast()) {
                if (TextUtils.equals(todaydate, forecast.getDate()) || TextUtils.equals(todaydate1, forecast.getDate())) {
                    todayForecast = forecast;
                    break;
                }
            }
            if (todayForecast != null) {
                final int curTime = Integer.valueOf((new SimpleDateFormat("HHmm").format(date)));
                final int srTime = Integer.valueOf(todayForecast.getAstro().getSr().replaceAll(":", ""));// 日出
                final int ssTime = Integer.valueOf(todayForecast.getAstro().getSs().replaceAll(":", ""));// 日落
                if (curTime > srTime && curTime <= ssTime) {// 是白天
                    return false;
                } else {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 把Weather转换为对应的BaseDrawer.Type
     *
     * @param weather
     * @return
     */
    public static BaseDrawer.Type convertWeatherType(Weather weather) {
        if (weather == null || (weather!=null && !"ok".equals(weather.getStatus()))) {
            return Type.DEFAULT;
        }
        final boolean isNight = isNight(weather);
        try {
            final int w = Integer.valueOf(weather.getNow().getCond().getCode());
            switch (w) {
                case 100://晴
                    return isNight ? Type.CLEAR_N : Type.CLEAR_D;
                case 101:// 多云
                case 102:// 少云
                case 103:// 晴间多云
                    return isNight ? Type.CLOUDY_N : Type.CLOUDY_D;
                case 104:// 阴
                    return isNight ? Type.OVERCAST_N : Type.OVERCAST_D;
                // 200 - 213是风
                case 200:
                case 201:
                case 202:
                case 203:
                case 204:
                case 205:
                case 206:
                case 207:
                case 208:
                case 209:
                case 210:
                case 211:
                case 212:
                case 213:
                    return isNight ? Type.WIND_N : Type.WIND_D;
                case 300:// 阵雨Shower Rain
                case 301:// 强阵雨 Heavy Shower Rain
                case 302:// 雷阵雨 Thundershower
                case 303:// 强雷阵雨 Heavy Thunderstorm
                case 304:// 雷阵雨伴有冰雹 Hail
                case 305:// 小雨 Light Rain
                case 306:// 中雨 Moderate Rain
                case 307:// 大雨 Heavy Rain
                case 308:// 极端降雨 Extreme Rain
                case 309:// 毛毛雨/细雨 Drizzle Rain
                case 310:// 暴雨 Storm
                case 311:// 大暴雨 Heavy Storm
                case 312:// 特大暴雨 Severe Storm
                case 313:// 冻雨 Freezing Rain
                    return isNight ? Type.RAIN_N : Type.RAIN_D;
                case 400:// 小雪 Light Snow
                case 401:// 中雪 Moderate Snow
                case 402:// 大雪 Heavy Snow
                case 403:// 暴雪 Snowstorm
                case 407:// 阵雪 Snow Flurry
                    return isNight ? Type.SNOW_N : Type.SNOW_D;
                case 404:// 雨夹雪 Sleet
                case 405:// 雨雪天气 Rain And Snow
                case 406:// 阵雨夹雪 Shower Snow
                    return isNight ? Type.RAIN_SNOW_N : Type.RAIN_SNOW_D;
                case 500:// 薄雾
                case 501:// 雾
                    return isNight ? Type.FOG_N : Type.FOG_D;
                case 502:// 霾
                case 504:// 浮尘
                    return isNight ? Type.HAZE_N : Type.HAZE_D;
                case 503:// 扬沙
                case 506:// 火山灰
                case 507:// 沙尘暴
                case 508:// 强沙尘暴
                    return isNight ? Type.SAND_N : Type.SAND_D;
                default:
                    return isNight ? Type.UNKNOWN_N : Type.UNKNOWN_D;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isNight ? Type.UNKNOWN_N : Type.UNKNOWN_D;
    }
}
