package com.lava.weather.refreshlayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

/**
 * Created by Shuo.Wang on 2017/2/23.
 */

public class WeatherProgressDrawable extends MaterialProgressDrawable {

    //  旋转因子，调整旋转速度
    private static final int ROTATION_FACTOR = 5*360;
    //  加载时的动画
    private Animation mAnimation;
    private View mParent;
    private Bitmap mBitmap;
    //  旋转角度
    private float rotation;
    private Paint paint;


    public WeatherProgressDrawable(Context context, View parent,Bitmap bitmap) {
        super(context, parent);
        mParent = parent;
        paint = new Paint();
        mBitmap= bitmap;
        setupAnimation();
    }

    private void setupAnimation() {
//    初始化旋转动画
        mAnimation = new Animation(){
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                setProgressRotation(-interpolatedTime);
            }
        };
        mAnimation.setDuration(5000);
//    无限重复
        mAnimation.setRepeatCount(Animation.INFINITE);
        mAnimation.setRepeatMode(Animation.RESTART);
//    均匀转速
        mAnimation.setInterpolator(new LinearInterpolator());
    }


    @Override
    public void start() {
        mParent.startAnimation(mAnimation);
    }
    public void setBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    @Override
    public void setProgressRotation(float rotation) {
     //下拉时逆时针转加载时顺时针转，旋转因子是为了调整转的速度。
        this.rotation = -rotation*ROTATION_FACTOR;
        invalidateSelf();
    }

    @Override
    public void draw(Canvas c) {
        Rect bound = getBounds();
        c.rotate(rotation,bound.exactCenterX(),bound.exactCenterY());
        Rect src = new Rect(0,0,mBitmap.getWidth(),mBitmap.getHeight());
        c.drawBitmap(mBitmap,src,bound,paint);
    }
}


