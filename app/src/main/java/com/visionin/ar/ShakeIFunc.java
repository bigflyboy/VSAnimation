package com.visionin.ar;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;

/**
 * Created by wangzhiyuan on 2017/3/27.
 */

public class ShakeIFunc implements IFunc {

    private SensorManager sensorManager;
    private Vibrator vibrator;
    private StatusChangedCallback mCallback;

    public void setmHandler(Handler mHandler) {
        this.mHandler = mHandler;
    }

    public Handler mHandler;

    private static final String TAG = "TestSensorActivity";
    public static final int SENSOR_SHAKE = 10;
    private boolean oneShake = true;

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (mCallback != null&&oneShake) {
                // 传感器信息改变时执行该方法
                float[] values = event.values;
                float x = values[0]; // x轴方向的重力加速度，向右为正
                float y = values[1]; // y轴方向的重力加速度，向前为正
                float z = values[2]; // z轴方向的重力加速度，向上为正
                //Log.i(TAG, "x轴方向的重力加速度" + x + "；y轴方向的重力加速度" + y + "；z轴方向的重力加速度" + z);
                // 一般在这三个方向的重力加速度达到40就达到了摇晃手机的状态。
                int medumValue = 20;
                if (Math.abs(x) > medumValue || Math.abs(y) > medumValue || Math.abs(z) > medumValue) {
                    vibrator.vibrate(200);
                    mCallback.StatusChanged(1);
                    oneShake = false;
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    public ShakeIFunc(SensorManager sm, Vibrator vr) {
        sensorManager = sm;
        vibrator = vr;
    }

    public ShakeIFunc(){

    }

    @Override
    public void start() {
        if (sensorManager != null) {// 注册监听器
            sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void stop() {
        if (sensorManager != null) {// 取消监听器
            sensorManager.unregisterListener(sensorEventListener);
        }
    }

    @Override
    public void setCallback(StatusChangedCallback callback) {
        mCallback = callback;
    }

    @Override
    public void setStrength(float strength) {

    }

}
