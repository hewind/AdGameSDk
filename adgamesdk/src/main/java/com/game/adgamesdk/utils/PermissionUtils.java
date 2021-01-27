package com.game.adgamesdk.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import com.game.adgamesdk.itf.OnPermissionResultListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 作者: heshuiguang
 * 日期: 2019/4/22 11:27 AM
 * 类说明：权限管理
 */
public class PermissionUtils {

    private Activity context;
    private OnPermissionResultListener onPermissionResultListener;
    private String[] myPermissions;
    private int requestCodes;
    private final int SHOW_DIALOG = 1;
    private final int GO_SETTING = 2;
    private Map<String,Boolean> permissionMap;
    private List<String> permissionList;

    public static final int PERMISSION_REQUEST_CODE = 1000;

    public PermissionUtils(Activity context) {
        this.context = context;
    }

    /**
     * 方法说明：申请权限之前做个判断，已经获取了权限返回true
     * 作者: heshuiguang
     * 日期: 2019/4/22 11:30 AM
     */
    public void getPermission(String[] permission, int requestCode, OnPermissionResultListener onPermissionResultCallback){
        this.onPermissionResultListener = onPermissionResultCallback;
        this.myPermissions = permission;
        this.requestCodes = requestCode;
        permissionMap = new HashMap<>();
        permissionList = new ArrayList<>();
        if (permission != null && permission.length != 0) {
            if (checkIsGetPermisson(permission)) {
                onBackListener();
            }else{
                boolean flag = true;
                for (int i=0; i<permissionList.size(); i++) {
                    //用户第一次申请该权限
                    if (ShareUtils.isFirstApplyPermission(context,permissionList.get(i))) {
                        getPermission();
                        flag = false;
                        break;
                    }else {
                        //如果再次申请，且上次申请权限时用户选择了拒绝(但没有勾选“下次不再询问”)，弹框提醒用户为什么SDK需要这个权限
                        if (checkIsSelectNotRemind(permissionList.get(i))) {
                            handler.sendEmptyMessage(SHOW_DIALOG);
                            flag = false;
                            break;
                        }
                    }
                }
                //这种情况是处理，用户再次申请权限，且上次申请权限时，所有权限用户都勾选了“下次不再询问”，这里直接回调出去
                if (flag) {
                    onBackListener();
                }
            }
        }else {
            onBackListener();
        }
    }

    /**
     * 方法说明：申请权限
     * 作者: heshuiguang
     * 日期: 2019/4/22 11:30 AM
     */
    private void getPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.requestPermissions(permissionList.toArray(new String[permissionList.size()]),requestCodes);
        }
    }

    /**
     * 方法说明：通知
     * 作者：heshuiguang
     * 日期：2019/4/22 11:30 AM
     */
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHOW_DIALOG:
                    showWarnDialog();
                    break;
                case GO_SETTING:
                    showGoSettingDialog();
                    break;
            }
        }
    };

    /**
     * 方法说明：再次申请授权之前先弹出一个提示框，提示用户尽量选择允许授权，否则游戏无法运行
     * 作者：heshuiguang
     * 日期：2019/4/22 11:31 AM
     */
    private void showWarnDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setCancelable(false);
        dialog.setTitle("温馨提示");
        dialog.setMessage("运行游戏需要获取您的设备权限，请在接下来的授权申请中选择允许授权！");
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getPermission();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onBackListener();
            }
        });
        dialog.show();
    }

    /**
     * 方法说明：弹出去设置页开启权限提醒框
     * 作者：heshuiguang
     * 日期：2019/4/22 11:31 AM
     */
    private void showGoSettingDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("温馨提示");
        dialog.setCancelable(false);
        dialog.setMessage("获取权限失败，是否去设置页开启权限？");
        dialog.setPositiveButton("去开启", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                goSetting();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onBackListener();
            }
        });
        dialog.show();
    }

    /**
     * 方法说明：处理申请权限的回调
     * 作者：heshuiguang
     * 日期：2019/4/22 11:31 AM
     */
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == requestCodes) {
            boolean hasPermissionDismiss = true;
            for (int i=0; i<grantResults.length; i++) {
                //改为非第一次获取该权限
                ShareUtils.setIsFirstApplyPermission(context,permissions[i],false);
                //允许授权
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    permissionMap.put(permissions[i],true);
                    permissionList.remove(permissions[i]);
                }else {
                    hasPermissionDismiss = false;
                }
            }
            //检查是否全部获取了权限，如果部分没有获取，即提示用户去设置页开启权限
            if (hasPermissionDismiss) {
                onBackListener();
            }else {
                handler.sendEmptyMessage(GO_SETTING);
            }
        }
    }

    /**
     * 方法说明：activity页面跳转后的回调
     * 作者：heshuiguang
     * 日期：2019/4/22 11:31 AM
     */
    @TargetApi(Build.VERSION_CODES.M)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        MyLog.hsgLog().i("requestCodes2 = "+requestCodes +", requestCode = "+requestCode);
        if (requestCode == requestCodes) {
            for (String s : permissionList) {
                if (context.checkSelfPermission(s) == PackageManager.PERMISSION_GRANTED) {
                    permissionMap.put(s,true);
                }else {
                    permissionMap.put(s,false);
                }
            }
            onBackListener();
        }
    }

    /**
     * 方法说明：检查是否获取了权限
     * 作者：heshuiguang
     * 日期：2019/4/22 11:31 AM
     */
    private boolean checkIsGetPermisson(String[] permission){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            for (String s : permission) {
                permissionMap.put(s,true);
            }
            return true;
        }
        for (String s : permission) {
            if (context.checkSelfPermission(s) == PackageManager.PERMISSION_GRANTED) {
                permissionMap.put(s,true);
                MyLog.hsgLog().i("permissions = "+s +",  check = true");
            }else {
                permissionMap.put(s,false);
                permissionList.add(s);
                MyLog.hsgLog().i("permissions = "+s +",  check = false");
            }
        }
        if (permissionList.size() > 0) {
            return false;
        }
        return true;
    }

    /**
     * 方法说明：检查权限申请时是否勾选了不再提醒复选框，默认是false，但在申请权限时用户拒绝了权限申请（不勾选“不再提醒”复选框），则返回true，当勾选了“不再提醒”复选框时并点击了拒绝，则返回false
     * 作者：heshuiguang
     * 日期：2019/4/22 11:31 AM
     */
    private boolean checkIsSelectNotRemind(String permission){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.shouldShowRequestPermissionRationale(permission);
        }
        return false;
    }

    /**
     * 方法说明：去设置页开启权限
     * 作者：heshuiguang
     * 日期：2019/4/22 11:32 AM
     */
    private void goSetting(){
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivityForResult(intent, requestCodes);
    }

    private void onBackListener(){
        if(onPermissionResultListener != null){
            onPermissionResultListener.onPermissionReault(permissionMap);
        }
    }


}
