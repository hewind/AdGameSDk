package com.game.adgamesdk.itf;

import android.app.Activity;
import com.adv.model.SubmitData;
import com.game.adgamesdk.entity.ReportType;

import java.util.HashMap;


/**
 * 作者：heshuiguang
 * 日期：2020-05-27 18:14
 * 类说明：手游功能接口
 */
public interface AdGameService {

    //初始化
    void initSDk(Activity activity,OnAdGameSdkCallback callback);

    void setDebugLog(boolean flag);

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
