package com.visionin.gpu;

import android.graphics.Bitmap;
import android.view.Surface;

import com.rex.load.NativeLoad;

public class GPU {
	static {
		long so = NativeLoad.loadSo("libvisionin.so");

        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "createTexture", "()I");
        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "destroyTexture", "(I)V");

        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "eglContext", "(Landroid/view/Surface;)J");
        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "makeCurrent", "(J)V");
        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "destroy", "(J)V");

        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "processTexture", "(I)V");
        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "processBytes", "([BIII)V");
        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "getBytes", "([B)V");
        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "getTexture", "()I");

        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "setOutputSize", "(II)V");
        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "setOutputFormat", "(I)V");

        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "setInputSize", "(II)V");
        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "setInputRotation", "(I)V");

        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "setPreviewMirror", "(Z)V");
        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "setOutputMirror", "(Z)V");

        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "setSmoothLevel", "(F)V");
        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "setBrightenLevel", "(F)V");
        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "setToningLevel", "(F)V");

        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "setExtraFilter", "(Ljava/lang/String;)V");
        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "closeExtraFilter", "()V");
        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "setExtraParameter", "(F)V");

        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "setOutputView", "()V");
        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "removeOutputView", "()V");

        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "setPreviewRotation", "(I)V");
        ///
        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "startAnimation", "()V");
        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "stopAnimation", "()V");
        ////////////////////
        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "getObjHead", "()I");
        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "appendObj", "(III)Z");
        ///
        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "AddPicture", "(I[III)Z");
        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "InitObj", "(III)Z");
        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "activeObj", "(I)Z");
        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "disableObj", "(I)Z");
        ///
        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "clearObj", "()V");
        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "deleteObj", "(I)Z");

        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "addBitmap", "(ILandroid/graphics/Bitmap;)V");

        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "startARTracking", "()V");
        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "stopARTracking", "()V");
        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "getTrackStat", "()F");

        ////////////////////////////////////AR动画/////////////////////////////////////

        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "startARMode", "()V");
        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "stopARMode", "()V");

        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "init", "(IIIIIIII)V");
        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "setAngle", "(FFF)V");
        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "draw", "()V");

        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "catchObj", "()I");
        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "setRedObj", "(III)I");
        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "setYellowObj", "(III)I");

        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "setOrengeObj", "(III)I");
        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "activeARAnimations", "()V");
        NativeLoad.registJNIMethod(so, "com/visionin/gpu/GPU", "disableARAnimations", "()V");
	}

    /// SurfaceTexture相关
    protected static native int createTexture();
    protected static native void destroyTexture(int texture);
    /// EGLContext
    protected native long eglContext(Surface surface);
    protected native void makeCurrent(long context);
    protected native void destroy(long context);
    /// c处理
    protected native void processTexture(int texture);
    protected native void processBytes(byte[] bytes, int width, int height, int format);
    public native void getBytes(byte[] bytes);

    public native int getTexture();
    /// 输出
    protected native void setOutputSize(int width, int height);
    protected native void setOutputFormat(int format);
    /// 输入
    protected native void setInputSize(int width, int height);
    public native void setInputRotation(int rotation);
    /// 镜像
    public native void setPreviewMirror(boolean mirror);
    public native void setOutputMirror(boolean mirror);

    /// 美颜
    public native void setSmoothLevel(float level);
    public native void setBrightenLevel(float level);
    public native void setToningLevel(float level);
    /// 滤镜
    public native void setExtraFilter(String filter);
    public native void closeExtraFilter();
    public native void setExtraParameter(float para);
    /// 预览
    public native void setOutputView();
    public native void removeOutputView();

    public native void setPreviewRotation(int rotation);

    /*****************************动画添加*****************************/

    public native void startAnimation();

    public native void stopAnimation();

    public native int getObjHead();

    public native boolean appendObj(int picNums, int frameSpeed, int category);

    public native boolean AddPicture(int objId, int[] data, int width, int height);

    public native boolean InitObj(int objId, int picNums, int category);

    public native boolean activeObj(int objId);

    public native boolean disableObj(int objId);

    public native void clearObj();

    public native boolean deleteObj(int objId);

    public native void addBitmap(int mId, Bitmap bitmap);

    public native void startARTracking();

    public native void stopARTracking();

    public native float getTrackStat();

    /*****************************AR动画添加*****************************/

    public native void startARMode();

    public native void stopARMode();

    public native void init(int kind, int count, int canvasWidth, int canvasHeight, int screenWidth, int screenHeight, int limitWidthAngle, int limitHeightAngle);

    public native void setAngle(float x, float y, float z);

    public native void draw();

    public native int catchObj();

    public native int setRedObj(int pic_num, int frame_speed, int category);

    public native int setYellowObj(int pic_num, int frame_speed, int category);

    public native int setOrengeObj(int pic_num, int frame_speed, int category);

    public native void activeARAnimations();

    public native void disableARAnimations();

    /*****************************AR动画添加*****************************/

    protected long  mEGLContext = 0;
    protected int   mProcessMode = 0;
    protected static boolean init = false;

    protected GPU(Surface surface) throws Exception {
        mEGLContext = eglContext(surface);
        if (mEGLContext==0){
            throw new Exception("GLContext create Error!");
        }
    }
    protected GPU(Surface surface, int mode){
        mProcessMode = mode;
        mEGLContext = eglContext(surface);
        if (mEGLContext==0){
        }
    }

    public void makeCurrent(){
        if (mEGLContext!=0) {
            makeCurrent(mEGLContext);
        }
    }
}