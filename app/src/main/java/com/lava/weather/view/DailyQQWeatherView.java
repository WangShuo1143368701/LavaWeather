package com.lava.weather.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.lava.weather.activity.R;


/**
 * Created by Shuo.Wang on 2017/2/20.
 */

public class DailyQQWeatherView extends View {
    //模拟数据 start
    public static final String wees[]={"今天","星期二","星期三","星期四","星期五"};
    public static final String dayWeather[]={"晴天","小雨","小雨","中雨","小雨"};
    public static final int  dayRes[]={R.drawable.w0,R.drawable.w7,R.drawable.w7,R.drawable.w8,R.drawable.w7};
    public static final int dayTemp[]={23,22,23,20,17};
    public static final int nightTemp[]={18,17,18,12,11};
    public static final int  nightRes[]={R.drawable.w2,R.drawable.w7,R.drawable.w7,R.drawable.w8,R.drawable.w7};
    public static final String nightWeather[]={"小雨","小雨","小雨","中雨","小雨"};
    public static final String date[]={"02/20","02/21","02/22","02/23","02/24"};
    public static final String wind[]={"南风","东风","西风","东南风","西北风"};
    public static final String windVilue[]={"微风","3-4级","5-6级","2-3级","微风"};
   //模拟数据

    private int mCount;//份
    private int mHorizontalCount;
    private float itemWidth;//每份长度
    private float itemHeight;//每份高度
    private Paint verticalLinePaint;
    private Paint textPaint;
    private Bitmap mBitmap;

    private Paint pointPaint;
    private Paint tempLinePaint;
    private Path mPath;
    private PathEffect mPathEffect;

    public DailyQQWeatherView(Context context) {
        this(context,null);
    }

    public DailyQQWeatherView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DailyQQWeatherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        intPaint();

        mCount=5;
        mHorizontalCount=40;
    }

    private void intPaint() {
        verticalLinePaint = new Paint();
        verticalLinePaint.setColor(Color.WHITE);
        verticalLinePaint.setStrokeWidth(1);
        verticalLinePaint.setStyle(Paint.Style.STROKE);
        verticalLinePaint.setAntiAlias(true);

        textPaint=new TextPaint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(24);
        textPaint.setStrokeWidth(4);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);

        pointPaint = new Paint();
        pointPaint.setColor(Color.WHITE);
        pointPaint.setAntiAlias(true);
        pointPaint.setStyle(Paint.Style.FILL);

        tempLinePaint=new Paint();
        tempLinePaint.setColor(Color.WHITE);
        tempLinePaint.setAntiAlias(true);
        tempLinePaint.setStyle(Paint.Style.STROKE);
        tempLinePaint.setStrokeWidth(2);
        tempLinePaint.setAntiAlias(true);
        mPath=new Path();
        mPathEffect=new CornerPathEffect(20);
        tempLinePaint.setPathEffect(mPathEffect);
    }

    public void setData(){

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        itemWidth=(getWidth()-getPaddingLeft()-getPaddingRight())/mCount;
        itemHeight=(getHeight()-getPaddingTop()-getPaddingBottom())/mHorizontalCount;
        //drawverticalLine(canvas);//绘制竖直线
        //drawHorizontalLine(canvas);//绘制水平直线

        drawText(canvas);//绘制文字图片
        drawTempPointLine(canvas);//绘制温度折线

    }

    private void drawTempPointLine(Canvas canvas) {
        drawDayTempPointLine(canvas);
        drawNightTempPointLine(canvas);
    }

    private void drawDayTempPointLine(Canvas canvas) {
         int maxTemp = 23;
         int minTemp = 17;
         float maxTempHeight=getPaddingTop()+itemHeight*11;
         float minTempHeight=getPaddingTop()+itemHeight*18;
         float X;
         double Y;
         for(int i=0;i<mCount;i++){
             X=(getPaddingLeft()+itemWidth)/2+i*itemWidth;
             Y=minTempHeight-(dayTemp[i]-minTemp)*1.0/(maxTemp-minTemp)*(minTempHeight-maxTempHeight);
             canvas.drawCircle(X, (float) Y, 6, pointPaint);//绘制点
             canvas.drawText(dayTemp[i]+"℃",X,(float)(Y-itemHeight+getTextPaintOffset(textPaint)),textPaint);//绘制温度数字
             if(i == 0) {
                 mPath.moveTo(X, (float) Y);
             }
                 mPath.lineTo(X,(float) Y);
                 //tempLinePaint.setPathEffect(mPathEffect);
                 canvas.drawPath(mPath,tempLinePaint);
         }

    }

    private void drawNightTempPointLine(Canvas canvas) {
        int maxTemp = 18;
        int minTemp = 11;
        float maxTempHeight=getPaddingTop()+itemHeight*21;
        float minTempHeight=getPaddingTop()+itemHeight*28;
        float X;
        double Y;
        for(int i=0;i<mCount;i++){
            X=(getPaddingLeft()+itemWidth)/2+i*itemWidth;
            Y=minTempHeight-(nightTemp[i]-minTemp)*1.0/(maxTemp-minTemp)*(minTempHeight-maxTempHeight);
            canvas.drawCircle(X, (float) Y, 6, pointPaint);//绘制点
            canvas.drawText(nightTemp[i]+"℃",X,(float)(Y-itemHeight+getTextPaintOffset(textPaint)),textPaint);//绘制温度数字
            if(i == 0) {
                mPath.moveTo(X, (float) Y);
            }
            mPath.lineTo(X,(float) Y);
            //tempLinePaint.setPathEffect(mPathEffect);
            canvas.drawPath(mPath,tempLinePaint);
        }
    }

    private void drawText(Canvas canvas) {
        float weekX,weekY;
        float dayWeatherX,dayWeatherY;
        float dayResX,dayResY;

        float nightWeatherX,nightWeatherY;
        float dateX,dateY;
        float windVilueX,windVilueY;
        for(int i=0;i<mCount;i++){
            //绘制星期
            weekX=(getPaddingLeft()+itemWidth)/2+i*itemWidth;
            weekY=getPaddingTop()+itemHeight+getTextPaintOffset(textPaint);
            canvas.drawText(wees[i],weekX,weekY,textPaint);

            dayWeatherX=weekX;
            dayWeatherY=getPaddingTop()+itemHeight*4+getTextPaintOffset(textPaint);
            canvas.drawText(dayWeather[i],dayWeatherX,dayWeatherY,textPaint);

            //绘制图片
            Drawable drawable = ContextCompat.getDrawable(getContext(), dayRes[i]);
            drawable.setBounds((int)(getPaddingLeft()+i*itemWidth),(int)(getPaddingTop()+itemHeight*6),(int)(getPaddingLeft()+(i+1)*itemWidth),(int)(getPaddingTop()+itemHeight*8));
            drawable.draw(canvas);
            //dayResX=weekX;
            //dayResY=getPaddingTop()+itemHeight*7;
            //mBitmap= BitmapFactory.decodeResource(getResources(), dayRes[i]);
            //canvas.drawBitmap(mBitmap,dayResX-mBitmap.getWidth()/2,dayResY-mBitmap.getHeight()/2,null);
            Drawable drawable2 = ContextCompat.getDrawable(getContext(), nightRes[i]);
            drawable2.setBounds((int)(getPaddingLeft()+i*itemWidth),(int)(getPaddingTop()+itemHeight*29),(int)(getPaddingLeft()+(i+1)*itemWidth),(int)(getPaddingTop()+itemHeight*31));
            drawable2.draw(canvas);

            //绘制夜间天气
            nightWeatherX=weekX;
            nightWeatherY=getPaddingTop()+itemHeight*33+getTextPaintOffset(textPaint);
            canvas.drawText(nightWeather[i],nightWeatherX,nightWeatherY,textPaint);

            //绘制时期
            dateX=weekX;
            dateY=getPaddingTop()+itemHeight*36+getTextPaintOffset(textPaint);
            canvas.drawText(date[i],dateX,dateY,textPaint);

            //绘制风力值
            windVilueX=weekX;
            windVilueY=getPaddingTop()+itemHeight*39+getTextPaintOffset(textPaint);
            canvas.drawText(windVilue[i],windVilueX,windVilueY,textPaint);
        }
    }

    private void drawHorizontalLine(Canvas canvas) {
        float startX,startY,endX,endY;
        for(int i=0;i<(mHorizontalCount+1);i++){
            startX=getPaddingLeft();
            startY=getPaddingTop()+i*itemHeight;
            endX=getWidth()-getPaddingRight();
            endY=startY;
            canvas.drawLine(startX,startY,endX,endY,verticalLinePaint);
     }

    }

    private void drawverticalLine(Canvas canvas) {
        float startX;
        float startY;
        float endX;
        float endY;
        for(int i=1;i<mCount;i++){
            startX=getPaddingLeft()+i*itemWidth;
            startY=getPaddingTop();
            endX=startX;
            endY=getHeight()-getPaddingBottom();
            canvas.drawLine(startX,startY,endX,endY,verticalLinePaint);
        }
    }

    public float getTextPaintOffset(Paint paint){
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        return  -fontMetrics.descent+(fontMetrics.bottom-fontMetrics.top)/2;
    }
}
