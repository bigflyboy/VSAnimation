package com.visionin.ar.BitmapParse;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by wangzhiyuan on 2017/3/31.
 */

public class AsyncBitmapParse implements IAsyncBitmapParse{

    private BitmapParseThread mParseThread;
    private static final String TAG = "AsyncBitmapParse";

    public Bitmap mBitmap;
    BitmapFactory.Options mOptions;
    BitmapStruct mBitmapStruct;
    Context mContext;
    private int pictureId = 0, mFrameNums;
    public AssetManager mManager;
    InputStream mInputStream;
    String mPath,mPictureName;
    private boolean isRunning = false;
    public int mObjectId;

    private static int bitmapSize = 4;

    public static ArrayBlockingQueue<BitmapStruct> mBitmapQueue = new ArrayBlockingQueue<BitmapStruct>(bitmapSize);

    public AsyncBitmapParse(Context context, AssetManager manager) {
        mContext = context;
        mManager = manager;
        mBitmapStruct = new BitmapStruct();
    }

    @Override
    public int setAnimation(String name, int frameNums, int option) {
        mOptions = new BitmapFactory.Options();
        mOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
        mFrameNums = frameNums;
        mPictureName = name;
        return 0;
    }

    @Override
    public BitmapStruct getPicture(int id) {
        pictureId = id;
        mPath = getPath(id);
        try {
            mInputStream = mManager.open(mPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //mBitmap = BitmapFactory.decodeFile("assets/animation.PuGongYi_Seed_V01test/PuGongYi_Seed_V01_00000.png", options);
        mBitmap = BitmapFactory.decodeStream(mInputStream, null, mOptions);

        mBitmapStruct.width = mBitmap.getWidth();
        mBitmapStruct.height = mBitmap.getHeight();

        mBitmapStruct.data = new int[mBitmapStruct.width*mBitmapStruct.height];
        mBitmap.getPixels(mBitmapStruct.data, 0, mBitmapStruct.width, 0, 0, mBitmapStruct.width, mBitmapStruct.height);

        if(mBitmap == null){
            Log.e(TAG, "bitmap为null");
        }

        return mBitmapStruct;
    }

    @Override
    public BitmapStruct getNextPicture() {
        return mBitmapQueue.poll();
    }

    @Override
    public void startParse() {
        isRunning = true;
        mParseThread = new BitmapParseThread();
        mParseThread.start();
    }

    @Override
    public void stopParse() {
        isRunning = false;
    }

    @Override
    public void setPictureCache(int cacheNums) {

    }

    @Override
    public void commit() {

    }

    private class BitmapParseThread extends Thread{

        @Override
        public void run() {
            while(isRunning){
                BitmapStruct bitmapStruct = getPicture(pictureId);
                Log.e(TAG, "解析图片id" + pictureId);
                pictureId++;
                try {
                    mBitmapQueue.put(bitmapStruct);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getPath(int id){
        if(id>=mFrameNums&&id<0){
            return null;
        }
        if(id<10){
            return "animation/" + mPictureName + "/PuGongYi_Seed_V01_0000" + id + ".png";
        }else if(id>9&&id<100){
            return "animation/" + mPictureName + "/PuGongYi_Seed_V01_000" + id + ".png";
        }else if(id>99&&id<1000){
            return "animation/" + mPictureName + "/PuGongYi_Seed_V01_00" + id + ".png";
        }else{
            return "animation/" + mPictureName + "/PuGongYi_Seed_V01_0" + id + ".png";
        }
    }

    public void setObjectId(int id){
        mObjectId = id;
    }

}
