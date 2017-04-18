package com.lava.weather.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

import com.lava.weather.util.DisplayUtil;

/**
 * Created by wnagshuo on 2017/02/16.
 */
public class IndexHorizontalScrollView extends HorizontalScrollView {

    private static final String TAG = "IndexHorizontal";


    public IndexHorizontalScrollView(Context context) {
        this(context, null);
    }

    public IndexHorizontalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
       // init();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int offset = computeHorizontalScrollOffset();
        int maxOffset = computeHorizontalScrollRange() - DisplayUtil.getScreenWidth(getContext());

        if(getChildCount()>0){
            final View firstChild = getChildAt(0);
            if(firstChild!=null && firstChild instanceof Today24HourView){
                ((Today24HourView) firstChild).setScrollOffset(offset, maxOffset);
            }
        }
    }

}
