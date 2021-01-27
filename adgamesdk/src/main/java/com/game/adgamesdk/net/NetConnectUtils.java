package com.game.adgamesdk.net;


import com.game.adgamesdk.itf.OnNetConnectListener;
import com.game.adgamesdk.utils.MyLog;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 作者：heshuiguang
 * 日期：2020-05-27 4:16 PM
 * 类说明：与服务器交互业务逻辑，使用httpURLconnection建立连接
 */
public class NetConnectUtils {

    private String mUrl;
    private OnNetConnectListener httpConnectCallback;
    private String json = "";
    private String connectType;//连接服务器类型
    private String contenType;//提交服务器数据类型
    private final int MAX_RETRY_SUM = 3;//重新尝试连接服务器次数
    private int connectTimeOut;
    private int readTimeOut;

    public NetConnectUtils(ParamsBuild paramsBuild, String url, String connectType, String contenType, int connectTimeOut,
                           int readTimeOut, OnNetConnectListener onNetConnectCallback) {
        super();
        this.mUrl = UrlUtils.getAddress + url;
        this.connectType = connectType;
        this.contenType = contenType;
        this.connectTimeOut = connectTimeOut;
        this.readTimeOut = readTimeOut;
        this.httpConnectCallback = onNetConnectCallback;
        //get请求
        if(connectType.equals(HttpRequest.GET)){
            //如果是get请求，参数也按照post的方式放入paramsbuild中统一拼接
            if (paramsBuild != null && paramsBuild.size() > 0) {
                String str = "";
                for(String key:paramsBuild.keySet()){
                    str += key + "=" + paramsBuild.get(key) + "&";
                }
                str = str.substring(0,str.length()-1);
                mUrl += "?" +str;
            }
        }
        //post请求
        else if(connectType.equals(HttpRequest.POST)){
            //如果是form表单格式请求
            if(contenType.equals(HttpRequest.CONTENT_TYPE_FORM)){
                for(String key:paramsBuild.keySet()){
                    json += key + "=" + paramsBuild.get(key) + "&";
                }
                json = json.substring(0, json.length()-1);
            }
            //如果是json格式请求
            else if(contenType.equals(HttpRequest.CONTENT_TYPE_JSON)){
                json = new JSONObject(paramsBuild).toString();
            }
        }
        MyLog.hsgLog().i("url = "+mUrl);
        MyLog.hsgLog().i("content = "+json);
    }


    /**
     * 方法说明：开始连接服务器
     * 作者：heshuiguang
     * 日期：2020-05-27 4:37 PM
     */
    public void startConnect(){
        PollingStateMachine.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                transaction();
            }
        });
    }


    /**
     * 方法说明：发起联网操作请求
     * 作者：heshuiguang
     * 日期：2020-05-27 4:34 PM
     */
    private void transaction() {
        int reTrySum = 0;
        Exception exception = null;
        while(reTrySum < MAX_RETRY_SUM){
            try {
                // 新建一个URL对象
                URL url = new URL(mUrl);
                // 打开一个HttpURLConnection连接
                HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                // 设置连接超时时间
                urlConn.setConnectTimeout(connectTimeOut);
                //设置从主机读取数据超时
                urlConn.setReadTimeout(readTimeOut);
                // Post请求必须设置允许输出 默认false，get请求必须为false
                urlConn.setDoOutput(connectType.equals(HttpRequest.POST));
                //设置请求允许输入 默认是true
                urlConn.setDoInput(true);
                // Post请求不能使用缓存
                urlConn.setUseCaches(false);
                // 设置为请求方式、默认post
                urlConn.setRequestMethod(connectType);
                //设置本次连接是否自动处理重定向
                urlConn.setInstanceFollowRedirects(true);
                // 配置请求Content-Type
                urlConn.setRequestProperty("Content-Type", contenType);
                // 开始连接
                urlConn.connect();
                // 发送请求参数，以下为POST方式写入数据，GET忽略
                if(connectType.equals(HttpRequest.POST)){
                    DataOutputStream dos = new DataOutputStream(urlConn.getOutputStream());
                    dos.write(json.toString().getBytes());
                    dos.flush();
                    dos.close();
                }
                //连接状态码
                int code = urlConn.getResponseCode();
//                MyLog.hsgLog().i("code = "+code);
                // 判断请求是否成功
                switch (code) {
                    case 200:
                        // 获取返回的数据
                        String result = streamToString(urlConn.getInputStream());
                        MyLog.hsgLog().i("result = "+result);
                        httpConnectCallback.onCompleted(result);
                        return;
                    case 404:
                    case 500:
                        MyLog.hsgLog().i("连接服务器失败：code = " + code);
                        httpConnectCallback.onException("连接服务器失败：code = " + code);
                        return;
                    default:
                        //重新尝试连接服务器
                        reTrySum++;
                        MyLog.hsgLog().i("连接服务器失败：code = " + code + "，重新第" + reTrySum + "次尝试");
                        break;
                }
                // 关闭连接
                urlConn.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                exception = e;
                MyLog.hsgLog().i("Url对象创建失败："+e.toString());
                httpConnectCallback.onException("Url对象创建失败："+e.toString());
                return;
            }catch (IOException e) {
                e.printStackTrace();
                exception = e;
                reTrySum ++;
                MyLog.hsgLog().i("连接服务器异常："+e.toString()+"，重新第"+reTrySum+"次尝试");
                continue;
            }
        }
        //尝试多次连接服务器，仍然失败了
        if(exception != null){
            MyLog.hsgLog().i("尝试"+reTrySum+"次连接服务器仍然失败了："+exception.toString());
            httpConnectCallback.onException("连接服务器异常："+exception.toString());
        }else{
            MyLog.hsgLog().i("尝试"+reTrySum+"次连接服务器仍然失败了：服务器未知错误");
            httpConnectCallback.onException("连接服务器未知错误："+new Exception("unknown"));
        }
    }


    /**
     * 方法说明：将输入流转换成字符串
     * 作者：heshuiguang
     * 日期：2020-05-27 4:31 PM
     */
    public String streamToString(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();
            return new String(byteArray);
        } catch (Exception e) {
            MyLog.hsgLog().i("输出流读取异常："+e.toString());
            httpConnectCallback.onException("输出流读取异常："+e.toString());
            return null;
        }
    }

}
