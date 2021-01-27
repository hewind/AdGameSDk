package com.game.adgamesdk;

import com.game.adgamesdk.impl.CoreImpl;
import com.game.adgamesdk.itf.AdGameService;
import com.game.adgamesdk.itf.AppService;
import com.game.adgamesdk.itf.CoreService;
import com.game.adgamesdk.itf.LifecycleService;


/**
 * 作者：heshuiguang
 * 日期：2020-05-28 10:36
 * 类说明：cp接口调用入口
 */
public class GameSDKPlatform {

    private static CoreService coreService = new CoreImpl();

    public static AppService app(){
        return coreService.getAppService();
    }

    public static AdGameService game(){
        return coreService.getAdGameService();
    }

    public static LifecycleService lifecycle(){
        return coreService.getLifecycleService();
    }

}
