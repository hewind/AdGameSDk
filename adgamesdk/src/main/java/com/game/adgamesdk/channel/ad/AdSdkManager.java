package com.game.adgamesdk.channel.ad;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.adv.callback.AdvListener;
import com.adv.model.SubmitData;
import com.adv.sdk.AdSDK;
import com.bytedance.applog.GameReportHelper;
import com.game.adgamesdk.GameConfig;
import com.game.adgamesdk.abs.SdkAbs;
import com.game.adgamesdk.dialog.PayDialog;
import com.game.adgamesdk.entity.CallbackEnum;
import com.game.adgamesdk.itf.OnAdGameSdkCallback;
import com.game.adgamesdk.utils.CommonHandler;
import com.game.adgamesdk.utils.MyLog;
import com.game.adgamesdk.utils.ShareUtils;
import com.qq.gdt.action.ActionUtils;
import com.qq.gdt.action.GDTAction;

import java.util.HashMap;

/**
 * 作者：heshuiguang
 * 日期：2020-05-28 11:08
 * 类说明：广告平台接入管理类
 */
public class AdSdkManager extends SdkAbs {

    private AdRewardHelper adRewardHelper;//激励视频
    private AdFullScreenHelper adFullScreenHelper;//全屏视频
    private AdNativeHelper adNativeHelper;//原生信息流广告

    @Override
    public void onAppCreate(Application application) {
        super.onAppCreate(application);
        AdSDK.applicationOnCreate(application);
    }
    @Override
    public void onAppAttachBaseContext(Context context) {
        super.onAppAttachBaseContext(context);
    }

    /**
     * 方法说明：初始化SDK
     * 作者：heshuiguang
     * 日期：2020-05-28 11:31
     */
    @Override
    public void initSDk(final Activity activity, final OnAdGameSdkCallback callback) {
        super.initSDk(activity, callback);
        MyLog.hsgLog().i("初始化广告SDK");
        adRewardHelper = new AdRewardHelper();
        adFullScreenHelper = new AdFullScreenHelper();
        adNativeHelper = new AdNativeHelper();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //初始化广告SDK
                AdSDK.onCreate(activity, new AdvListener() {
                    @Override
                    public void onInitSuccess(String uid) {
                        MyLog.hsgLog().i("初始化SDK; onInitSuccess; uid = "+uid);
                        //投放平台广点通，设置用户软ID，可以和数据源进行关联
                        GDTAction.setUserUniqueId(uid);
                        //投放平台广点通，上报注册行为
                        ActionUtils.onRegister("default",true);
                        //投放平台头条，上报注册行为
                        GameReportHelper.onEventRegister("default",true);

                        //回调
                        CommonHandler.callbackResult(callback, CallbackEnum.INIT_SUCCESS,CommonHandler.convertResult(CommonHandler.CODE_OK,"初始化成功",uid));

                        //保存UID
                        ShareUtils.setUid(activity,uid);
                    }
                    @Override
                    public void onInitFailure(String reason) {
                        MyLog.hsgLog().i("初始化SDK; onInitFailure; reason = "+reason);
                        //投放平台广点通，上报注册行为
                        ActionUtils.onRegister("default",false);
                        //投放平台头条，上报注册行为
                        GameReportHelper.onEventRegister("default",false);

                        //回调
                        CommonHandler.callbackResult(callback, CallbackEnum.INIT_FAIL,CommonHandler.convertResult(CommonHandler.CODE_OK,"初始化失败",reason));
                    }
                });

            }
        });
    }

    /**
     * 方法说明：加载广告
     * 作者：heshuiguang
     * 日期：2020-05-28 11:31
     */
    @Override
    public void loadAd(Activity activity, int adType, String adId,OnAdGameSdkCallback callback) {
        super.loadAd(activity, adType, adId,callback);
        MyLog.hsgLog().i("加载视频；adType = "+adType+", adId = "+adId);
        switch (adType){
            case GameConfig.AD_REWARD:
                adRewardHelper.loadAd(activity,adId,callback);
                break;
            case GameConfig.AD_FULLCREEN:
                adFullScreenHelper.loadAd(activity,adId,callback);
                break;
            case GameConfig.AD_NATIVE:

                break;
        }
    }

    /**
     * 方法说明：播放广告
     * 作者：heshuiguang
     * 日期：2020-05-28 11:31
     */
    @Override
    public void playAd(Activity activity, int adType) {
        super.playAd(activity, adType);
        MyLog.hsgLog().i("播放视频；adType = "+adType);
        switch (adType){
            case GameConfig.AD_REWARD:
                adRewardHelper.playAd(activity);
                break;
            case GameConfig.AD_FULLCREEN:
                adFullScreenHelper.playAd(activity);
                break;
            case GameConfig.AD_NATIVE:

                break;
        }
    }

    /**
     * 方法说明：上报信息
     * 作者：heshuiguang
     * 日期：2020-05-28 16:55
     */
    @Override
    public void submitRoleInfo(final Activity activity, final SubmitData data) {
        super.submitRoleInfo(activity, data);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MyLog.hsgLog().i("上报角色信息；data = "+data);
                AdSDK.submitRoleData(activity,data);
            }
        });
    }

    /**
     * 方法说明：支付
     * 作者：heshuiguang
     * 日期：2020-06-11 16:17
     */
    @Override
    public void onPay(final Activity activity, final HashMap<String,String> map, OnAdGameSdkCallback callback) {
        super.onPay(activity, map, callback);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PayDialog payDialog = new PayDialog();
                payDialog.showPay(activity,map);
            }
        });
    }

    /**
     * 方法说明：退出SDK
     * 作者：heshuiguang
     * 日期：2020-05-28 16:53
     */
    @Override
    public void exitSDk(Activity activity) {
        super.exitSDk(activity);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MyLog.hsgLog().i("退出SDK");
                AdSDK.onExit();
            }
        });
    }

    @Override
    public void onDestroy(Activity activity) {
        super.onDestroy(activity);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AdSDK.onDestroy();
            }
        });
    }

    @Override
    public void onActivityResult(final Activity activity, int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(activity, requestCode, resultCode, data);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AdSDK.onActivityResult(activity,resultCode,resultCode,data);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(final Activity activity, final int requestCode, final String[] permissions, final int[] grantResults) {
        super.onRequestPermissionsResult(activity, requestCode, permissions, grantResults);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AdSDK.onRequestPermissionsResult(activity,requestCode,permissions,grantResults);
            }
        });
    }


}
