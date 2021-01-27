package com.game.adgamesdk;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import com.adv.model.SubmitData;
import com.game.adgamesdk.abs.SdkAbs;
import com.game.adgamesdk.channel.ad.AdSdkManager;
import com.game.adgamesdk.channel.launch.GDTSdkManager;
import com.game.adgamesdk.channel.launch.TTSdkManager;
import com.game.adgamesdk.entity.ReportType;
import com.game.adgamesdk.itf.OnAdGameSdkCallback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作者：heshuiguang
 * 日期：2020-05-28 10:38
 * 类说明：接口调用，各个渠道的接口在此调用
 */
public class FuncDispatcher{


    private volatile static FuncDispatcher mFuncDispatcher;
    private List<SdkAbs> sdkAbsList;

    /**
     * 方法说明：单例对象
     * 作者：heshuiguang
     * 日期：2020-05-28 16:38
     */
    public static FuncDispatcher getInstance(){
        if (mFuncDispatcher == null) {
            synchronized (FuncDispatcher.class){
                if (mFuncDispatcher == null) {
                    mFuncDispatcher = new FuncDispatcher();
                }
            }
        }
        return mFuncDispatcher;
    }

    public FuncDispatcher() {
        sdkAbsList = new ArrayList<>();

        //广告SDK
        AdSdkManager adManager = new AdSdkManager();
        sdkAbsList.add(adManager);

        //广点通统计SDK
        GDTSdkManager gdtSdkManager = new GDTSdkManager();
        sdkAbsList.add(gdtSdkManager);

        //头条统计SDK
        TTSdkManager ttSdkManager = new TTSdkManager();
        sdkAbsList.add(ttSdkManager);
    }


    //------------------------------Application生命周期接口----------------------------------------
    public void onAppCreate(Application application) {
        for (SdkAbs sdkAbs:sdkAbsList) {
            sdkAbs.onAppCreate(application);
        }
    }
    public void onAppAttachBaseContext(Context context) {
        for (SdkAbs sdkAbs:sdkAbsList) {
            sdkAbs.onAppAttachBaseContext(context);
        }
    }


    //------------------------------Activity生命周期接口----------------------------------------
    public void onCreate(Activity activity) {
        for (SdkAbs sdkAbs:sdkAbsList) {
            sdkAbs.onCreate(activity);
        }
    }
    public void onStart(Activity activity) {
        for (SdkAbs sdkAbs:sdkAbsList) {
            sdkAbs.onStart(activity);
        }
    }
    public void onRestart(Activity activity) {
        for (SdkAbs sdkAbs:sdkAbsList) {
            sdkAbs.onRestart(activity);
        }
    }
    public void onResume(Activity activity) {
        for (SdkAbs sdkAbs:sdkAbsList) {
            sdkAbs.onResume(activity);
        }
    }
    public void onPause(Activity activity) {
        for (SdkAbs sdkAbs:sdkAbsList) {
            sdkAbs.onPause(activity);
        }
    }
    public void onStop(Activity activity) {
        for (SdkAbs sdkAbs:sdkAbsList) {
            sdkAbs.onStop(activity);
        }
    }
    public void onDestroy(Activity activity) {
        for (SdkAbs sdkAbs:sdkAbsList) {
            sdkAbs.onDestroy(activity);
        }
    }
    public void onNewIntent(Activity activity, Intent intent) {
        for (SdkAbs sdkAbs:sdkAbsList) {
            sdkAbs.onNewIntent(activity,intent);
        }
    }
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        for (SdkAbs sdkAbs:sdkAbsList) {
            sdkAbs.onActivityResult(activity,requestCode,resultCode,data);
        }
    }
    public void onRequestPermissionsResult(Activity activity, int requestCode, String[] permissions, int[] grantResults) {
        for (SdkAbs sdkAbs:sdkAbsList) {
            sdkAbs.onRequestPermissionsResult(activity,requestCode,permissions,grantResults);
        }
    }
    public void onConfigurationChanged(Activity activity, Configuration newConfig) {
        for (SdkAbs sdkAbs:sdkAbsList) {
            sdkAbs.onConfigurationChanged(activity,newConfig);
        }
    }
    public void onSaveInstanceState(Activity activity, Bundle outState) {
        for (SdkAbs sdkAbs:sdkAbsList) {
            sdkAbs.onSaveInstanceState(activity,outState);
        }
    }
    public void onRestoreInstanceState(Activity activity, Bundle savedInstanceState) {
        for (SdkAbs sdkAbs:sdkAbsList) {
            sdkAbs.onRestoreInstanceState(activity,savedInstanceState);
        }
    }




    //------------------------------广告手游接口----------------------------------------
    
    public void setDebugLog(boolean flag) {
        for (SdkAbs sdkAbs:sdkAbsList) {
            sdkAbs.setDebugLog(flag);
        }
    }
    public void initSDk(Activity activity, OnAdGameSdkCallback callback) {
        for (SdkAbs sdkAbs:sdkAbsList) {
            sdkAbs.initSDk(activity,callback);
        }
    }
    public void submitRoleInfo(Activity activity, SubmitData data) {
        for (SdkAbs sdkAbs:sdkAbsList) {
            sdkAbs.submitRoleInfo(activity,data);
        }
    }
    public void exitSDk(Activity activity) {
        for (SdkAbs sdkAbs:sdkAbsList) {
            sdkAbs.exitSDk(activity);
        }
    }
    public void loadAd(Activity activity, int adType, String adId,OnAdGameSdkCallback callback) {
        for (SdkAbs sdkAbs:sdkAbsList) {
            sdkAbs.loadAd(activity,adType,adId,callback);
        }
    }
    public void playAd(Activity activity, int adType) {
        for (SdkAbs sdkAbs:sdkAbsList) {
            sdkAbs.playAd(activity,adType);
        }
    }

    /**
     * 方法说明：行为数据上报（目前接入的有广点通、头条渠道）
     * 作者：heshuiguang
     * 日期：2020-06-03 18:02
     */
    public void onEventReport(Activity activity, ReportType type, String json) {
        for (SdkAbs sdkAbs:sdkAbsList) {
            sdkAbs.onEventReport(activity,type,json);
        }
    }

    /**
     * 方法说明：支付
     * 作者：heshuiguang
     * 日期：2020-06-11 16:16
     */
    public void onPay(Activity activity, HashMap<String,String> map, OnAdGameSdkCallback callback) {
        for (SdkAbs sdkAbs:sdkAbsList) {
            sdkAbs.onPay(activity,map,callback);
        }
    }
}
