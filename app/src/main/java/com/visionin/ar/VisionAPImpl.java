package com.visionin.ar;

/**
 * Created by wangzhiyuan on 2017/3/29.
 */

public class VisionAPImpl implements IVisionAPI{

    //功能详见FuncImpl
    private BlewIFunc mBlewFunc;
    private AnimationIFunc mAnimationFunc;
    private MonitorIFunc mMonitorFunc;
    private ShakeIFunc mShakeFunc;
    private TimeFunc mTimeFunc;

    //获取吹气识别功能
    @Override
    public IFunc getBlewFunc() {
        if(mBlewFunc == null){
            mBlewFunc = new BlewIFunc();
        }
        return mBlewFunc;
    }
    //获取动画播放功能
    @Override
    public IFunc getAnimationFunc() {
        if(mAnimationFunc == null){
            //mAnimationFunc = new AnimationIFunc();
        }
        return mAnimationFunc;
    }
    //获取识别图片功能
    @Override
    public IFunc getMonitorFunc() {
        if(mMonitorFunc == null){
            mMonitorFunc = new MonitorIFunc();
        }
        return mMonitorFunc;
    }
    //获取摇一摇识别功能
    @Override
    public IFunc getShakeFunc() {
        if(mShakeFunc == null){
            mShakeFunc = new ShakeIFunc();
        }
        return mShakeFunc;
    }

    @Override
    public IFunc getTimeFunc() {
        if(mTimeFunc == null){
            mTimeFunc = new TimeFunc();
        }
        return mTimeFunc;
    }

    //初始化
    @Override
    public void init(){

    }

    //结束释放资源
    @Override
    public void destory(){

    }

}
