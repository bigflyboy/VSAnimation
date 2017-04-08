package com.visionin.ar;

/**
 * Created by wangzhiyuan on 2017/3/27.
 */

public class BlewIFunc implements IFunc {

    public static boolean isRunning = false;
    public static float strengt = 0.5f;

    public BlewIFunc(){

    }

    public void setBlewCallback(RecordThread.ChangeState mCallback) {
        //this.mCallback = mCallback;
    }

    private StatusChangedCallback mCallback;

    @Override
    public void start(){
        if(!isRunning){
            new RecordThread(mCallback).start();
            isRunning = !isRunning;
        }
    }

    @Override
    public void stop(){
        if(isRunning){
            RecordThread.interrupted();
            isRunning = !isRunning;
        }
    }

    @Override
    public void setCallback(StatusChangedCallback callback) {
        mCallback = callback;
    }

    @Override
    public void setStrength(float strength) {
        strengt = strength;
    }

}
