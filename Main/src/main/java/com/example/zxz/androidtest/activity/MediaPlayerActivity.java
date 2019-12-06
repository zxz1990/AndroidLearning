package com.example.zxz.androidtest.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.zxz.androidtest.R;

/**
 * Created by xuezhi.zxz on 2017/5/27.
 */

public class MediaPlayerActivity extends BaseActivity {

    private VideoView mVideoView;
    private MediaController mMediaController;
    private boolean mIsActivated;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mediaplayer_activity);
        mIsActivated = true;
        initVideoView();
        initButton();
    }

    void initVideoView() {
        mVideoView = (VideoView)findViewById(R.id.video_view);
        mMediaController = new MediaController(MediaPlayerActivity.this);
        mVideoView.setMediaController(mMediaController);
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d(TAG, "onCompletion() called with: mp = [" + mp + "]");
            }
        });

        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.d(TAG, "onError() called with: mp = [" + mp + "], what = [" + what + "], extra = [" + extra + "]");
                return false;
            }
        });

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.d(TAG, "onPrepared() called with: mp = [" + mp + "]");
            }
        });

        mVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {

            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                Log.d(TAG, "onInfo() called with: mp = [" + mp + "], what = [" + what + "], extra = [" + extra + "]");
                return false;
            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {
                while(mIsActivated) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (mVideoView != null) {
                        Log.d(TAG, "run() called:" + mVideoView.getBufferPercentage());
                    }
                }
            }
        }).start();
    }

    void initButton() {
        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoView.setVideoPath("http://183.131.210.57/v.cctv.com/flash/mp4video6/TMS/2011/01/05/cf752b1c12ce452b3040cab2f90bc265_h264818000nero_aac32-1.mp4");
                mVideoView.start();
            }
        });

        findViewById(R.id.pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoView.pause();
                Log.d(TAG, "onClick() called with: v = [" + v + "]" + " position="+mVideoView.getCurrentPosition());
            }
        });

        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoView.stopPlayback();
            }
        });

        findViewById(R.id.resume).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoView.start();
            }
        });

        findViewById(R.id.seek).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoView.seekTo(50000);
                mVideoView.getDuration();
//                mVideoView.start();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIsActivated = false;
    }
}
