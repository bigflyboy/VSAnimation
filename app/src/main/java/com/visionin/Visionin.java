package com.visionin;

import android.content.Context;
import android.content.res.AssetManager;

import com.rex.load.NativeLoad;

/**
 * Created by Rex on 16/6/2.
 */
public class Visionin {
    public static String TAG = "visionin";
    private static boolean initFlag = false;
    public static String mAppId,mAppKey;

    static{
        long so = NativeLoad.loadSo("libvisionin.so");
        NativeLoad.registJNIMethod(so, "com/visionin/Visionin", "authorization", "(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V");
        NativeLoad.registJNIMethod(so, "com/visionin/Visionin", "resource", "(Landroid/content/res/AssetManager;)V");
    }

    public static void initialize(Context context, String appId, String appKey){
        authorization(context, appId, appKey);
        resource(context.getAssets());
    }

    public static native void authorization(Context context, String appId, String appKey);
    public static native void resource(AssetManager asset);
}
