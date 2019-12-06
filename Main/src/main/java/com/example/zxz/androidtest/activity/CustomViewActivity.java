package com.example.zxz.androidtest.activity;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;

import com.example.zxz.androidtest.R;
import com.example.zxz.androidtest.widget.SectorView;

public class CustomViewActivity extends Activity {

    private SectorView view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        view = ((SectorView)findViewById(R.id.sector_view));
        findViewById(R.id.button).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        view.reset();
                        view.startAnimation();
                        break;
                    case MotionEvent.ACTION_UP:
                        view.stopAnimation();
                        break;
                }
                return false;
            }
        });
    }

}
