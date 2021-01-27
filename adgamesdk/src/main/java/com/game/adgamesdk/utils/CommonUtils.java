package com.game.adgamesdk.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.FileUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.adv.sdk.AdSDK;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

/**
 * 作者：heshuiguang
 * 日期：2019-04-23 14:16
 * 类说明：工具类
 */
public class CommonUtils {

    //------------------------参数字段名key------------------------------------
    public static final String ADSDK_GDT_ACTION_ID = "ADSDK_GDT_ACTION_ID";//投放平台；广点通 行为数据源ID
    public static final String ADSDK_GDT_SCERET_KEY = "ADSDK_GDT_SCERET_KEY";//投放平台；广点通 AppSecretKey


    public static final String ADSDK_TT_APPID = "ADSDK_TT_APPID";//投放平台；头条 APPID
    public static final String ADSDK_TT_CHANNEL = "ADSDK_TT_CHANNEL";//投放平台；头条 当前包的渠道，没有即使用default




    /**
     * 方法说明：
     * 作者：heshuiguang
     * 日期：2019-04-23 14:25
     */
    public static boolean checkNullMethod(String arg0) {
        if (arg0 == null)
            return false;

        arg0 = arg0.trim();
        if (arg0 != null && arg0.length() != 0 && !arg0.equals("null") && !arg0.equals("")) {
            return true;
        }
        return false;
    }

    /**
     * 方法说明：获取应用程序名称
     * 作者：heshuiguang
     * 日期：2019-04-23 14:19
     */
    public static String getAppName(Context context){
        try{
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 方法说明：获取当前应用版本号
     * 作者：heshuiguang
     * 日期：2019/4/9 7:43 PM
     */
    public static int getAppVersionCode(Context context) {
        int versionCode = 0;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = pi.versionCode;
        } catch (Exception e) {
        }
        return versionCode;
    }

    /**
     * 方法说明：获取当前版本名称
     * 作者：heshuiguang
     * 日期：2019/4/9 7:43 PM
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
        } catch (Exception e) {
        }
        return versionName;
    }

    /**
     * 方法说明：获得网络类型
     * 作者：heshuiguang
     * 日期：2019/4/9 7:43 PM
     */
    public  static String getNetworkType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo!=null)
            return networkInfo.getTypeName();
        else{
            return "NoNetwork";
        }

    }

    /**
     * 方法说明：获取清单文件中Application节点下meta_data内容 string
     * 作者：heshuiguang
     * 日期：2019-04-23 16:35
     */
    public static String getMetaDataString(Context activity,String key){
        ApplicationInfo appInfo;
        try {
            appInfo = activity.getPackageManager().getApplicationInfo(activity.getPackageName(),PackageManager.GET_META_DATA);
            String msg=appInfo.metaData.getString(key);
            return msg;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 方法说明：获取清单文件中Application节点下meta_data内容 int
     * 作者：heshuiguang
     * 日期：2019-04-23 16:39
     */
    public static int getMetaDataInt(Context activity,String key){
        ApplicationInfo appInfo;
        try {
            appInfo = activity.getPackageManager().getApplicationInfo(activity.getPackageName(),PackageManager.GET_META_DATA);
            int msg = appInfo.metaData.getInt(key);
            return msg;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 方法说明：获取当前正在运行的进程名称
     * 作者：heshuiguang
     * 日期：2019-04-24 10:00
     */
    public static String getProcessName(Context context){
        String sCurProcessName = null;
        try{
            int pid = android.os.Process.myPid();
            ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
                if (appProcess.pid == pid) {
                    sCurProcessName = appProcess.processName;
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return sCurProcessName;
    }



    /**
     * 方法说明：通过反射获取类对象
     * 作者：heshuiguang
     * 日期：2019-05-08 20:19
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static Object getObject(String className){
        Class c = null;
        try {
            c = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (c == null) {
            MyLog.hsgLog().e(className+" 类对象获取失败！！！");
            return null;
        }
        Object object = null;
        try {
            object = c.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return object;
    }

    /**
     * 方法说明：获取SHA1值
     * 作者：heshuiguang
     * 日期：2019-07-01 10:29
     */
    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length()-1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 方法说明：dp转为px
     * 作者：heshuiguang
     * 日期：2019-07-31 15:28
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * 方法说明：px转成为dp
     * 作者：heshuiguang
     * 日期：2019-08-09 18:10
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 方法说明：MD5加密
     * 作者：heshuiguang
     * 日期：2019/4/9 7:36 PM
     */
    public static String md5(String string) {
        if (!checkNullMethod(string)) {
            return null;
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            if(result.length()<24){
                return result.toString();
            }
            return result.substring(0,32);//为了提高磁盘的查找文件速度，让文件名为16位
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 方法说明：将json串转为map类型
     * 作者：heshuiguang
     * 日期：2019-11-04 17:23
     */
    public static HashMap getMap(String json){
        if(!CommonUtils.checkNullMethod(json)) return null;
        HashMap<String, String> map = new HashMap<>();
        try {
            JSONObject object = new JSONObject(json);
            Iterator<String> it = object.keys();
            while (it.hasNext()){
                String key = it.next();
                String value = object.optString(key);
                map.put(key,value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 方法说明：读取配置文件内容
     * 作者：heshuiguang
     * 日期：2020-06-03 16:09
     */
    private static String readAssetsContent(Context context) {
        InputStream inputStream = context.getClass().getResourceAsStream( "/assets/wxsdk.ini");
        MyLog.hsgLog().i( "inputStream = "+inputStream );
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[4096];
        try {
            int count;
            while((count = inputStream.read(data, 0, 4096)) != -1) {
                outStream.write(data, 0, count);
            }
            outStream.flush();
            outStream.close();
            inputStream.close();
            return new String(outStream.toByteArray(), "utf-8");
        } catch (IOException var7) {
            var7.printStackTrace();
            return null;
        }
    }

    public static String getConfigValue(Context context,String key){
        String str = readAssetsContent(context);
        try {
            JSONObject jsonObject = new JSONObject( str );
            return jsonObject.optString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 方法说明：获取屏幕分辨率宽
     * 作者：heshuiguang
     * 日期：2020-06-11 18:07
     */
    public static int getWindowWidth(Context context){
        WindowManager wm = (WindowManager) (context.getSystemService(Context.WINDOW_SERVICE));
        DisplayMetrics metrics =new DisplayMetrics();
        wm.getDefaultDisplay().getRealMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * 方法说明：获取屏幕分辨率高
     * 作者：heshuiguang
     * 日期：2020-06-11 18:07
     */
    public static int getWindowHeigh(Context context){
        WindowManager wm = (WindowManager) (context.getSystemService(Context.WINDOW_SERVICE));
        DisplayMetrics metrics =new DisplayMetrics();
        wm.getDefaultDisplay().getRealMetrics(metrics);
        return metrics.heightPixels;
    }

    /**
     * 方法说明：生成sign值
     * 作者：heshuiguang
     * 日期：2020-06-12 11:04
     */
    public static String getSign(HashMap<String, String> map, String site, String time){
        StringBuilder currenSign = new StringBuilder();
        String key = AdSDK.getString("houlang_key",null);

        Object[] keys = map.keySet().toArray();
        Arrays.sort(keys);
        //拼接字符串
        for (int i=0; i<keys.length; i++){
            currenSign .append(keys[i]).append(map.get(keys[i]));
        }
        MyLog.hsgLog().i("排序字符串 = "+currenSign);

        //将字符串首尾添加site、time、key
        String sign;
        sign = time + site + currenSign + key + time;
        MyLog.hsgLog().i("拼接site、time、key = "+sign);
        //md5加密
        sign = md5(sign);
        MyLog.hsgLog().i("sign = "+sign);
        return sign;
    }

    /**
     * 方法说明：拼接支付地址
     * 作者：heshuiguang
     * 日期：2020-06-12 10:41
     */
    public static String getUrl(HashMap<String,String> map) {
        String url = "http://recharge.youxirs.com/h/p?";
        for(String key:map.keySet()){
            url += key + "=" + map.get(key) + "&";
        }
        url = url.substring(0,url.length()-1);
        MyLog.hsgLog().i("url = "+url);
        return url;
    }









}
