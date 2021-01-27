package com.game.adgamesdk.channel.launch;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import com.adv.sdk.AdSDK;
import com.game.adgamesdk.abs.SdkAbs;
import com.game.adgamesdk.entity.ReportType;
import com.game.adgamesdk.itf.OnAdGameSdkCallback;
import com.game.adgamesdk.itf.OnPermissionResultListener;
import com.game.adgamesdk.utils.CommonUtils;
import com.game.adgamesdk.utils.MyLog;
import com.game.adgamesdk.utils.PermissionUtils;
import com.qq.gdt.action.ActionType;
import com.qq.gdt.action.ActionUtils;
import com.qq.gdt.action.GDTAction;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Map;


/**
 * 作者：heshuiguang
 * 日期：2020-06-03 15:56
 * 类说明：投放平台接入管理类（广点通）
 */
public class GDTSdkManager extends SdkAbs {

    private PermissionUtils permissionUtils;
    private String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;

    @Override
    public void onAppCreate(Application application) {
        super.onAppCreate(application);
        MyLog.hsgLog().i("初始化广点通统计SDK");
        String actionSetID = AdSDK.getString(CommonUtils.ADSDK_GDT_ACTION_ID,null);
        String appSecretKey = AdSDK.getString(CommonUtils.ADSDK_GDT_SCERET_KEY,null);
        // 第一个参数是Context上下文;第二个参数是您在DMP上获得的行为数据源ID;第三个参数是您在DMP上获得AppSecretKey;第四个参数是您的渠道ID, 选填
        GDTAction.init(application,actionSetID,appSecretKey);
    }

    @Override
    public void initSDk(Activity activity, OnAdGameSdkCallback callback) {
        super.initSDk(activity, callback);
        permissionUtils = new PermissionUtils(activity);

    }

    @Override
    public void onResume(final Activity activity) {
        super.onResume(activity);
        if (permissionUtils != null){
            permissionUtils.getPermission(new String[]{READ_PHONE_STATE}, PermissionUtils.PERMISSION_REQUEST_CODE, new OnPermissionResultListener() {
                @Override
                public void onPermissionReault(Map<String, Boolean> result) {
                    reportResume(activity);
                }
            });
        }else {
            reportResume(activity);
        }
    }

    /**
     * 方法说明：上报APP行为；上报应用启动
     * 作者：heshuiguang
     * 日期：2020-06-03 17:10
     */
    private void reportResume(Activity activity) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MyLog.hsgLog().i("广点通上报应用启动");
                GDTAction.logAction(ActionType.START_APP);
            }
        });
    }

    /**
     * 方法说明：行为数据上报（目前接入的有广点通、头条渠道）
     * 作者：heshuiguang
     * 日期：2020-06-03 18:04
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
     * 方法说明：支付上报
     * productType 商品类型如"装备"、"皮肤"
     * productName 商品名称
     * productId 商品标识符
     * productNumber 商品数量
     * payChannel 支付渠道名，如支付宝、微信等
     * payCurrency 真实货币类型，ISO 4217代码，如："CNY"
     * payAmount 本次支付的真实货币的金额，单位分
     * isSuccess 支付是否成功
     * 作者：heshuiguang
     * 日期：2020-06-03 18:21
     */
    private void reportPay(Activity activity, String json){
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
                        MyLog.hsgLog().i("广点通上报支付，带参数");
                        ActionUtils.onPurchase(productType,productName,productId,productNumber,payChannel,payCurrency,payAmount,isSuccess);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {//如果json没有值
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MyLog.hsgLog().i("广点通上报支付，不带参数");
                    GDTAction.logAction(ActionType.PURCHASE);
                }
            });
        }
    }


    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        super.onActivityResult(activity, requestCode, resultCode, data);
        if (permissionUtils != null) permissionUtils.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onRequestPermissionsResult(Activity activity, int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(activity, requestCode, permissions, grantResults);
        if (permissionUtils != null) permissionUtils.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }
}
