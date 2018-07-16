package com.example.zxz.androidtest.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class SectorView extends View {

    public static final int DURATION = 2000;
    public static final int MAX_PROGRESS = 100;
    public static final float STEP = 1f;
    private static final String TAG = "SectorView";

    private Paint mPaint1, mPaint2;
    private RectF mRectF;
    private Handler mHandler;

    private float mProgress = 0f;//最大不能超过100

    {
        init();
    }

    public SectorView(Context context) {
        super(context);
    }

    public SectorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SectorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void init() {
        mRectF = new RectF(0, 0, 100, 100);
        mPaint1 = new Paint();
        mPaint1.setAntiAlias(true);
        mPaint1.setColor(Color.parseColor("#ffffff"));
        mPaint2 = new Paint(mPaint1);
        mPaint2.setColor(Color.parseColor("#88aaff"));
        mHandler = new Handler();
    }

    void setProgress(float progress) {
        mProgress = progress;
    }

    public void reset() {
        setProgress(0);
        Log.d(TAG, "reset() called");
    }

    public void startAnimation() {
        if (mProgress > MAX_PROGRESS) {
            stopAnimation();
        }

        setProgress(mProgress + STEP);
        invalidate();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startAnimation();
            }
        }, (int)(DURATION * STEP / MAX_PROGRESS));
    }

    public void stopAnimation() {
        Log.d(TAG, "stopAnimation() called");
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mRectF.left = getPaddingLeft();
        mRectF.top = getPaddingTop();
        mRectF.right = getWidth() - getPaddingRight();
        mRectF.bottom = getHeight() - getPaddingBottom();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(mRectF, 0, 360, true, mPaint1);
        canvas.drawArc(mRectF, -90, 360 * mProgress / MAX_PROGRESS, true, mPaint2);
    }
}
