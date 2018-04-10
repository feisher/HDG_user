//package com.yusong.yslib.utils;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.Context;
//import android.media.AudioManager;
//
//import com.baidu.tts.auth.AuthInfo;
//import com.baidu.tts.client.SpeechSynthesizer;
//import com.baidu.tts.client.SpeechSynthesizerListener;
//import com.baidu.tts.client.TtsMode;
//import com.lzy.okgo.utils.OkLogger;
//import com.tbruyelle.rxpermissions2.RxPermissions;
//import com.yusong.hostbaselibrary.utils.AppUtils;
//
//import java.io.Serializable;
//
//import io.reactivex.annotations.NonNull;
//import io.reactivex.functions.Consumer;
//
///**
// * @authorfeisher on 2017/12/11.9:23
// * email:458079442@qq.com
// */
//
//public class TTsUtil implements Serializable{
//    private static String appId = "10492856";
//    private static String appKey = "Ygk3AlDlDg8dRDGiNoaZmGrm";
//    private static String secretKey = "OS8D4Ct0OGMdm9Uvf77BeyAbQXsPR88B";
//    private static TtsMode ttsMode = TtsMode.ONLINE;
//    protected static SpeechSynthesizer mSpeechSynthesizer = SpeechSynthesizer.getInstance();
//    private static TTsUtil tTsUtil;
//
//    /**
//     * android 6.0 以上需要动态申请权限
//     */
//    public static TTsUtil init(final Activity activity) {
//        if (AppUtils.isEmpty(tTsUtil)) {
//            tTsUtil = new TTsUtil();
//        }
//        new RxPermissions(activity)
//                .request(Manifest.permission.MODIFY_AUDIO_SETTINGS,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .subscribe(new Consumer<Boolean>() {
//                    @Override
//                    public void accept(@NonNull Boolean aBoolean) throws Exception {
//                        if (aBoolean) {
//                            initTTs(activity);
//                        } else {
////                            TastyToast.makeText(activity,"请授与应用语音权限后重试",TastyToast.LENGTH_SHORT, TastyToast.WARNING).show();
//                        }
//                    }
//                });
//
//        return  tTsUtil;
//    }
//
//    public static  TTsUtil initTTs(final Context activity){
//        if (AppUtils.isEmpty(tTsUtil)) {
//            tTsUtil = new TTsUtil();
//        }
//        SpeechSynthesizerListener listener = new MessageListener(); // 日志更新在UI中，可以换成MessageListener，在logcat中查看日志
//        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
//        mSpeechSynthesizer.setContext(activity);
//        mSpeechSynthesizer.setSpeechSynthesizerListener(listener);
//        int result =  mSpeechSynthesizer.setAppId(appId);
//        result = mSpeechSynthesizer.setApiKey(appKey, secretKey);
//        mSpeechSynthesizer.setAudioStreamType(AudioManager.MODE_IN_CALL);
//        result = mSpeechSynthesizer.initTts(ttsMode);
//        return  tTsUtil;
//    }
//    public  void speak(String content) {
//        try {
//            if (!AppUtils.isEmpty(mSpeechSynthesizer)) {
//                mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "9");
////                mSpeechSynthesizer.stop();
//                int result = mSpeechSynthesizer.speak(content);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public  void stop() {
//        int result = mSpeechSynthesizer.stop();
//    }
//     public static void release() {
//         if (mSpeechSynthesizer != null){
//             mSpeechSynthesizer.stop();
//             mSpeechSynthesizer.release();
//             mSpeechSynthesizer = null;
//         }
//    }
//
//    /**
//     * 检查appId ak sk 是否填写正确，另外检查官网应用内设置的包名是否与运行时的包名一致。本demo的包名定义在build.gradle文件中
//     * @return
//     */
//    private boolean checkAuth() {
//        AuthInfo authInfo = mSpeechSynthesizer.auth(ttsMode);
//        if (!authInfo.isSuccess()) {
//            String errorMsg = authInfo.getTtsError().getDetailMessage();
//            OkLogger.d("【error】鉴权失败 errorMsg=" + errorMsg);
//            return false;
//        } else {
//            OkLogger.d("验证通过，离线正式授权文件存在。");
//            return true;
//        }
//    }
//
//
//
//
//}
