package com.game.adgamesdk.abs;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import com.adv.model.SubmitData;
import com.game.adgamesdk.entity.ReportType;
import com.game.adgamesdk.itf.OnAdGameSdkCallback;
import com.game.adgamesdk.itf.SdkService;
import com.game.adgamesdk.utils.MyLog;

import java.util.HashMap;

/**
 * 作者：heshuiguang
 * 日期：2020-05-28 11:12
 * 类说明：SDK接口抽象类
 */
public abstract class SdkAbs implements SdkService {


    //------------------------------Application生命周期接口----------------------------------------
    @Override
    public void onAppCreate(Application application) {

    }
    @Override
    public void onAppAttachBaseContext(Context context) {

    }

    //------------------------------Activity生命周期接口----------------------------------------
    @Override
    public void onCreate(Activity activity) {

    }

    @Override
    public void onStart(Activity activity) {

    }

    @Override
    public void onRestart(Activity activity) {

    }

    @Override
    public void onResume(Activity activity) {

    }

    @Override
    public void onPause(Activity activity) {

    }

    @Override
    public void onStop(Activity activity) {

    }

    @Override
    public void onDestroy(Activity activity) {

    }

    @Override
    public void onNewIntent(Activity activity, Intent intent) {

    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onRequestPermissionsResult(Activity activity, int requestCode, String[] permissions, int[] grantResults) {

    }

    @Override
    public void onConfigurationChanged(Activity activity, Configuration newConfig) {

    }

    @Override
    public void onSaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onRestoreInstanceState(Activity activity, Bundle savedInstanceState) {

    }



    //------------------------------广告手游接口----------------------------------------
    @Override
    public void initSDk(Activity activity, OnAdGameSdkCallback callback) {

    }

    @Override
    public void setDebugLog(boolean flag) {
        MyLog.logFlag = flag;
    }

    @Override
    public void submitRoleInfo(Activity activity, SubmitData data) {

    }

    @Override
    public void exitSDk(Activity activity) {

    }

    @Override
    public void loadAd(Activity activity, int adType, String adId,OnAdGameSdkCallback callback) {

    }

    @Override
    public void playAd(Activity activity, int adType) {

    }

    @Override
    public void onEventReport(Activity activity, ReportType type, String json) {

    }

    @Override
    public void onPay(Activity activity, HashMap<String,String> map, OnAdGameSdkCallback callback) {

    }
}
