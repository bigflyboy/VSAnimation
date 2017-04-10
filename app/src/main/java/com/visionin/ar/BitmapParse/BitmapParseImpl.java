package com.visionin.ar.BitmapParse;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wangzhiyuan on 2017/3/30.
 */

public class BitmapParseImpl implements IBitmapParse {

    private static final String TAG = "BitmapParseImpl";

    public Bitmap mBitmap;
    BitmapFactory.Options mOptions;

    Context mContext;
    private int mFrameNums;
    public static int pictureId = -1;
    public AssetManager mManager;
    InputStream mInputStream;
    String mPath,mPictureName;

    public void setStride(int mStride) {
        this.mStride = mStride;
    }

    private int mStride = 1;

    public BitmapParseImpl(Context context, AssetManager manager){
        mContext = context;
        mManager = manager;

    }

    //设置要播放的动画，帧数和选项  返回实际的帧数
    @Override
    public int setAnimation(String name, int frameNums, int option) {
        mOptions = new BitmapFactory.Options();
        mOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
        mFrameNums = frameNums;
        mPictureName = name;
        return 0;
    }

    //获取指定id图片的RGBA32字节数组，图片不存在返回空
    @Override
    public BitmapStruct getPicture(int id) {
        mPath = getPath(id);
        try {
            mInputStream = mManager.open(mPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //mBitmap = BitmapFactory.decodeFile("assets/animation.PuGongYi_Seed_V01test/PuGongYi_Seed_V01_00000.png", options);
        mBitmap = BitmapFactory.decodeStream(mInputStream, null, mOptions);

        BitmapStruct mBitmapStruct = new BitmapStruct();

        mBitmapStruct.width = mBitmap.getWidth();
        mBitmapStruct.height = mBitmap.getHeight();

        mBitmapStruct.data = new int[mBitmapStruct.width*mBitmapStruct.height];
        mBitmap.getPixels(mBitmapStruct.data, 0, mBitmapStruct.width, 0, 0, mBitmapStruct.width, mBitmapStruct.height);

        if(mBitmap == null){
            Log.e(TAG, "bitmap为null");
        }

        Log.e(TAG, mBitmapStruct.data.length + "  " + id);

        return mBitmapStruct;
    }

    public Bitmap getBitmap(int id){
        mPath = getPath(id);
        try {
            mInputStream = mManager.open(mPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //mBitmap = BitmapFactory.decodeFile("assets/animation.PuGongYi_Seed_V01test/PuGongYi_Seed_V01_00000.png", options);
        mBitmap = BitmapFactory.decodeStream(mInputStream, null, mOptions);

        return mBitmap;
    }

    public Bitmap getNextBitmap(){
        pictureId += mStride;
        return getBitmap(pictureId);
    }

    //获取下一张图片，图片不存在返回空，第一次调用返回第一张
    @Override
    public BitmapStruct getNextPicture() {
        pictureId++;
        return getPicture(pictureId);
    }

    public String getPath(int id){
        if(id>mFrameNums&&id<0){
            return null;
        }
        if(id<10){
            return "GMAnimation/" + mPictureName + "/" +mPictureName + "_0000" + id + ".png";
        }else if(id>9&&id<100){
            return "GMAnimation/" + mPictureName + "/" +mPictureName + "_000" + id + ".png";
        }else if(id>99&&id<1000){
            return "GMAnimation/" + mPictureName + "/" +mPictureName + "_00" + id + ".png";
        }else{
            return "GMAnimation/" + mPictureName + "/" +mPictureName + "_0" + id + ".png";
        }
    }

}
