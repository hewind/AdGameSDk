package com.game.adgamesdk.itf;

/**
 * 作者：heshuiguang
 * 日期：2020-05-27 4:01 PM
 * 类说明：请求服务器业务回调接口
 */
public interface OnNetConnectListener {


    /**
     * 方法说明：服务器连接成功返回数据
     * 作者：heshuiguang
     * 日期：2020-05-27 4:03 PM
     */
    void onCompleted(String result);

    /**
     * 方法说明：服务器连接失败：各种异常信息
     * 作者：heshuiguang
     * 日期：2020-05-27 4:04 PM
     */
    void onException(String exc);

}
