package com.visionin.ar;

/**
 * Created by wangzhiyuan on 2017/3/29.
 */

public interface IFunc {
    //开始 结束 回调
    //吹气功能 摇一摇功能 播放动画
    public void start();
    public void stop();
    //设置回调 检测到吹气，摇一摇，识别到图片和动画结束
    public void setCallback(StatusChangedCallback callback);
    //设置识别精度 数值范围0～1 默认0.5
    public void setStrength(float strength);
}


