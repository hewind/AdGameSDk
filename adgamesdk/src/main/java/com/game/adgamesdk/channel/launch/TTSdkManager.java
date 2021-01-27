package com.game.adgamesdk.channel.launch;

import android.app.Activity;
import android.app.Application;
import com.adv.sdk.AdSDK;
import com.bytedance.applog.AppLog;
import com.bytedance.applog.GameReportHelper;
import com.bytedance.applog.InitConfig;
import com.bytedance.applog.util.UriConfig;
import com.game.adgamesdk.abs.SdkAbs;
import com.game.adgamesdk.entity.ReportType;
import com.game.adgamesdk.utils.CommonUtils;
import com.game.adgamesdk.utils.MyLog;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 作者：heshuiguang
 * 日期：2020-06-09 14:36
 * 类说明：
 */
public class TTSdkManager extends SdkAbs {

    @Override
    public void onAppCreate(Application application) {
        super.onAppCreate(application);
        MyLog.hsgLog().i("初始化头条统计SDK");
        String appid = AdSDK.getString(CommonUtils.ADSDK_TT_APPID,null);
        String channel = AdSDK.getString(CommonUtils.ADSDK_TT_CHANNEL,null);

        /* 初始化开始 */
        // appid和渠道，appid须保证与广告后台申请记录一致，渠道可自定义，如有多个马甲包建议设置渠道号唯一标识一个马甲包。
        final InitConfig config = new InitConfig(appid, channel);
         /*
         域名默认国内: DEFAULT, 新加坡:SINGAPORE, 美东:AMERICA
         注意：国内外不同vendor服务注册的did不一样。由DEFAULT切换到SINGAPORE或者AMERICA，会发生变化，
         切回来也会发生变化。因此vendor的切换一定要慎重，随意切换导致用户新增和统计的问题，需要自行评估。
         */
        config.setUriConfig(UriConfig.DEFAULT);
        // YES playSession
        config.setEnablePlay(true);
        AppLog.init(application, config);
        /* 初始化结束 */

        // 是否在控制台输出日志，可用于观察用户行为日志上报情况，建议仅在调试时使用，release版本请设置为false ！
        AppLog.setEnableLog(false);
    }


    /**
     * 方法说明：行为数据上报（目前接入的有广点通、头条渠道）
     * 作者：heshuiguang
     * 日期：2020-06-09 15:20
     */
    @Override
    public void onEventReport(Activity activity, ReportType type, String json) {
        super.onEventReport(activity, type, json);
        switch (type){
            case TYPE_PAY:
                reportPay(activity,json);
                break;
        }
    }

    /**
     * 方法说明：头条支付行为上报
     * productType 商品类型如"装备"、"皮肤"
     * productName 商品名称
     * productId 商品id
     * productNumber 商品数量
     * payChannel 支付渠道名，如支付宝、微信等
     * payCurrency 真实货币类型，ISO 4217代码，如："￥"
     * payAmount 本次支付的真实货币的金额，单位分
     * isSuccess 支付是否成功
     * 作者：heshuiguang
     * 日期：2020-06-09 15:21
     */
    private void reportPay(Activity activity, String json) {
        //如果json有值
        if(CommonUtils.checkNullMethod(json)){
            try {
                JSONObject jsonObject = new JSONObject(json);
                final String productType = jsonObject.optString("productType");
                final String productName = jsonObject.optString("productName");
                final String productId = jsonObject.optString("productId");
                final int productNumber = jsonObject.optInt("productNumber");
                final String payChannel = jsonObject.optString("payChannel");
                final String payCurrency = jsonObject.optString("payCurrency");
                final int payAmount = jsonObject.optInt("payAmount");
                final boolean isSuccess = jsonObject.optBoolean("isSuccess");
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MyLog.hsgLog().i("头条上报支付");
                        //内置事件 “支付”，属性：商品类型，商品名称，商品ID，商品数量，支付渠道，币种，是否成功（必传），金额（必传）
                        GameReportHelper.onEventPurchase(productType,productName, productId,productNumber,
                                payChannel,payCurrency, isSuccess, payAmount);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
                MyLog.hsgLog().e("支付参数json串解析失败");
            }
        }
    }
}
