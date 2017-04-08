package com.visionin.ar;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wangzhiyuan on 2017/3/29.
 */

public class TimeFunc implements IFunc{

    private static final String TAG = "TimeFunc";

    private Timer mTimer = null;
    private TimerTask mTimerTask = null;

    private static final int TIMEOUT = 1;

    public int count = 0;
    private static int period = 1000;
    private boolean isPause = false;

    private int timeout = 10;

    private Handler mHandler;
    private StatusChangedCallback mCallback;

    @Override
    public void start() {
        Looper looper = Looper.getMainLooper();
        mHandler = new Handler(looper);
        startTimer();
    }

    @Override
    public void stop() {
        stopTimer();
    }

    @Override
    public void setCallback(StatusChangedCallback callback) {
        mCallback = callback;
    }

    @Override
    public void setStrength(float strength) {

    }

    private void startTimer(){
        if (mTimer == null) {
            mTimer = new Timer();
        }

        if (mTimerTask == null) {
            mTimerTask = new TimerTask() {
                @Override
                public void run() {

                    do {
                        try {
                            Log.i(TAG, "sleep(1000)..."+count);
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                        }
                    } while (isPause);

                    count ++;

                    if(count == timeout) {
                        mHandler.post(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                mCallback.StatusChanged(1);
                            }
                        });
                        count = 0;
                    }
                }
            };
        }

        if(mTimer != null && mTimerTask != null )
            mTimer.schedule(mTimerTask, 0, period);

    }

    private void stopTimer(){

        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }

        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }

    }

}
