package com.game.adgamesdk.net;

import android.app.Activity;

import java.util.HashMap;

/**
 * 作者：heshuiguang
 * 日期：2019/4/9 4:22 PM
 * 类说明：包装数据的载体
 */
public class ParamsBuild extends HashMap<String,String> {

    private static final long serialVersionUID = 1L;

    /**
     * 方法说明：封装请求数据体
     * 作者：heshuiguang
     * 日期：2019/4/9 4:24 PM
     */
    public static ParamsBuild build(){
        ParamsBuild params = new ParamsBuild();
        return params;
    }

    public static ParamsBuild build(Activity activity){

        ParamsBuild params = new ParamsBuild();

        return params;
    }

}
