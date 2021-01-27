package com.game.adgamesdk;

import android.app.Application;
import android.content.Context;

/**
 * 作者：heshuiguang
 * 日期：2020-05-27 14:41
 * 类说明：Application类
 */
public class GameSDKApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        GameSDKPlatform.app().onAppCreate(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        GameSDKPlatform.app().onAppAttachBaseContext(base);
    }



}
