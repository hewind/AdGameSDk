package com.game.adgamesdk.impl;

import android.app.Application;
import android.content.Context;

import com.game.adgamesdk.FuncDispatcher;
import com.game.adgamesdk.itf.AppService;

/**
 * 作者：heshuiguang
 * 日期：2020-05-28 17:14
 * 类说明：Application实现类
 */
public class AppImpl implements AppService {

    private final FuncDispatcher funcDispatcher;

    public AppImpl(FuncDispatcher funcDispatcher) {
        this.funcDispatcher = funcDispatcher;
    }

    @Override
    public void onAppCreate(Application application) {
        funcDispatcher.onAppCreate(application);
    }
    @Override
    public void onAppAttachBaseContext(Context context) {
        funcDispatcher.onAppAttachBaseContext(context);
    }
}
