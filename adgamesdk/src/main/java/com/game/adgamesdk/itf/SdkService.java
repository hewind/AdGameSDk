package com.game.adgamesdk.itf;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import com.adv.model.SubmitData;
import com.game.adgamesdk.entity.ReportType;

import java.util.HashMap;


/**
 * 作者：heshuiguang
 * 日期：2020-05-27 16:34
 * 类说明：游戏调用的接口
 */
public interface SdkService {

    //Application生命周期接口
    void onAppCreate(Application application);
    void onAppAttachBaseContext(Context context);

    //Activity生命周期接口
    void onCreate(Activity activity);
    void onStart(Activity activity);
    void onRestart(Activity activity);
    void onResume(Activity activity);
    void onPause(Activity activity);
    void onStop(Activity activity);
    void onDestroy(Activity activity);
    void onNewIntent(Activity activity, Intent intent);
    void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data);
    void onRequestPermissionsResult(Activity activity, int requestCode, String[] permissions, int[] grantResults);
    void onConfigurationChanged(Activity activity, Configuration newConfig);
    void onSaveInstanceState(Activity activity, Bundle outState);
    void onRestoreInstanceState(Activity activity, Bundle savedInstanceState);


    //手游功能接口
    //debug
    void setDebugLog(boolean flag);
    //初始化
    void initSDk(Activity activity,OnAdGameSdkCallback callback);
    //上报角色信息
    void submitRoleInfo(Activity activity, SubmitData data);
    //退出游戏
    void exitSDk(Activity activity);

    //广告接口
    //加载广告
    void loadAd(Activity activity,int adType,String adId,OnAdGameSdkCallback callback);
    //播放广告
    void playAd(Activity activity,int adType);

    //行为数据上报（目前接入的有广点通、头条渠道）
    void onEventReport(Activity activity, ReportType type, String json);

    //支付
    void onPay(Activity activity, HashMap<String,String> map, OnAdGameSdkCallback callback);
}
