package com.game.adgamesdk.utils;

import android.os.Handler;
import android.os.Looper;

import com.game.adgamesdk.entity.CallbackEnum;
import com.game.adgamesdk.itf.OnAdGameSdkCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 作者：heshuiguang
 * 日期：2020-05-27 15:01
 * 类说明：使用handler回调
 */
public class CommonHandler {

    /**
     * 错误code值
     */
    public static final int CODE_OK = 1000;//正常
    public static final int CODE_ACTIVITY_DESTORY = 1001;//activty不存在或被销毁


    //获取main looper
    private static Handler handler = new Handler(Looper.getMainLooper());


    /**
     * 方法说明：主线程中回调接口
     * 作者：heshuiguang
     * 日期：2019-11-01 14:22
     */
    public static void callbackResult(final OnAdGameSdkCallback callback, final CallbackEnum callbackEnum, final String result){
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(callback != null)
                    callback.onCallback(callbackEnum,result);
            }
        });
    }

    /**
     * 方法说明：回调给cp的json数据格式
     * 作者：heshuiguang
     * 日期：2020-05-28 14:54
     */
    public static String convertResult(int code,String msg,String data){
        JSONObject json = new JSONObject();
        try {
            json.put("code",code);
            json.put("msg",msg);
            json.put("data",data);
            return json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
