package com.example.zxz.androidtest.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by xuezhi.zxz on 2017/6/26.
 */

public class MyItemDecoration extends RecyclerView.ItemDecoration {
    public static final String TAG = MyItemDecoration.class.getSimpleName();

    private Drawable mDrawable;
    private int HORIZONTAL_WIDTH = 10;
    private int VERTICAL_WIDTH = 10;

    public MyItemDecoration(Context context) {
        super();
        mDrawable = new ColorDrawable(Color.YELLOW);//context.getResources().getDrawable(R.drawable.child_app_icon);
    }

    void drawVertical(Canvas c, RecyclerView parent) {
        int cnt = parent.getChildCount();
        for (int i = 0; i < cnt; ++i) {
            View child = parent.getChildAt(i);
            int left = child.getLeft();
            int right = child.getRight();
            int top = child.getTop();
            int bottom = child.getBottom();
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            mDrawable.setBounds(left - params.leftMargin - HORIZONTAL_WIDTH, top - params.topMargin - VERTICAL_WIDTH, right + params.rightMargin + HORIZONTAL_WIDTH, bottom + params.bottomMargin + VERTICAL_WIDTH);
            Log.i(TAG, "drawVertical: " + mDrawable.getBounds());
            mDrawable.draw(c);
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        Log.d(TAG, "onDraw() called with: c = [" + c + "], parent = [" + parent + "], state = [" + state + "]");
        drawVertical(c, parent);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        Log.d(TAG, "onDrawOver() called with: c = [" + c + "], parent = [" + parent + "], state = [" + state + "]");
        super.onDrawOver(c, parent, state);
//        mDrawable.setBounds(0,0,100,100);
//        mDrawable.draw(c);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        super.getItemOffsets(outRect, view, parent, state);
        Log.d(TAG, "getItemOffsets() called with: outRect = [" + outRect + "], view = [" + view + "], parent = [" + parent + "], state = [" + state + "]");
        outRect.set(HORIZONTAL_WIDTH, VERTICAL_WIDTH, HORIZONTAL_WIDTH, VERTICAL_WIDTH);
    }
}
