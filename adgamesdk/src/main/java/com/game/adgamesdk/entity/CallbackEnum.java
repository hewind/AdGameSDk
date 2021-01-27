package com.game.adgamesdk.entity;

/**
 * 作者：heshuiguang
 * 日期：2020-05-28 14:57
 * 类说明：回调类型
 */
public enum CallbackEnum {

    INIT_SUCCESS,//初始化成功
    INIT_FAIL,//初始化失败

    VIDEO_LOAD_SUCCESS,//视频加载成功
    VIDEO_CACHE_SUCCESS,//视频缓存成功
    VIDEO_LOAD_ERROR,//视频加载出错
    VIDEO_SHOW,//视频展示
    VIDEO_CLICK,//视频被点击
    VIDEO_CLOSE,//视频关闭
    VIDEO_PLAY_COMPLETE,//视频播放完成
    VIDEO_PLAY_ERROR,//视频播放出错

    NATIVE_SHOW,//原生广告展示
    NATIVE_CLICK,//原生广告点击
    NATIVE_LOAD_SUCCESS,//原生广告加载成功
    NATIVE_LOAD_FAIL,//原生广告加载失败
    NATIVE_ERROR,//原生广告出错
    NATIVE_CLOSE,//原生广告关闭

    LOGIN_SUCCESS,//登录成功
    LOGIN_FAIL,//登录失败

    PAY_SUCCESS,//支付成功
    PAY_FAIL,//支付失败
}
