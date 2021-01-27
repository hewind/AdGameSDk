package com.game.adgamesdk.net;


import com.game.adgamesdk.VersionManager;

/**
 * 作者：heshuiguang
 * 日期：2020-05-27 6:26 PM
 * 类说明：地址类
 */
public class UrlUtils {


    public static final String getAddress = getUrl();

    private static String getUrl(){
        if (VersionManager.RELEASE_MODE) {
            return "http://microsdk.hohool.com:9000/advertisement";//正式环境
        }
        return "http://10.0.0.233:9000/advertisement";//测试环境
//        return "http://172.16.0.112:9000/advertisement";//测试环境
    }

}
