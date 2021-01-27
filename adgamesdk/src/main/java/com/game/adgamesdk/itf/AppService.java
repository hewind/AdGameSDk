package com.game.adgamesdk.itf;

import android.app.Application;
import android.content.Context;

/**
 * 作者：heshuiguang
 * 日期：2020-05-28 17:13
 * 类说明：Application接口
 */
public interface AppService {

    //Application接口
    void onAppCreate(Application application);
    void onAppAttachBaseContext(Context context);

}
