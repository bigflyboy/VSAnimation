package com.visionin.ar;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

/**
 * Created by fangming.fm on 2017/3/16.
 */

public class RecordThread extends Thread {

    private AudioRecord mAudioRecord;
    private Handler mHandler;

    private StatusChangedCallback mCallback;

    private static final int BUFFER_SIZE = 11025;
    private static int MIN_VALUE = 3000;

    private int bufferSize = 100;

    private boolean isConfiged = false;

    public RecordThread(StatusChangedCallback callback) {
        mCallback = callback;
        Looper looper = Looper.getMainLooper();
        mHandler = new Handler(looper);
        bufferSize = AudioRecord.getMinBufferSize(
                BUFFER_SIZE,
                AudioFormat.CHANNEL_IN_DEFAULT,
                AudioFormat.ENCODING_PCM_16BIT);
        mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                BUFFER_SIZE,
                AudioFormat.CHANNEL_IN_DEFAULT,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferSize);
    }


    @Override
    public void run() {
        // TODO Auto-generated method stub
        super.run();
        mAudioRecord.startRecording();
        byte[] buffer = new byte[bufferSize];
        int sum = 0;
        int configTimes = 0;
        int checkTimes = 0;
        while (BlewIFunc.isRunning) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (!isConfiged) {
                configTimes++;
                int length = mAudioRecord.read(buffer, 0, bufferSize) + 1;
                int av = 0;
                for (int i = 0; i < buffer.length; i++) {
                    av += (buffer[i] * buffer[i]);
                }
                av = Integer.valueOf(av / (int) length);
                if(av < 200){
                    configTimes--;
                    continue;
                }
                sum += av;
                if (configTimes > 10) {
                    MIN_VALUE = (int)((sum / configTimes + 100) * BlewIFunc.strengt * 2);
                    isConfiged = true;
                    Log.e("value=", String.valueOf(MIN_VALUE));
                }
                //Log.e("av=", String.valueOf(av));
            } else {
                int length = mAudioRecord.read(buffer, 0, bufferSize) + 1;
                int av = 0;
                for (int i = 0; i < buffer.length; i++) {
                    av += (buffer[i] * buffer[i]);
                }
                av = Integer.valueOf(av / (int) length);

//            double mean = av / (double) length;
//            double volume = 10 * Math.log10(mean);
                //Log.e("av=", String.valueOf(av));
                if (av > MIN_VALUE) {
                    //mHandler.sendEmptyMessage(1);
                    checkTimes++;
                    if (checkTimes > 3)
                        mHandler.post(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                mCallback.StatusChanged(1);
                            }
                        });
                } else {
                    checkTimes = 0;
                }
            }
        }
        mAudioRecord.stop();
        mAudioRecord.release();
    }

    public interface ChangeState {
        public void changeOpen();

        public void changeClose();
    }

}
