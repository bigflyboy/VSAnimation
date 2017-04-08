package com.visionin.core;

import android.content.Context;

import com.rex.load.NativeLoad;
import com.rex.utils.DeviceUtil;
import com.rex.utils.FileUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Visionin on 16/9/10.
 */
public class VSFacer {
    static {
        long so = NativeLoad.loadSo("libvisionin.so");
        NativeLoad.registJNIMethod(so, "com/visionin/core/VSFacer", "loadFacerBuffer", "(Ljava/lang/String;[B)Z");
        NativeLoad.registJNIMethod(so, "com/visionin/core/VSFacer", "loadFacerPath", "(Ljava/lang/String;Ljava/lang/String;)Z");
        NativeLoad.registJNIMethod(so, "com/visionin/core/VSFacer", "destroyFacer", "()V");

        NativeLoad.registJNIMethod(so, "com/visionin/core/VSFacer", "startFacerTracking", "()V");
        NativeLoad.registJNIMethod(so, "com/visionin/core/VSFacer", "stopFacerTracking", "()V");
        NativeLoad.registJNIMethod(so, "com/visionin/core/VSFacer", "isFacerTracking", "()Z");

//        NativeLoad.registJNIMethod(so, "com/visionin/core/VSFacer", "startFacerShaper", "()V");
//        NativeLoad.registJNIMethod(so, "com/visionin/core/VSFacer", "stopFacerShaper", "()V");
//        NativeLoad.registJNIMethod(so, "com/visionin/core/VSFacer", "setShaping", "(IF)V");
//
//        NativeLoad.registJNIMethod(so, "com/visionin/core/VSFacer", "setMarker", "(Z)V");
//        NativeLoad.registJNIMethod(so, "com/visionin/core/VSFacer", "getFacerMarks", "()[F");
    }

    public static boolean initialize(Context context){
        InputStream is = null;
        try {
            is = context.getResources().getAssets().open("face_track_2.0.1.model");
            OutputStream os = FileUtil.fileOutputStream("/data/data/"+ DeviceUtil.getPackageName(context) +"/face_track_2.0.1.model");
            //OutputStream os = context.openFileOutput("face_track_2.0.1.model", Activity.MODE_PRIVATE);
            FileUtil.write(os, is);

            is = context.getResources().getAssets().open("SenseME.lic");
            os = FileUtil.fileOutputStream("/data/data/"+ DeviceUtil.getPackageName(context) +"/SenseME.lic");
            FileUtil.write(os, is);

            return loadFacerPath("/data/data/"+ DeviceUtil.getPackageName(context) +"/face_track_2.0.1.model", "/data/data/"+ DeviceUtil.getPackageName(context) +"/SenseME.lic");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean initialize(String model, String lisence){
        return loadFacerPath(model, lisence);
    }

    protected static native boolean loadFacerBuffer(String path, byte[] lisence);
    protected static native boolean loadFacerPath(String path, String lisence);
    public static native void destroyFacer();

    public static native void startFacerTracking();
    public static native void stopFacerTracking();
    public static native boolean isFacerTracking();

//    public native void startFacerShaper();
//    public native void stopFacerShaper();
//    public native void setShaping(int cmd, float strength);
//
//    public native void setMarker(boolean flag);
//    public native float[] getFacerMarks();
}
