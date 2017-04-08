package com.visionin.ar.BitmapParse;

/**
 * Created by wangzhiyuan on 2017/3/30.
 */

public interface IAsyncBitmapParse extends IBitmapParse {

    //设置要播放的动画，帧数和选项  返回实际的帧数
    public int setAnimation(String name, int frameNums, int option);

    //获取指定id图片的RGBA32字节数组，图片不存在返回空
    public BitmapStruct getPicture(int id);

    //获取下一张图片，图片不存在返回空，第一次调用返回第一张
    public BitmapStruct getNextPicture();

    //异步开始解析
    public void startParse();

    //结束异步解析
    public void stopParse();

    //设置缓存图片数量，默认为4张
    public void setPictureCache(int cacheNums);

    //使用完图片调用，释放掉使用完图片缓存，开始解析下一张
    public void commit();

}
