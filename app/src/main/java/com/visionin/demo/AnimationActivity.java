package com.visionin.demo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.visionin.ar.BitmapParse.BitmapParseImpl;
import com.visionin.ar.BlewIFunc;
import com.visionin.ar.ShakeIFunc;
import com.visionin.ar.StatusChangedCallback;
import com.visionin.core.VSVideoFrame;
import com.visionin.samples.helloar.R;

import java.io.IOException;

/**
 * Created by Visionin on 16/7/26.
 */
public class AnimationActivity extends Activity implements SurfaceHolder.Callback {
    private static final String TAG = "Activity";

    private SurfaceView surfaceView = null;
    private ImageView imageView = null;
    private Camera mCamera = null;
    private VSVideoFrame videoFrame = null;
    private Button switchCamera = null;
    private EditText pitchTones = null;
    SurfaceHolder surfaceHolder = null;
    Camera.Size videoSize;
    private int mPosition = 0;
    public int mId;
    BitmapParseImpl bitmapParse;
    public boolean isStartAnimation = false;


    private ShakeIFunc mShakeFunc;
    private BlewIFunc mBlewFunc;

    private int states = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Visionin.initialize(this, "f1a87ce5ae57f1e2484283419c3ef277", "e1f638ec0d52ac96b5e3fc8242813df7");

        setContentView(R.layout.animation);


        surfaceView = (SurfaceView) findViewById(R.id.camera_surfaceView);
        surfaceHolder = surfaceView.getHolder();


    }


    @Override
    protected void onResume() {
        Log.d(TAG, "onResume -- acquiring camera");
        super.onResume();
        videoSize = openCamera(1920, 1080);
        surfaceHolder.addCallback(this);
        Log.d(TAG, "onResume complete: " + this);
        startShake();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause -- releasing camera");
        releaseCamera();
        super.onPause();
        Log.d(TAG, "onPause complete");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Opens a camera, and attempts to establish preview mode at the specified width and height.
     * <p>
     * Sets mCameraPreviewWidth and mCameraPreviewHeight to the actual width/height of the preview.
     */
    private Camera.Size openCamera(int desiredWidth, int desiredHeight) {
        if (mCamera != null) {
            throw new RuntimeException("camera already initialized");
        }

        mPosition = Camera.CameraInfo.CAMERA_FACING_BACK;
        mCamera = Camera.open(mPosition);

        Camera.Parameters parms = mCamera.getParameters();
        Camera.Size ppsfv = parms.getPreferredPreviewSizeForVideo();
        if (ppsfv != null) {
            Log.e(TAG, "Camera preferred preview size for video is " +
                    ppsfv.width + "x" + ppsfv.height);
            parms.setPreviewSize(ppsfv.width, ppsfv.height);
        }

        for (Camera.Size size : parms.getSupportedPreviewSizes()) {
            if (size.width == desiredWidth && size.height == desiredHeight) {
                parms.setPreviewSize(desiredWidth, desiredHeight);
                break;
            }
        }

        parms.setRecordingHint(true);
        mCamera.setParameters(parms);

        //没用
        //mCamera.setDisplayOrientation(180);

        int[] fpsRange = new int[2];
        parms.getPreviewFpsRange(fpsRange);
        return parms.getPreviewSize();
    }

    /**
     * Stops camera preview, and releases the camera to the system.
     */
    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
            Log.d(TAG, "releaseCamera -- done");
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "surfaceCreated!");
        this.surfaceHolder = surfaceHolder;
        try {
            videoFrame = new VSVideoFrame(surfaceHolder.getSurface());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        videoFrame.setCameraPosition(VSVideoFrame.CAMERA_FACING_FRONT);

        //设置推流视频镜像
        //videoFrame.setMirrorBackVideo(false);
        //设置预览镜像，true时预览为镜像（左右颠倒），false时为非镜像(正常画面)
        //videoFrame.setMirrorFrontPreview(true);
        //videoFrame.setMirrorBackPreview(true);
        videoFrame.setVideoSize(videoSize.width, videoSize.height);
        //设置滤镜，获取美颜数据.
        videoFrame.setInputRotation(2);
        videoFrame.setPreviewRotation(3);
        videoFrame.start();


        try {
            mCamera.setPreviewTexture(videoFrame.surfaceTexture());
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }


        videoFrame.startAnimation();
        mId = videoFrame.getObjHead();
        videoFrame.appendObj(30, 1, 3);

        videoFrame.activeObj(mId);
        Log.e("ray","mid = "+mId);
        bitmapParse = new BitmapParseImpl(getApplicationContext(), getAssets());
        bitmapParse.setAnimation("PuGongYi_Seed_V01", 1, 0);
        for (int i = 30; i > 0; i--) {
            Log.e("ray","i = "+i);
            Bitmap bitmap = bitmapParse.getNextBitmap();
            videoFrame.addBitmap(mId, bitmap);
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        videoFrame.stop();
        videoFrame.destroy();
        videoFrame = null;
    }

    public void startAnimation(View v) {
        //Log.e(TAG, "startAnimation!");
//        if (states == 0) {
//            startShake();
//            states++;
//        } else if (states == 1) {
//            startBlew();
//            states++;
//        } else
        if (states == 2) {
            startHongBao();
            states++;
        }
//        } else if (states == 3) {
//            BitmapParseImpl.pictureId = -1;
//            videoFrame.deleteObj(mId);
//            videoFrame.stopAnimation();
//            isStartAnimation = false;
//            states++;
//        }

    }

    private void startShake() {
        //Toast.makeText(AnimationActivity.this, "开启震动监听事件", Toast.LENGTH_SHORT).show();
        mShakeFunc = new ShakeIFunc((SensorManager) getSystemService(SENSOR_SERVICE), (Vibrator) getSystemService(VIBRATOR_SERVICE));
        //mShakeFunc.setmHandler(mHandler);
        mShakeFunc.setCallback(new StatusChangedCallback() {
            @Override
            public void StatusChanged(int status) {
                //Toast.makeText(AnimationActivity.this, "检测到摇晃，蒲公英长大！关闭监听震动事件！", Toast.LENGTH_SHORT).show();
                mShakeFunc.stop();

                BitmapParseImpl.pictureId = -1;
                videoFrame.deleteObj(mId);
                videoFrame.stopAnimation();
                //isStartAnimation = false;

                videoFrame.startAnimation();
                mId = videoFrame.getObjHead();
                videoFrame.appendObj(30, 1, 3);

                videoFrame.activeObj(mId);
                bitmapParse = new BitmapParseImpl(getApplicationContext(), getAssets());
                bitmapParse.setAnimation("GetTheDandelion_P", 1, 2);
                for (int i = 30; i > 0; i--) {
                    Bitmap bitmap = bitmapParse.getNextBitmap();
                    videoFrame.addBitmap(mId, bitmap);
                }
                //isStartAnimation = true;
                startBlew();
            }
        });
        mShakeFunc.start();
    }

    private void startBlew() {
        Toast.makeText(AnimationActivity.this, "开始吹气检测", Toast.LENGTH_SHORT).show();
        mBlewFunc = new BlewIFunc();
        mBlewFunc.setStrength(0.7f);
        mBlewFunc.setCallback(new StatusChangedCallback() {
            @Override
            public void StatusChanged(int status) {
                mBlewFunc.stop();
                BitmapParseImpl.pictureId = -1;
                videoFrame.deleteObj(mId);
                videoFrame.stopAnimation();
                //isStartAnimation = false;

                videoFrame.startAnimation();
                mId = videoFrame.getObjHead();
                videoFrame.appendObj(30, 1, 2);

                videoFrame.activeObj(mId);
                bitmapParse = new BitmapParseImpl(getApplicationContext(), getAssets());
                bitmapParse.setAnimation("BlowTheDandelion_P", 1, 2);
                for (int i = 30; i > 0; i--) {
                    Bitmap bitmap = bitmapParse.getNextBitmap();
                    videoFrame.addBitmap(mId, bitmap);
                }


                //Toast.makeText(AnimationActivity.this, "检测到吹气，蒲公英散开！", Toast.LENGTH_SHORT).show();

            }
        });
        mBlewFunc.start();
    }

    private void startHongBao() {

        BitmapParseImpl.pictureId = -1;
        videoFrame.deleteObj(mId);
        videoFrame.stopAnimation();
        //isStartAnimation = false;

        videoFrame.startAnimation();
        mId = videoFrame.getObjHead();
        videoFrame.appendObj(23, 1, 3);

        videoFrame.activeObj(mId);
        bitmapParse = new BitmapParseImpl(getApplicationContext(), getAssets());
        bitmapParse.setAnimation("open", 1, 0);
        for (int i = 23; i > 0; i--) {
            Bitmap bitmap = bitmapParse.getNextBitmap();
            videoFrame.addBitmap(mId, bitmap);
        }
        //isStartAnimation = true;

    }
}