package com.visionin.ar;

/**
 * Created by wangzhiyuan on 2017/3/27.
 */

public class VisioninNativeImpl {

    static {
        System.loadLibrary("EasyAR");
        System.loadLibrary("HelloARNative");
    }

    public static native void nativeInitGL();
    public static native void nativeResizeGL(int w, int h);
    public static native void nativeRender();

    public static native boolean nativeInit();
    public static native void nativeDestory();
    public static native void nativeRotationChange(boolean portrait);
    public static native int getTargetStatus();

    //设置要识别的图片
    public static native void setPicture(String picture);
    //设置要播放的动画
    public static native void animationChange(int animation);

}
