package com.visionin.ar;

/**
 * Created by wangzhiyuan on 2017/3/29.
 */

public interface IVisionAPI {

    public static final int ANIMATION_PLAYONCE = 0;//动画停止在最后一帧
    public static final int ANIMATION_REPEATABLE = 1;//动画重复播放

    //获取吹气识别功能
    public IFunc getBlewFunc();

    //获取动画播放功能
    public IFunc getAnimationFunc();

    //获取识别图片功能,默认是开启的
    public IFunc getMonitorFunc();

    //获取摇一摇识别功能
    public IFunc getShakeFunc();

    //获取计时器功能
    public IFunc getTimeFunc();

    //初始化
    public void init();

    //结束释放资源
    public void destory();

}
