package com.game.adgamesdk.dialog;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.adv.sdk.AdSDK;
import com.game.adgamesdk.R;
import com.game.adgamesdk.utils.CommonUtils;
import com.game.adgamesdk.utils.DeviceUtils;
import com.game.adgamesdk.utils.MyLog;
import com.game.adgamesdk.utils.ShareUtils;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 作者：heshuiguang
 * 日期：2020-06-11 16:19
 * 类说明：支付弹窗
 */
public class PayDialog extends DialogFragment {

    private View view;
    private WebView webView;

    private static boolean isShow = false;//是否显示了dialog
    private Activity activity;
    private HashMap<String,String> map;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉title
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);//设置背景透明
//        setCancelable(false);//设置dialog以外区域不可点击
        view = inflater.inflate(R.layout.pay_dialog,container,false);

        init();
        initWebView();
        return view;
    }

    /**
     * 方法说明：初始化
     * 作者：heshuiguang
     * 日期：2020-06-11 16:23
     */
    private void init() {
        webView = view.findViewById(R.id.paydialog_webview);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) webView.getLayoutParams();
        params.width = CommonUtils.getWindowWidth(activity) - CommonUtils.dip2px(getActivity(),40);
        params.height = CommonUtils.getWindowHeigh(activity) * 2/3;
        webView.setLayoutParams(params);

        if (map == null){
            map = new HashMap<>();
        }
        String site = AdSDK.getString("houlang_site",null);
        map.put("aid",AdSDK.getString("houlang_aid",null));
        map.put("site",site);
        map.put("uid",ShareUtils.getUid(activity));
        String time = System.currentTimeMillis()+"";
        time = time.substring(0,10);
        map.put("time",time);
        map.put("ip", DeviceUtils.GetNetIp(activity));
        //添加sign
        map.put("sign",CommonUtils.getSign(map,site,time));
    }


    /**
     * 方法说明：配置webview
     * 作者：heshuiguang
     * 日期：2020-06-11 16:42
     */
    private void initWebView() {
        //启用支持javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportZoom(false); //支持缩放
        settings.setBuiltInZoomControls(false); //支持手势缩放
        settings.setDisplayZoomControls(false); //是否显示缩放按钮
        settings.setDefaultTextEncodingName("utf-8"); //设置文本编码
        settings.setUseWideViewPort(false); //将图片调整到适合WebView的大小
        settings.setLoadWithOverviewMode(true); //自适应屏幕

        //缓存相关
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setSaveFormData(true);
        settings.setSupportMultipleWindows(true);
        settings.setPluginState(WebSettings.PluginState.ON);//支持插件
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE); //不适用缓存
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//支持内容重新布局
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER); // 取消WebView中滚动或拖动到顶部、底部时的阴影
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); // 取消滚动条白边效果
        // >= 19(SDK4.4)启动硬件加速，否则启动软件加速
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            settings.setLoadsImagesAutomatically(false);
        }
        //实现WebViewClient
        webView.setWebViewClient(new MyWebClient());
        webView.loadUrl(CommonUtils.getUrl(map));
    }

    /**
     * 方法说明：实现WebViewClient
     * 作者：heshuiguang
     * 日期：2020-06-11 16:50
     */
    private class MyWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            MyLog.hsgLog().i("shouldOverrideUrlLoading; url = "+url);
            if(url.startsWith("weixin:")||url.startsWith("alipays:")){
                try {
                    startActivityForResult(new Intent(Intent.ACTION_VIEW,Uri.parse(url)), 1);
                    dismissPay();
                }catch (ActivityNotFoundException e){
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, "请检测是否安装客户端", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                //没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出错误页面
            }else {
                return super.shouldOverrideUrlLoading(view, url);
            }
            return true;
        }
    }


    /**
     * 方法说明：显示支付窗口
     * 作者：heshuiguang
     * 日期：2020-06-11 16:34
     */
    public void showPay(final Activity activity, HashMap<String,String> map){
        this.activity = activity;
        if(isShow){
            return;
        }
        this.map = map;
        isShow = true;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //显示更新框
                FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
                transaction.add(PayDialog.this,"PayDialog");
                transaction.commitAllowingStateLoss();
            }
        });
    }

    /**
     * 方法说明：取消支付窗口
     * 作者：heshuiguang
     * 日期：2020-06-11 16:33
     */
    public void dismissPay(){
        MyLog.hsgLog().i("取消支付窗口");
        this.dismissAllowingStateLoss();
        isShow = false;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        isShow = false;
    }
    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        isShow = false;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        isShow = false;
    }


}
