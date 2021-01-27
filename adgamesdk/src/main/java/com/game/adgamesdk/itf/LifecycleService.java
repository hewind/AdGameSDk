package com.game.adgamesdk.itf;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

/**
 * 作者：heshuiguang
 * 日期：2020-05-27 18:12
 * 类说明：生命周期接口
 */
public interface LifecycleService {


    void onCreate(Activity activity);
    void onStart(Activity activity);
    void onRestart(Activity activity);
    void onResume(Activity activity);
    void onPause(Activity activity);
    void onStop(Activity activity);
    void onDestroy(Activity activity);
    void onNewIntent(Activity activity, Intent intent);
    void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data);
    void onRequestPermissionsResult(Activity activity, int requestCode, String[] permissions, int[] grantResults);
    void onConfigurationChanged(Activity activity, Configuration newConfig);
    void onSaveInstanceState(Activity activity, Bundle outState);
    void onRestoreInstanceState(Activity activity, Bundle savedInstanceState);

}
