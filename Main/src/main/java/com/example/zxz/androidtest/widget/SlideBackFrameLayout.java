package com.example.zxz.androidtest.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.customview.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.zxz.androidtest.R;

/**
 * Created by zhuxuezhi on 2019/1/20.
 */
public class SlideBackFrameLayout extends FrameLayout {
    private static final String TAG = "SlideBackFrameLayout";

    //于是乎……就有了这么一丢丢的属性……
    private ViewDragHelper mViewDragHelper;
    private View mContentView;
    private int mContentWidth;
    private int mMoveLeft;
    private boolean isClose = false;
    private boolean isEdgeDrag = false;
    private CallBack mCallBack;//自定义内部的回调函数，下面写
    private Drawable mShadowLeft;
    private static final int FULL_ALPHA = 255;
    private static final int DEFAULT_SCRIM_COLOR = 0x99000000;
    private int mScrimColor = DEFAULT_SCRIM_COLOR;
    private float mScrimOpacity;
    private float mScrollPercent;
    private Rect mTmpRect = new Rect();
    private PreviousView mPreviousView = null;

    //界面移出屏幕时接口回调
    public interface CallBack {
        void onFinish();//这个就可以直接用了咯，然后在acitiviy中实例化接口
    }

    //设置回调接口，提供给activity实现接口
    public void setCallBack(CallBack callBack) {
        mCallBack = callBack;
    }

    public SlideBackFrameLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public SlideBackFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlideBackFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        Log.d(TAG, "init() called");
        mViewDragHelper = ViewDragHelper.create(this, 1, new ViewDragHelper.Callback() {

            @Override
            public void onViewDragStateChanged(int state) {
                super.onViewDragStateChanged(state);
                Log.d(TAG, "onViewDragStateChanged() called with: state = [" + state + "]");
            }

            //记录值的变化
            @Override
            public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                Log.d(TAG, "onViewPositionChanged() called with: changedView = [" + changedView + "], left = [" + left + "], top = [" + top + "], dx = [" + dx + "], dy = [" + dy + "]");
                //记录左边的值的变化，因为我们实现的是往右滑动，所以只记录左边的值就可以了
                mScrollPercent = Math.abs((float) left / (mContentView.getWidth() + mShadowLeft.getIntrinsicWidth()));
                mMoveLeft = left;
                if (isClose && (left == mContentWidth)) {
                    //如果当前状态是关闭状态且左边的值等于滑动的View的宽度，
                    //也就是说当前的界面已经滑出屏幕，就回调finish方法，通知activity可以finish了
                    if (mCallBack != null) {
                        mCallBack.onFinish();
                    }
                }
            }

            @Override
            public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
                super.onViewCaptured(capturedChild, activePointerId);
                Log.d(TAG, "onViewCaptured() called with: capturedChild = [" + capturedChild + "], activePointerId = [" + activePointerId + "]");
            }

            //手指松开会触发这个方法，做复位操作就在此方法中实现
            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                Log.d(TAG, "onViewReleased() called with: releasedChild = [" + releasedChild + "], xvel = [" + xvel + "], yvel = [" + yvel + "]");
                //一定得重写computeScroll()方法，不然没有效果
                //如果移动的距离大于或等于当前界面的1/10，则触发关闭
                if (mMoveLeft >= (mContentWidth / 10)) {
                    isClose = true;  //设置滑动的View移动位置，即然当前的界面滑出屏幕
                    mViewDragHelper.settleCapturedViewAt(mContentWidth, releasedChild.getTop());
                } else {
                    //设置滑动的View移动位置，即恢复原来的位置
                    mViewDragHelper.settleCapturedViewAt(0, releasedChild.getTop());
                }
                //通知重绘界面
                invalidate();
            }

            @Override
            public void onEdgeTouched(int edgeFlags, int pointerId) {
                super.onEdgeTouched(edgeFlags, pointerId);
                Log.d(TAG, "onEdgeTouched() called with: edgeFlags = [" + edgeFlags + "], pointerId = [" + pointerId + "]");
                isEdgeDrag = true;

            }

            @Override
            public boolean onEdgeLock(int edgeFlags) {
                Log.d(TAG, "onEdgeLock() called with: edgeFlags = [" + edgeFlags + "]");
                return super.onEdgeLock(edgeFlags);
            }

            @Override
            public int getOrderedChildIndex(int index) {
                Log.d(TAG, "getOrderedChildIndex() called with: index = [" + index + "]");
                return super.getOrderedChildIndex(index);
            }

            //设置水平拖动的距离
            @Override
            public int getViewHorizontalDragRange(@NonNull View child) {
                Log.d(TAG, "getViewHorizontalDragRange() called with: child = [" + child + "]");

                //因为我们移动的是整个界面，所以直接返回整个界面的宽度就可以了
                return mContentWidth;
            }

            @Override
            public int getViewVerticalDragRange(@NonNull View child) {
                Log.d(TAG, "getViewVerticalDragRange() called with: child = [" + child + "]");
                return super.getViewVerticalDragRange(child);
            }

            //重新定位水平移动的位置
            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
                Log.d(TAG, "clampViewPositionHorizontal() called with: child = [" + child + "], left = [" + left + "], dx = [" + dx + "]");
//水平移动距离的范围是0~当前界面的宽度，如果left小于0直接返回0，
                // 如果大于当前界面的宽度直接返回当前界面宽度
                //也就是控制当前界面只能往右移动
                return Math.min(mContentWidth, Math.max(left, 0));
            }

            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                Log.d(TAG, "clampViewPositionVertical() called with: child = [" + child + "], top = [" + top + "], dy = [" + dy + "]");
                return super.clampViewPositionVertical(child, top, dy);
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                super.onEdgeDragStarted(edgeFlags, pointerId);
                Toast.makeText(getContext(), "边界", Toast.LENGTH_LONG).show();
                Log.d(TAG, "onEdgeDragStarted() called with: edgeFlags = [" + edgeFlags + "], pointerId = [" + pointerId + "]");
            }

            //返回true表示可以拖动
            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                return child == mContentView;//如果child==mContentView，返回true，也就是说mContentView可以移动
            }
        });
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
        setShadow();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //把事件传递给ViewDragHelper
        mViewDragHelper.processTouchEvent(event);
//        invalidate();
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        mScrimOpacity = 1 - mScrollPercent;
        //一定要做这个操作，否则onViewReleased不起作用
        if (mViewDragHelper != null && mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //SwipeBackFrameLayout的子View有且只有一个，否则抛异常
        if (getChildCount() != 1) {
            throw new IllegalStateException("SwipeBackFrameLayout must host one child.");
        }
        //取得当前布局的第一个子View，也是唯一一个子View
        //也就是activity的主要布局
        mContentView = getChildAt(0);
    }

    @Override
    public void addView(View child) {
        if (mPreviousView == null) {
            mPreviousView = new PreviousView(getContext());
        }
        if (mPreviousView.getParent() == null) {
            mPreviousView.setHost(child);//测一下
            super.addView(mPreviousView);
        }
        super.addView(child);
        mContentView = child;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);    //获取当前界面宽度
        mContentWidth = mContentView.getWidth();
    }

    //画一个子项 ，到时候把acitivity的主题设置下就可以看到下面的activity了
    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        final boolean drawContent = child == mContentView;
        boolean ret = super.drawChild(canvas, child, drawingTime);
        if (drawContent && mViewDragHelper.getViewDragState() != ViewDragHelper.STATE_IDLE) {
            drawShadow(canvas, child);
            drawScrim(canvas, child);
        }
        return ret;
    }

    //这个是那个阴影线
    private void drawShadow(Canvas canvas, View child) {
        final Rect childRect = mTmpRect;
        child.getHitRect(childRect);
        mShadowLeft.setBounds(childRect.left - mShadowLeft.getIntrinsicWidth(), childRect.top, childRect.left, childRect.bottom);
        mShadowLeft.setAlpha((int) (mScrimOpacity * FULL_ALPHA));
        mShadowLeft.draw(canvas);
    }

    //这个就是画那个透明渐变出来的帷幕，还真™不知道怎么形容
    private void drawScrim(Canvas canvas, View child) {
        final int baseAlpha = (mScrimColor & 0xff000000) >>> 24;
        final int alpha = (int) (baseAlpha * mScrimOpacity);
        final int color = alpha << 24 | (mScrimColor & 0xffffff);
        canvas.clipRect(0, 0, child.getLeft(), getHeight());
        canvas.drawColor(color);
    }

    public void setShadow() {
        mShadowLeft = getResources().getDrawable(R.drawable.child_app_icon);
        invalidate();
    }

    class PreviousView extends View {
        private View mHost;

        public PreviousView(Context context) {
            super(context);
        }

        public PreviousView(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        public PreviousView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        public void setHost(View host) {
            mHost = host;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (mHost != null) {
                mHost.draw(canvas);
            }
        }
    }
}
