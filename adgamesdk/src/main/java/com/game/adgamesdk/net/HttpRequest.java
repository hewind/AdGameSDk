package com.game.adgamesdk.net;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.game.adgamesdk.itf.OnHttpRequestListener;
import com.game.adgamesdk.itf.OnNetConnectListener;
import com.game.adgamesdk.utils.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 作者：heshuiguang
 * 日期：2020-05-27 4:17 PM
 * 类说明：联网请求体，访问网络调用该类sendRequest方法发起请求
 */
public class HttpRequest implements OnNetConnectListener {

    public static final String POST = "POST";
    public static final String GET = "GET";
    public static final String CONTENT_TYPE_JSON = "application/json";//json格式
    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";//form表单格式


    private Context context;
    private String connectType = POST;//连接类型，默认post连接
    private String contentType = CONTENT_TYPE_JSON;//数据提交格式类型，默认为application/json;
    private int connectTimeOut = 10*1000;
    private int readTimeOut = 10*1000;


    private NetConnectUtils netConnectUtils;
    private OnHttpRequestListener onHttpRequestListener;

    public HttpRequest(Context context) {
        super();
        this.context = context;
    }

    /**
     * 方法说明：连接类型
     * 作者：heshuiguang
     * 日期：2020-05-27 2:42 PM
     */
    public String getConnectType() {
        return connectType == null ? "" : connectType;
    }
    public void setConnectType(String connectType) {
        this.connectType = connectType;
    }

    /**
     * 方法说明：返回提交数据类型
     * 作者：heshuiguang
     * 日期：2020-05-27 4:44 PM
     */
    public String getContentType() {
        if(contentType == null)
            return CONTENT_TYPE_JSON;
        return contentType;
    }
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * 方法说明：返回连接超时
     * 作者：heshuiguang
     * 日期：2020-05-27 4:45 PM
     */
    public int getConnectTimeOut() {
        return connectTimeOut;
    }
    public void setConnectTimeOut(int connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    /**
     * 方法说明：返回读入超时
     * 作者：heshuiguang
     * 日期：2020-05-27 4:45 PM
     */
    public int getReadTimeOut() {
        return readTimeOut;
    }
    public void setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    /**
     * 方法说明：网络状态校验（需要网络状态的权限）
     * 作者：heshuiguang
     * 日期：2020-05-27 4:45 PM
     */
    private boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable();
        }
        return false;
    }

    /**
     * 方法说明：发送请求；map类型
     * 作者：heshuiguang
     * 日期：2020-05-27 4:45 PM
     */
    public void sendRequest(ParamsBuild paramsBuild,String url,OnHttpRequestListener onHttpRequestListener){
        this.onHttpRequestListener = onHttpRequestListener;
        if(!isNetworkConnected()){
            onHttpRequestListener.onError("无网络");
            return ;
        }
        netConnectUtils = new NetConnectUtils(paramsBuild, url, getConnectType(),getContentType(),getConnectTimeOut(),getReadTimeOut(),this);
        netConnectUtils.startConnect();
    }


    /**
     * 方法说明：处理异常信息并回调出去
     * 作者：heshuiguang
     * 日期：2020-05-27 4:45 PM
     */
    @Override
    public void onException(String ecString) {
        callbackError(ecString);
    }

    /**
     * 方法说明：连接正常，拿到服务器返回数据进一步处理
     * 作者：heshuiguang
     * 日期：2020-05-27 4:46 PM
     */
    @Override
    public void onCompleted(String result) {
        if (CommonUtils.checkNullMethod(result)) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.optInt("rs");
                String msg = jsonObject.optString("msg");
                String data = jsonObject.optString("cn");
                if (code == 1) {//成功，返回数据
                    if(onHttpRequestListener != null)
                        onHttpRequestListener.onSuccess(msg,data);
                }else if (code == 2) {//后台把广告显示给关了,
                    callbackError(msg);
                }else{//其他情况失败
                    callbackError(msg);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                callbackError("返回的数据非json格式："+e.toString());
            }
        }else{
            callbackError("服务器没有返回任何数据："+result);
        }
    }

    /**
     * 方法说明：回调error
     * 作者：heshuiguang
     * 日期：2020-05-27 19:23
     */
    private void callbackError(String error){
        if(onHttpRequestListener != null)
            onHttpRequestListener.onError(error);
    }

}
