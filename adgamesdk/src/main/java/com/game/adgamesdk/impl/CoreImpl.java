package com.game.adgamesdk.impl;

import com.game.adgamesdk.FuncDispatcher;
import com.game.adgamesdk.itf.AdGameService;
import com.game.adgamesdk.itf.AppService;
import com.game.adgamesdk.itf.CoreService;
import com.game.adgamesdk.itf.LifecycleService;

/**
 * 作者：heshuiguang
 * 日期：2020-05-28 17:43
 * 类说明：各接口层统一调用类
 */
public class CoreImpl implements CoreService {

    private AppService appService;
    private AdGameService adGameService;
    private LifecycleService lifecycleService;

    public CoreImpl() {
        appService = new AppImpl(FuncDispatcher.getInstance());
        adGameService = new AdGameImpl(FuncDispatcher.getInstance());
        lifecycleService = new LifecycleImpl(FuncDispatcher.getInstance());
    }

    @Override
    public AppService getAppService() {
        return appService;
    }

    @Override
    public AdGameService getAdGameService() {
        return adGameService;
    }

    @Override
    public LifecycleService getLifecycleService() {
        return lifecycleService;
    }
}
