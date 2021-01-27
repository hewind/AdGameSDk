package com.example.adgamesdk;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.adv.model.SubmitData;
import com.game.adgamesdk.GameConfig;
import com.game.adgamesdk.GameSDKPlatform;
import com.game.adgamesdk.entity.CallbackEnum;
import com.game.adgamesdk.entity.ReportType;
import com.game.adgamesdk.itf.OnAdGameSdkCallback;
import com.game.adgamesdk.utils.MyLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends Activity {

    private Button btn_load_reward;
    private Button btn_show_reward;
    private Button btn_load_fullscreen;
    private Button btn_show_fullscreen;
    private Button btn_exit;
    private Button btn_submitInfo;
    private Button btn_payReport;
    private Button btn_pay;

    private String rewardId = "60";
    private String fullscreenId = "54";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_load_reward = findViewById(R.id.load_reward);
        btn_show_reward = findViewById(R.id.show_reward);
        btn_load_fullscreen= findViewById(R.id.load_fullscreen);
        btn_show_fullscreen = findViewById(R.id.show_fullscreen);
        btn_exit = findViewById(R.id.exit);
        btn_submitInfo = findViewById(R.id.submitInfo);
        btn_payReport = findViewById(R.id.payReport);
        btn_pay = findViewById(R.id.pay);


        //初始化SDK
        GameSDKPlatform.game().initSDk(this, new OnAdGameSdkCallback() {
            @Override
            public void onCallback(CallbackEnum callbackEnum, String msg) {
                MyLog.hsgLog().i("初始化SDK；msg = "+msg);
                String data = null;
                try {
                    JSONObject json = new JSONObject(msg);
                    data = json.optString("data");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                switch (callbackEnum){
                    case INIT_SUCCESS:
                        break;
                    case INIT_FAIL:
                        break;
                }
            }
        });

        //加载激励视频
        btn_load_reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameSDKPlatform.game().loadAd(MainActivity.this, GameConfig.AD_REWARD, rewardId, new OnAdGameSdkCallback() {
                    @Override
                    public void onCallback(CallbackEnum callbackEnum, String msg) {
                        MyLog.hsgLog().i("激励视频："+"callbackEnum = "+callbackEnum+"，msg = "+msg);
                        switch (callbackEnum){
                            case VIDEO_LOAD_SUCCESS://视频加载成功；视频广告的素材加载完毕，比如视频url等，在此回调后，可以播放在线视频，网络不好可能出现加载缓冲，影响体验。

                                break;
                            case VIDEO_LOAD_ERROR://视频加载失败

                                break;
                            case VIDEO_CACHE_SUCCESS://视频缓存成功；视频广告加载后，视频资源缓存到本地的回调，在此回调后，播放本地视频，流畅不阻塞。

                                break;
                            case VIDEO_CLICK://视频被点击

                                break;
                            case VIDEO_SHOW://视频展示

                                break;
                            case VIDEO_CLOSE://视频关闭

                                break;
                            case VIDEO_PLAY_ERROR://视频播放出错

                                break;
                            case VIDEO_PLAY_COMPLETE://视频播放完成

                                break;
                        }
                    }
                });
            }
        });
        //显示激励视频
        btn_show_reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameSDKPlatform.game().playAd(MainActivity.this,GameConfig.AD_REWARD);
            }
        });


        //加载全屏视频
        btn_load_fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameSDKPlatform.game().loadAd(MainActivity.this, GameConfig.AD_FULLCREEN, fullscreenId, new OnAdGameSdkCallback() {
                    @Override
                    public void onCallback(CallbackEnum callbackEnum, String msg) {
                        MyLog.hsgLog().i("全屏视频："+"callbackEnum = "+callbackEnum+"，msg = "+msg);
                        switch (callbackEnum){
                            case VIDEO_LOAD_SUCCESS://视频加载成功；视频广告的素材加载完毕，比如视频url等，在此回调后，可以播放在线视频，网络不好可能出现加载缓冲，影响体验。

                                break;
                            case VIDEO_LOAD_ERROR://视频加载失败

                                break;
                            case VIDEO_CACHE_SUCCESS://视频缓存成功；视频广告加载后，视频资源缓存到本地的回调，在此回调后，播放本地视频，流畅不阻塞。

                                break;
                            case VIDEO_CLICK://视频被点击

                                break;
                            case VIDEO_SHOW://视频展示

                                break;
                            case VIDEO_CLOSE://视频关闭

                                break;
                            case VIDEO_PLAY_ERROR://视频播放出错

                                break;
                            case VIDEO_PLAY_COMPLETE://视频播放完成

                                break;
                        }
                    }
                });
            }
        });
        //显示全屏视频
        btn_show_fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameSDKPlatform.game().playAd(MainActivity.this,GameConfig.AD_FULLCREEN);
            }
        });


        //上报角色信息
        btn_submitInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitData data = new SubmitData();
                data.setTypeId(SubmitData.TYPEID_ENTER_SERVER);//必传,当前情景； TYPEID_ENTER_SERVER：进入服务器；TYPEID_CREATE_ROLE：创建角色、TYPEID_LEVELUP：角色升级
                data.setRoleId("0");//必传,当前角色ID，若无,可传"0",不能为null
                data.setRoleName("无");//必传,当前角色名称,若无,可传"无",不能为null
                data.setRoleLevel("0");//必传,当前角色等级，必须为数字，若无,可传"0",不能为null
                data.setZoneId("0");//必传,当前服务器ID，若无,可传"0",不能为null
                data.setZoneName("无");//必传,当前服务器名称，若无,可传"无",不能为null
                GameSDKPlatform.game().submitRoleInfo(MainActivity.this,data);
            }
        });


        //退出
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameSDKPlatform.game().exitSDk(MainActivity.this);
                finish();
            }
        });

        //上报支付行为
        btn_payReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject json = new JSONObject();
                try {
                    //以下json串中的key字段名为固定写法，不可改动
                    json.put("productType","");//商品类型如"装备"、"皮肤"
                    json.put("productName","");//商品名称
                    json.put("productId","12");//商品id
                    json.put("productNumber",2);//商品数量 int
                    json.put("payChannel","");//支付渠道名，如支付宝、微信等
                    json.put("payCurrency","CNY");//真实货币类型，ISO 4217代码，如："CNY"
                    json.put("payAmount",100);//本次支付的真实货币的金额，单位分
                    json.put("isSuccess",true);//支付是否成功
                    GameSDKPlatform.game().onEventReport(MainActivity.this, ReportType.TYPE_PAY,json.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        //发起支付
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> map= new HashMap<>();
                map.put("cp_order_id",System.currentTimeMillis()+"");
                map.put("roleid","1");
                map.put("rolename","haha");
                map.put("level","1");
                map.put("serverid","1");
                map.put("productid","123456");
                map.put("product_name","道具");
                map.put("money","0.01");
                map.put("ext","扩展字段");
                GameSDKPlatform.game().onPay(MainActivity.this, map, new OnAdGameSdkCallback() {
                    @Override
                    public void onCallback(CallbackEnum callbackEnum, String msg) {
                        switch (callbackEnum){
                            case PAY_SUCCESS:

                                break;
                            case PAY_FAIL:

                                break;
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        GameSDKPlatform.lifecycle().onResume(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GameSDKPlatform.lifecycle().onDestroy(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        GameSDKPlatform.lifecycle().onActivityResult(this,requestCode,resultCode,data);
        MyLog.hsgLog().i("data = "+data+", requestCode = "+requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        GameSDKPlatform.lifecycle().onRequestPermissionsResult(this,requestCode,permissions,grantResults);
    }
}
