package com.visionin.ar.BitmapParse;

/**
 * Created by wangzhiyuan on 2017/3/30.
 */

public interface IBitmapParse {

    //设置要播放的动画，帧数和选项  返回实际的帧数
    public int setAnimation(String name, int frameNums, int option);
    //获取指定id图片的RGBA32字节数组，图片不存在返回空
    public BitmapStruct getPicture(int id);
    //获取下一张图片，图片不存在返回空，第一次调用返回第一张
    public BitmapStruct getNextPicture();

}
