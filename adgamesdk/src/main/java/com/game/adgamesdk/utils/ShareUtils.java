package com.game.adgamesdk.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 作者：heshuiguang
 * 日期：2020-06-03 16:59
 * 类说明：共享参数保存工具类
 */
public class ShareUtils {

    /**
     * 共享文件名称
     */
    private static final String AdSdkInfo = "AdSdkInfo";//SDK信息相关
    private static final String key_uid = "key_uid";//uid


    /**
     * 方法说明：保存及获取是否是第一次申请某个权限
     * 作者：heshuiguang
     * 日期：2020-06-03 17:00
     */
    public static void setIsFirstApplyPermission(Context context,String permissionName,boolean isFirst){
        SharedPreferences sp=context.getSharedPreferences(AdSdkInfo, 0);
        SharedPreferences.Editor spEditor=sp.edit();
        spEditor.putBoolean(permissionName, isFirst);
        spEditor.commit();
    }
    public static boolean isFirstApplyPermission(Context context,String permissionName) {
        return context.getSharedPreferences(AdSdkInfo, 0).getBoolean(permissionName, true);
    }


    /**
     * 方法说明：保存UID
     * 作者：heshuiguang
     * 日期：2020-06-11 17:34
     */
    public static void setUid(Context context,String uid){
        SharedPreferences sp=context.getSharedPreferences(AdSdkInfo, 0);
        SharedPreferences.Editor spEditor=sp.edit();
        spEditor.putString(key_uid,uid);
        spEditor.commit();
    }
    /**
     * 方法说明：获取UID
     * 作者：heshuiguang
     * 日期：2020-06-11 17:35
     */
    public static String getUid(Context context){
        if (context == null) return null;
        return context.getSharedPreferences(AdSdkInfo, 0).getString(key_uid,null);
    }

}
