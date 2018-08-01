package com.example.zxz.aidlclienttest;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zxz.androidtest.service.ISumAidlInterface;
import com.yunos.tv.second.R;

/**
 * Created by xuezhi.zxz on 2015/12/30.
 */
public class MainActivity extends Activity {

    private String TAG = "Test";

    private TextView mTextview;
    private Button mButtonOK;
    private Button mButtonCancel;

    private boolean mBound = false;

    private int ans = 0;

    ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            mBound = true;
            ISumAidlInterface iSum = ISumAidlInterface.Stub.asInterface(service);
            try {
                ans = iSum.getSum(10, 20);
                mTextview.setText("ans:" + ans);
            } catch (RemoteException e) {
                ans = -1;
                mTextview.setText("ans:" + ans);
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected");
            mBound = false;
            ans = -2;
            mTextview.setText("ans:" + ans);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mainactivity);

        mTextview = (TextView)findViewById(R.id.textview);
        mButtonOK = (Button)findViewById(R.id.button_ok);
        mButtonCancel = (Button)findViewById(R.id.button_cancel);

        mButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.example.zxz.aidlservertest.sumservice");
                bindService(intent, mConn, BIND_AUTO_CREATE);
                mBound = true;
            }
        });

        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "unbindService1");
                if(mBound) {
                    unbindService(mConn);
                    mBound = false;
                }
                Log.d(TAG, "unbindService2");
            }
        });
    }
}
