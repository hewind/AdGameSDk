package com.game.adgamesdk.itf;

/**
 * 作者：heshuiguang
 * 日期：2020-05-27 3:53 PM
 * 类说明：封装联网访问返回结果的接口
 */
public interface OnHttpRequestListener {

    /**
     * 方法说明：数据处理校验成功，可以返回给调用者
     * 作者：heshuiguang
     * 日期：2020-05-27 3:55 PM
     */
    void onSuccess(String msg, String result);

    /**
     * 方法说明：返回结果错误，包括服务器访问失败、数据解析失败、网络原因等
     * 作者：heshuiguang
     * 日期：2020-05-27 3:57 PM
     */
    void onError(String msg);

}
