package com.visionin.ar.Gyro;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by yuqiaomiao on 31/03/2017.
 */

public class VisioninG {

    private Context context;
    private VisioninGyroListener listener = null;

    private SensorManager mSensorManager;
    private Sensor gyroscope;

    public VisioninG(Context context, VisioninGyroListener listener) {
        this.context = context;
        this.listener = listener;

        mSensorManager = (SensorManager) this.context.getSystemService(Context.SENSOR_SERVICE);
        gyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        mSensorManager.registerListener(new GyroscopeEventListener(), gyroscope, Sensor.TYPE_GYROSCOPE);

    }

    private static final double NS2S = 1.0f / 1000000000.0f;
    private long timestamp;
    private float angle[] = {0.0f, 0.0f, 0.0f};

    private class GyroscopeEventListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {

            if (timestamp != 0) {
                // event.timesamp表示当前的时间，单位是纳秒（1百万分之一毫秒）
                final double dT = (event.timestamp - timestamp) * NS2S;
                angle[0] += (event.values[2] * dT);
                angle[1] += (event.values[0] * dT);
                angle[2] += (event.values[1] * dT);
            }

            float[] out = new float[3];
            out[0] = (float) Math.toDegrees(angle[0]);
            out[1] = (float) Math.toDegrees(angle[1]);
            out[2] = (float) Math.toDegrees(angle[2]);

            listener.onChange(-out[1], -out[0], out[2]);

            timestamp = event.timestamp;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }


    private int getPrecision(float precision) {
        if (precision <= 0.25f) {
            return SensorManager.SENSOR_DELAY_FASTEST;
        } else if (precision > 0.25f && precision <= 0.5f) {
            return SensorManager.SENSOR_DELAY_GAME;
        } else if (precision > 0.5f && precision <= 0.75f) {
            return SensorManager.SENSOR_DELAY_NORMAL;
        } else if (precision > 0.75f) {
            return SensorManager.SENSOR_DELAY_UI;
        }
        return SensorManager.SENSOR_DELAY_GAME;
    }

}
