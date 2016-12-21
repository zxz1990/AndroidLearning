package com.example.zxz.androidtest.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.xuezhizxz.androidlearning.R;
import com.example.zxz.androidtest.adapter.MyAdapter;
import com.example.zxz.androidtest.data.TestData;

/**
 * Created by 学智 on 2016-01-02.
 */
public class TestActivity extends Activity {

    private static String TAG = "MainActivity";

    private MyAdapter<String> mAdapter;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_testactivity);
        mListView = (ListView)findViewById(R.id.listview);
        mAdapter = new MyAdapter<>(this, R.layout.layout_listitem);
        mListView.setAdapter(mAdapter);
        mAdapter.setData(TestData.getListString());
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "position:" + position + ",id:" + id);
                if (view.hasFocus()) {
                    view.setBackgroundColor(Color.RED);
                } else {
                    view.setBackgroundColor(Color.BLUE);
                }
            }
        });
        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.equals(MotionEvent.ACTION_BUTTON_RELEASE)) {
                    v.setBackgroundColor(Color.RED);
                }
                return false;
            }
        });
    }
}
