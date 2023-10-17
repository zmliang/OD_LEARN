package com.zml.tab;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.ScrollView;

public class SwitchTab extends ViewGroup {

    private VelocityTracker mVelocityTracker;
    private int mTouchSlop;

    private OverScroller mScroller;

    SparseArray<View> child = new SparseArray<>();

    public SwitchTab(Context context) {
        super(context);
        this.init(context);
    }

    public SwitchTab(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public SwitchTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context);
    }

    private void init(Context context){
        mScroller = new OverScroller(context);
        setFocusable(true);
        setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
        setWillNotDraw(false);
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w_mode = MeasureSpec.getMode(widthMeasureSpec);
        int w_value = MeasureSpec.getSize(widthMeasureSpec);
        int h_mode = MeasureSpec.getMode(heightMeasureSpec);
        int h_value = MeasureSpec.getSize(heightMeasureSpec);

        int count = getChildCount();

        int totalWidth = 0;
        int maxHeight = 0;
        int useWidth = 0;
        for (int i=0;i<count;i++){
            View child = getChildAt(i);
            final ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) child.getLayoutParams();
            Log.i("zml","lp="+lp.toString());
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            totalWidth+= (child.getMeasuredWidth());
            maxHeight = Math.max(maxHeight,child.getMeasuredHeight());
        }

        if (w_mode == MeasureSpec.AT_MOST){
            w_value = totalWidth;
        }
        if (h_mode == MeasureSpec.AT_MOST){
            h_value = maxHeight;
        }
        Log.i("zml","width="+w_value+"； height="+h_value);
        setMeasuredDimension(w_value,h_value);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int left = l;
        for (int i=0;i<count;i++){
            View child = getChildAt(i);
            Log.i("zml","index="+i+"; left = "+left+"; top = "+t
                    +"; right = "+(left+child.getMeasuredWidth())
                    +"; bottom = "+child.getMeasuredHeight());
            child.layout(left,t,left+child.getMeasuredWidth(),child.getMeasuredHeight());
            left+=child.getMeasuredWidth();
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
