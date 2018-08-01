package com.example.zxz.androidtest.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.HandlerThread;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.zxz.androidtest.R;
import com.example.zxz.androidtest.utils.ReflectUtils;
import com.example.zxz.androidtest.widget.TouchingView;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by xuezhi.zxz on 2016/12/16.
 */

public class TestMainActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = TestMainActivity.class.getSimpleName();

    private Notification mNotification = null;
    private NotificationManager mNotificationManager = null;
    private Button mBtnNotification = null;
    private TouchingView mTouchingView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_testmainactivity);
        mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        mBtnNotification = (Button)findViewById(R.id.btn_show_notification);
        findViewById(R.id.btn_show_recyclerview_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TestMainActivity.this, RecyclerViewActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_start_animation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                Animation animation = new ScaleAnimation(0,1,0,1, Animation.RELATIVE_TO_SELF, 0.5f,
                // Animation.RELATIVE_TO_PARENT, 0.5f);
                //                animation.setDuration(2000);
                //                mBtnNotification.startAnimation(animation);

                //                ObjectAnimator.ofFloat(mBtnNotification, "scaleX", 0.5f).start();
                /*ObjectAnimator animX = ObjectAnimator.ofFloat(mBtnNotification, "x", 50f);
                ObjectAnimator animY = ObjectAnimator.ofFloat(mBtnNotification, "y", 100f);
                AnimatorSet animSetXY = new AnimatorSet();
                animSetXY.playTogether(animX, animY);
                animSetXY.start();*/

                /*DisplayMetrics metrics = getResources().getDisplayMetrics();
                Toast.makeText(TestMainActivity.this, metrics.toString(), Toast.LENGTH_LONG).show();*/

                startSearch(null, false, null, false);


/*
                String dir = getApplicationInfo().nativeLibraryDir;
                Toast.makeText(TestMainActivity.this, dir, Toast.LENGTH_LONG).show();
                Toast.makeText(TestMainActivity.this, "显示测试的Toast", Toast.LENGTH_LONG).show();*/
                /*EyeProtectionManager manager = new EyeProtectionManager(TestMainActivity.this);
                int mode = manager.getEyeprotectionMode();
                Toast.makeText(TestMainActivity.this, "mode="+ mode, Toast.LENGTH_LONG).show();
                manager.setEyeprotectionMode(1-mode);*/
                //                switchQBaoEyeModel(TestMainActivity.this, mode != EyeProtectionManager.MODE_ON);

                //                openNetwork(TestMainActivity.this);

                //                mTouchingView.smoothScrollTo(-100, -50);
            }
        });

        findViewById(R.id.btn_show_viewpager_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TestMainActivity.this, ViewPagerActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_start_mediaplayer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TestMainActivity.this, MediaPlayerActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_show_event_test_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TestMainActivity.this, EventTestActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_start_float_window).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFloatWindow();
            }
        });

        findViewById(R.id.btn_show_custom_view_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TestMainActivity.this, CustomViewActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_start_lottie_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TestMainActivity.this, LottieTestActivity.class);
                startActivity(intent);
            }
        });

        mTouchingView = (TouchingView)findViewById(R.id.btn_touchingview);
        mBtnNotification.setOnClickListener(this);
        mBtnNotification.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "onTouch() called with: v = [" + v + "], event = [" + event + "]");
                return false;
            }
        });

        mTouchingView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "onTouch() called with: v = [" + v + "], event = [" + event + "]");
                return false;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState() called with: outState = [" + outState + "]");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState() called with: savedInstanceState = [" + savedInstanceState + "]");
    }

    @Override
    public boolean onSearchRequested() {
        return super.onSearchRequested();
    }

    public static boolean isQBaoWhiteBalanceEnable(Context context) {
        try {
            Class<?> newoneClass = Class.forName("com.droidlogic.app.EyeProtectionManager");
            Constructor<?>[] constructors = newoneClass.getConstructors();
            Object TvControlManager = constructors[0].newInstance(context);
            Object result = ReflectUtils.invokeMethod(TvControlManager, "getEyeprotectionMode", new Object[0]);
            Log.d(TAG, "QBao result is " + result);
            if (result instanceof Integer) {
                return (Integer)result == 1;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 海浪项目护眼模式打开方式
     *
     * @param open
     */
    private static void switchQBaoEyeModel(Context context, boolean open) {
        try {
            Class<?> newoneClass = Class.forName("com.droidlogic.app.EyeProtectionManager");
            Constructor<?>[] constructors = newoneClass.getConstructors();
            Object TvControlManager = constructors[0].newInstance(context);
            int mode = open ? 1 : 0;
            Method method = newoneClass.getMethod("setEyeprotectionMode", int.class);
            if (method != null) {
                method.invoke(TvControlManager, mode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_show_notification:
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto:110"));
                intent.putExtra("sms_body", "消息体");
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

                Notification.Builder builder = new Notification.Builder(this);
                builder.setContentText("测试内容")
                    .setSmallIcon(R.drawable.child_app_icon)
                    .setContentIntent(pendingIntent);

                int icon = R.drawable.child_app_icon;
                mNotification = new Notification();
                mNotification.icon = icon;
                mNotification.tickerText = "new TickerText";
                mNotification.defaults |= Notification.DEFAULT_SOUND;
                mNotification.flags = Notification.FLAG_AUTO_CANCEL;
                mNotificationManager.notify(0, builder.build());
                break;
            default:
                break;
        }
    }

    void sendToThread() {
        HandlerThread handlerThread = new HandlerThread("test-handlerthread");
        handlerThread.start();
    }

    void openNetwork(Context context) {
        try {
            Intent intent = new Intent("android.settings.NETWORK_SETTINGS");
            Bundle bnd = new Bundle();
            bnd.putString("FinishMode", "BACK");
            intent.putExtras(bnd);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e("Exception", ".native setnet error! e = " + e.getMessage());
            try {
                //跳转不到android.settings.NETWORK_SETTINGS直接跳转到settings,比如cvte
                Intent intent = new Intent("android.settings.SETTINGS");
                Bundle bnd = new Bundle();
                bnd.putString("FinishMode", "BACK");
                intent.putExtras(bnd);
                context.startActivity(intent);
            } catch (Exception e1) {
                Log.e("Exception", ".native setnet error! e = " + e1.getMessage());
                try {
                    Bundle pageBundle = new Bundle();
                    //							pageBundle.putString("url", "http://h5.waptest.taobao
                    // .com/yuntv/h5yingshi/detailvideo.html");
                    Method methodMeta = context.getClass().getMethod("startHostPage", String.class, String.class,
                        Bundle.class, boolean.class);
                    Log.d("Exception", ".showYunosHostPage.methodMeta = " + methodMeta);
                    if (methodMeta != null) {
                        methodMeta.invoke(context, "page://settingrelease.tv.yunos.com/settingrelease", null,
                            pageBundle, true);
                        Log.d("Exception", ".showYunosHostPage ok");
                    }
                } catch (Exception e2) {
                    Log.e("Exception", ".showYunosHostPage error! e1 = " + e1.getMessage());
                }
            }
        }
    }

    WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
        WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
        PixelFormat.TRANSLUCENT);

    int mStartX = -100;
    int mStartY = -100;

    void showFloatWindow() {
        if (!permission()) {
            return;
        }
        final WindowManager wm = getWindowManager();
        lp.gravity = Gravity.LEFT;
        lp.x = 0;
        lp.y = 0;
        final View layout = getLayoutInflater().inflate(R.layout.layout_float_window, null);
        Button btn = (Button)layout.findViewById(R.id.btn_float);
        //btn.setOnTouchListener(new View.OnTouchListener() {
        //    @Override
        //    public boolean onTouch(View v, MotionEvent event) {
        //        Log.d(TAG, "onTouch() called with: v = [" + v + "], event = [" + event + "]");
        //        Toast.makeText(TestMainActivity.this, "点击悬浮框", Toast.LENGTH_LONG).show();
        //
        //        return false;
        //    }
        //});

        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mStartX < 0 && mStartY < 0) {
                            mStartX = (int)event.getRawX();
                            mStartY = (int)event.getRawY();
                            Log.d(TAG, "onTouch() called with: lp.x = [" + lp.x + "], lp.y = [" + lp.y + "]");
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //Log.d(TAG, "onTouch() called with: x = [" + event.getRawX() + "], y = [" + event.getRawY()
                        // + "]");
                        lp.x = (int)event.getRawX() - mStartX;
                        lp.y = (int)event.getRawY() - mStartY;
                        wm.updateViewLayout(layout, lp);
                        break;
                }

                return false;
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick() called with: v = [" + v + "]");
                Toast.makeText(TestMainActivity.this, "点击悬浮框", Toast.LENGTH_LONG).show();
                //AlertDialog.Builder builder = new AlertDialog.Builder(TestMainActivity.this);
                //AlertDialog dialog = builder.setTitle("弹出的框")
                //    .create();
                //dialog.show();
                wm.removeView(layout);
            }
        });
        wm.addView(layout, lp);
    }

    public boolean permission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                startActivity(intent);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
}
