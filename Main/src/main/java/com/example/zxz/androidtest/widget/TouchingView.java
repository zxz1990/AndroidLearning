package com.example.zxz.androidtest.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/**
 * Created by xuezhi.zxz on 2017/5/8.
 */

public class TouchingView extends View{

    public static final String TAG = TouchingView.class.getSimpleName();
    private static final float ORIGIN_R = 100;

    Paint mPaint = new Paint();


    private Scroller mScroller;
    private float mCircleX = 0;
    private float mCircleY = 0;
    private float mCircleR = ORIGIN_R;
    private double mOriginD = 0;

    public TouchingView(Context context) {
        super(context);
        init(context);
    }

    public TouchingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TouchingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context) {
        mPaint.setColor(Color.GREEN);
        mScroller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw() called with: X=" + mCircleX + ",Y=" + mCircleY + ",R=" + mCircleR + ",canvas = [" + canvas + "]");
//        mCircleX = getWidth()/2;
//        mCircleY = getHeight()/2;
        canvas.drawCircle(mCircleX, mCircleY, mCircleR, mPaint);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG, "dispatchTouchEvent() called with: event = [" + event + "]");
        return super.dispatchTouchEvent(event);
    }

    double getFingerDistance(MotionEvent event) {
        double d = 0;
        if (event.getPointerCount() > 1) {
            float x0 = event.getX(0);
            float y0 = event.getY(0);
            float x1 = event.getX(1);
            float y1 = event.getY(1);
            d = Math.sqrt((x0 - x1) * (x0 - x1) + (y0 - y1) * (y0 - y1));
        }
        Log.d(TAG, "getFingerDistance() called with: d=" + d + " event = [" + event + "]");
        return d;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent() called with: event = [" + event + "]");
//        scrollTo(-50,-20);
//        offsetLeftAndRight(50);
//        offsetTopAndBottom(20);
//        event.getAction()
        int action = event.getAction()&MotionEvent.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mCircleX = event.getX();
                mCircleY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mCircleX = event.getX();
                mCircleY = event.getY();
                if(event.getPointerCount()>1) {
                    double d = getFingerDistance(event);
                    mCircleR = ORIGIN_R + (float) (d - mOriginD);
                    Log.i(TAG, "onTouchEvent: d=" + d + ",mOriginD=" + mOriginD + ",mCircleR=" + mCircleR);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.i(TAG, "onTouchEvent: ACTION_POINTER_DOWN called ");
                mOriginD = getFingerDistance(event);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
        }
        invalidate();
        return true;
    }

    public void smoothScrollTo(int destX, int destY) {
        int scroolX = getScrollX();
        int delta = destX - scroolX;
        mScroller.startScroll(scroolX, 0, delta, 0, 1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        Log.d(TAG, "onAttachedToWindow() called");
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        Log.d(TAG, "onDetachedFromWindow() called");
        super.onDetachedFromWindow();
    }
}
