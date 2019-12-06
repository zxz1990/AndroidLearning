package com.example.zxz.androidtest.activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.example.zxz.androidtest.R;

public class LottieTestActivity extends Activity {

    private static final String TAG = "LottieTestActivity";

    Button mStart, mStop;
    LottieAnimationView mLottie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie_test);

        mStart = (Button)findViewById(R.id.btn_start_animation);
        mStop = (Button)findViewById(R.id.btn_stop_animation);
        mLottie = (LottieAnimationView)findViewById(R.id.lottie);

        mLottie.setAnimation("red_box.json");

        //mLottie.useHardwareAcceleration();
        mLottie.enableMergePathsForKitKatAndAbove(true);

        mLottie.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.d(TAG, "onAnimationStart() called with: animation = [" + animation + "]");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d(TAG, "onAnimationEnd() called with: animation = [" + animation + "]");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.d(TAG, "onAnimationCancel() called with: animation = [" + animation + "]");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.d(TAG, "onAnimationRepeat() called with: animation = [" + animation + "]");
            }
        });

        mLottie.addAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.d(TAG, "onAnimationUpdate() called with: animation = [" + animation + "]");
            }
        });
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLottie.playAnimation();
            }
        });

        mStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLottie.cancelAnimation();
            }
        });
    }

    void init() {

    }
}
