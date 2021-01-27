package com.game.adgamesdk.impl;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.game.adgamesdk.FuncDispatcher;
import com.game.adgamesdk.itf.LifecycleService;

/**
 * 作者：heshuiguang
 * 日期：2020-05-28 10:59
 * 类说明：生命周期实现类
 */
public class LifecycleImpl implements LifecycleService {

    private final FuncDispatcher funcDispatcher;

    public LifecycleImpl(FuncDispatcher funcDispatcher) {
        this.funcDispatcher = funcDispatcher;
    }

    @Override
    public void onCreate(Activity activity) {
        funcDispatcher.onCreate(activity);
    }
    @Override
    public void onStart(Activity activity) {
        funcDispatcher.onStart(activity);
    }
    @Override
    public void onRestart(Activity activity) {
        funcDispatcher.onRestart(activity);
    }
    @Override
    public void onResume(Activity activity) {
        funcDispatcher.onResume(activity);
    }
    @Override
    public void onPause(Activity activity) {
        funcDispatcher.onPause(activity);
    }
    @Override
    public void onStop(Activity activity) {
        funcDispatcher.onStop(activity);
    }
    @Override
    public void onDestroy(Activity activity) {
        funcDispatcher.onDestroy(activity);
    }
    @Override
    public void onNewIntent(Activity activity, Intent intent) {
        funcDispatcher.onNewIntent(activity,intent);
    }
    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        funcDispatcher.onActivityResult(activity,requestCode,resultCode,data);
    }
    @Override
    public void onRequestPermissionsResult(Activity activity, int requestCode, String[] permissions, int[] grantResults) {
        funcDispatcher.onRequestPermissionsResult(activity,requestCode,permissions,grantResults);
    }
    @Override
    public void onConfigurationChanged(Activity activity, Configuration newConfig) {
        funcDispatcher.onConfigurationChanged(activity,newConfig);
    }
    @Override
    public void onSaveInstanceState(Activity activity, Bundle outState) {
        funcDispatcher.onSaveInstanceState(activity,outState);
    }
    @Override
    public void onRestoreInstanceState(Activity activity, Bundle savedInstanceState) {
        funcDispatcher.onRestoreInstanceState(activity,savedInstanceState);
    }
}
