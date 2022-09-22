package com.game.adgamesdk.itf;

import java.util.Map;

/**
 * 作者: heshuiguang
 * 日期: 2019/4/22 1:21 PM
 * 类说明：权限申请回调接口类
 */
public interface OnPermissionResultListener {

    /**
     * 权限回调结果
     */
    void onPermissionReault(Map<String, Boolean> result);
}
