package com.game.adgamesdk.channel.ad;

import android.app.Activity;

import com.adv.callback.AdvFullScreenVideoListener;
import com.adv.sdk.AdSDK;
import com.game.adgamesdk.abs.HelperAbs;
import com.game.adgamesdk.entity.CallbackEnum;
import com.game.adgamesdk.itf.OnAdGameSdkCallback;
import com.game.adgamesdk.utils.CommonHandler;
import com.game.adgamesdk.utils.MyLog;

/**
 * 作者：heshuiguang
 * 日期：2020-05-28 11:08
 * 类说明：全屏视频广告接入类
 */
public class AdFullScreenHelper extends HelperAbs {

    private OnAdGameSdkCallback adGameSdkCallback;

    /**
     * 方法说明：加载全屏视频
     * 作者：heshuiguang
     * 日期：2020-05-28 15:23
     */
    public void loadAd(final Activity activity, final String adId, final OnAdGameSdkCallback callback) {
        this.adGameSdkCallback = callback;
        if (activity != null && !activity.isFinishing()){
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MyLog.hsgLog().i("加载全屏视频: adId = "+adId);
                    AdSDK.loadFullScreenVideoAd(activity, adId, new AdvFullScreenVideoListener() {
                        @Override
                        public void onError(int code, String message) {//全屏视频加载出错
                            String result = onResult(CommonHandler.CODE_OK,"全屏视频加载出错","code = "+code+", message = "+message);
                            CommonHandler.callbackResult(callback, CallbackEnum.VIDEO_LOAD_ERROR,result);
                        }
                        @Override
                        public void onFullScreenVideoCached() {//视频广告加载后，视频资源缓存到本地的回调，在此回调后，播放本地视频，流畅不阻塞
                            CommonHandler.callbackResult(callback,CallbackEnum.VIDEO_CACHE_SUCCESS,onResult(CommonHandler.CODE_OK,"全屏视频缓存本地成功",""));
                        }
                        @Override
                        public void onFullScreenVideoAdLoad() {//视频广告的素材加载完毕，比如视频url等，在此回调后，可以播放在线视频，网络不好可能出现加载缓冲，影响体验。
                            CommonHandler.callbackResult(callback,CallbackEnum.VIDEO_LOAD_SUCCESS,onResult(CommonHandler.CODE_OK,"全屏视频加载成功",""));
                        }
                        @Override
                        public void onAdShow() {//全屏视频展示
                            CommonHandler.callbackResult(callback,CallbackEnum.VIDEO_SHOW,onResult(CommonHandler.CODE_OK,"全屏视频展示",""));
                        }
                        @Override
                        public void onAdVideoClick() {//全屏视频点击
                            CommonHandler.callbackResult(callback,CallbackEnum.VIDEO_CLICK,onResult(CommonHandler.CODE_OK,"全屏视频点击",""));
                        }
                        @Override
                        public void onAdClose() {//全屏视频关闭
                            CommonHandler.callbackResult(callback,CallbackEnum.VIDEO_CLOSE,onResult(CommonHandler.CODE_OK,"全屏视频关闭",""));
                        }
                        @Override
                        public void onVideoComplete() {//全屏视频播放完成
                            CommonHandler.callbackResult(callback,CallbackEnum.VIDEO_PLAY_COMPLETE,onResult(CommonHandler.CODE_OK,"全屏视频播放完成",""));
                        }
                        @Override
                        public void onVideoError() {//全屏视频播放出错
                            CommonHandler.callbackResult(callback,CallbackEnum.VIDEO_PLAY_ERROR,onResult(CommonHandler.CODE_OK,"全屏视频播放出错",""));
                        }
                    });
                }
            });
        }else {
            CommonHandler.callbackResult(callback, CallbackEnum.VIDEO_LOAD_ERROR,onResult(CommonHandler.CODE_ACTIVITY_DESTORY,"activity不存在或被销毁",""));
        }
    }

    /**
     * 方法说明：播放全屏视频
     * 作者：heshuiguang
     * 日期：2020-05-28 15:23
     */
    public void playAd(Activity activity) {
        if (activity != null && !activity.isFinishing()){
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MyLog.hsgLog().i("播放全屏视频");
                    AdSDK.showFullScreenVideoAd();
                }
            });
        }else {
            CommonHandler.callbackResult(adGameSdkCallback, CallbackEnum.VIDEO_PLAY_ERROR,onResult(CommonHandler.CODE_ACTIVITY_DESTORY,"activity不存在或被销毁",""));
        }
    }




}
