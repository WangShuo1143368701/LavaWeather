package com.lava.weather.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Shuo.Wang on 2016/12/14.
 */
public class LavaViewPager extends ViewPager  {

    private static final String TAG = "LavaViewPager";
    public LavaViewPager(Context context) {
        super(context);
    }

    public LavaViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    private float mDownPosX = 0;
    private float mDownPosY = 0;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final float x = ev.getX();
        final float y = ev.getY();

        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mDownPosX = x;
                mDownPosY = y;

                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaX = Math.abs(x - mDownPosX);
                final float deltaY = Math.abs(y - mDownPosY);
                if (deltaX < deltaY) {
                    return false;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }


}
