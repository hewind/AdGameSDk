package com.game.adgamesdk.impl;

import android.app.Activity;
import com.adv.model.SubmitData;
import com.game.adgamesdk.FuncDispatcher;
import com.game.adgamesdk.entity.ReportType;
import com.game.adgamesdk.itf.AdGameService;
import com.game.adgamesdk.itf.OnAdGameSdkCallback;

import java.util.HashMap;

/**
 * 作者：heshuiguang
 * 日期：2020-05-27 17:43
 * 类说明：广告、手游接口实现类
 */
public class AdGameImpl implements AdGameService {

    private final FuncDispatcher funcDispatcher;

    public AdGameImpl(FuncDispatcher funcDispatcher) {
        this.funcDispatcher = funcDispatcher;
    }

    @Override
    public void initSDk(Activity activity, OnAdGameSdkCallback callback) {
        funcDispatcher.initSDk(activity,callback);
    }
    @Override
    public void setDebugLog(boolean flag) {
        funcDispatcher.setDebugLog(flag);
    }
    @Override
    public void submitRoleInfo(Activity activity, SubmitData data) {
        funcDispatcher.submitRoleInfo(activity,data);
    }
    @Override
    public void exitSDk(Activity activity) {
        funcDispatcher.exitSDk(activity);
    }
    @Override
    public void loadAd(Activity activity, int adType, String adId,OnAdGameSdkCallback callback) {
        funcDispatcher.loadAd(activity,adType,adId,callback);
    }
    @Override
    public void playAd(Activity activity, int adType) {
        funcDispatcher.playAd(activity,adType);
    }

    @Override
    public void onEventReport(Activity activity, ReportType type, String json) {
        funcDispatcher.onEventReport(activity,type,json);
    }

    @Override
    public void onPay(Activity activity, HashMap<String,String> map, OnAdGameSdkCallback callback) {
        funcDispatcher.onPay(activity,map,callback);
    }


}
