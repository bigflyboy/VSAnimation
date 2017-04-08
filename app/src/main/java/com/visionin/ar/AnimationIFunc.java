package com.visionin.ar;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.visionin.ar.BitmapParse.AsyncBitmapParse;


/**
 * Created by wangzhiyuan on 2017/3/29.
 */

public class AnimationIFunc implements IFunc {

    AsyncBitmapParse mAsyncParse;

    Context mContext;
    AssetManager mManager;
    public int objId;

    private static final String TAG = "AnimationIFunc";

    public AnimationIFunc(Context context, AssetManager manager){
        mContext = context;
        mManager = manager;
    }

    @Override
    public void start() {
        mAsyncParse.startParse();
    }

    @Override
    public void stop() {
        mAsyncParse.stopParse();
    }

    @Override
    public void setCallback(StatusChangedCallback callback) {

    }

    @Override
    public void setStrength(float strength) {

    }

    //设置要播放的简单动画，帧数和播放方式
    public void setAnimation(String animation, int frameNums, int option){
        Log.e(TAG, "setAnimation" + animation);
//        mAsyncParse = new AsyncBitmapParse(mContext, mManager);
//        mAsyncParse.setAnimation(animation, frameNums,option);
//        objId = IAnimationNative.getObjHead();
//        mAsyncParse.setObjectId(objId);
//        IAnimationNative.appendObj(frameNums, 1, 0);
    }

    //设置要播放的AR动画
    public void setARAnimation(String arAnimation){

    }

}
