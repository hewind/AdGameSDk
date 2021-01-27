package com.game.adgamesdk.abs;

import com.game.adgamesdk.GameConfig;
import com.game.adgamesdk.utils.CommonHandler;

/**
 * 作者：heshuiguang
 * 日期：2020-05-28 15:08
 * 类说明：功能接口辅助类
 */
public abstract class HelperAbs {

    /**
     * 方法说明：回调方法中的result参数
     * 作者：heshuiguang
     * 日期：2020-05-28 15:21
     */
    public String onResult(int code,String msg,String data){
        return CommonHandler.convertResult(code,msg,data);
    }

}
