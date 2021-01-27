package com.game.adgamesdk.itf;

import com.game.adgamesdk.entity.CallbackEnum;

/**
 * 作者：heshuiguang
 * 日期：2020-05-27 15:19
 * 类说明：
 */
public interface OnAdGameSdkCallback {


    void onCallback(CallbackEnum callbackEnum, String msg);

}
