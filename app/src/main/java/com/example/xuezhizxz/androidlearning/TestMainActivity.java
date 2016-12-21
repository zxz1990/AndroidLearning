package com.example.xuezhizxz.androidlearning;

        import android.app.Notification;
        import android.app.NotificationManager;
        import android.app.PendingIntent;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;

/**
 * Created by xuezhi.zxz on 2016/12/16.
 */

public class TestMainActivity extends BaseActivity implements View.OnClickListener{

    private Notification mNotification = null;
    private NotificationManager mNotificationManager = null;
    private Button mBtnNotification = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_testmainactivity);
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        mBtnNotification = (Button) findViewById(R.id.btn_show_notification);
        mBtnNotification.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_show_notification:
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto:18969186090"));
                intent.putExtra("sms_body", "消息体");
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                Notification.Builder builder = new Notification.Builder(this);
                builder.setContentText("测试内容")
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentIntent(pendingIntent);

                int icon = R.drawable.ic_launcher;
                mNotification = new Notification();
                mNotification.icon = icon;
                mNotification.tickerText = "new TickerText";
                mNotification.defaults |= Notification.DEFAULT_SOUND;
                mNotification.flags = Notification.FLAG_AUTO_CANCEL;
                mNotificationManager.notify(0,builder.build());
                break;
            default:
                break;
        }
    }
}
