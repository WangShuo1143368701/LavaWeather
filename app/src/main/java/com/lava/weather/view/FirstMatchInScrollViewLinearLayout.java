package com.lava.weather.view;

/**
 * Created by Shuo.Wang on 2016/12/14.
 */
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.ScrollView;
/**
 * Created by Shuo.Wang on 2016/12/14.
 */
public class FirstMatchInScrollViewLinearLayout extends LinearLayout{

    public FirstMatchInScrollViewLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FirstMatchInScrollViewLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FirstMatchInScrollViewLinearLayout(Context context) {
        super(context);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(getChildCount() > 0){
            final ViewParent parent = getParent();
            if(parent!=null && parent instanceof ScrollView){
                final int height = ((ScrollView)parent).getMeasuredHeight();
                if(height > 0 ){
                    Log.d("wangshuo", "ScrollView.height->" + height);
                    final View firstChild = getChildAt(0);
                    LinearLayout.LayoutParams layoutParams = (LayoutParams) firstChild.getLayoutParams();
                    layoutParams.height = height;
                    firstChild.setLayoutParams(layoutParams);
                }
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
