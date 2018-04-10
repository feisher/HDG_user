//package com.yusong.yslib.utils;
//
//import android.content.Context;
//import android.content.Intent;
//
//import com.lzy.okgo.utils.OkLogger;
//import com.peng.one.push.OnePush;
//import com.peng.one.push.entity.OnePushCommand;
//import com.peng.one.push.entity.OnePushMsg;
//import com.peng.one.push.receiver.BaseOnePushReceiver;
//import com.yusong.dabanma.HostApplication;
//import com.yusong.dabanma.ui.messagelist.MessageListActivity;
//import com.yusong.hostbaselibrary.utils.AppUtils;
//
//import java.util.Map;
//
///**
// * @authorfeisher on 2017/12/5.15:39
// * email:458079442@qq.com
// */
//
//public class PushReceiver extends BaseOnePushReceiver {
//
//    @Override
//    public void onReceiveNotificationClick(Context context, OnePushMsg msg) {
//
//        Intent intent = new Intent(context, MessageListActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
//    }
//
//    @Override
//    public void onReceiveMessage(Context context, OnePushMsg msg) {
//        final String content = AppUtils.isEmpty(msg.getMsg())?AppUtils.isEmpty(msg.getContent())?msg.getTitle():msg.getContent():msg.getMsg();
//        Map<String, String> map = msg.getKeyValue();
//        if (!AppUtils.isEmpty(map)) {
//            if ("1".equals(map.get("isPlay"))) {
//                TTsUtil.initTTs(context).speak(content);
//            }
//        }
//        OkLogger.d("推送内容"+content);
//    }
//
//    @Override
//    public void onReceiveNotification(Context context, OnePushMsg msg) {
//        super.onReceiveNotification(context, msg);
//        final String content = AppUtils.isEmpty(msg.getMsg())?AppUtils.isEmpty(msg.getContent())?msg.getTitle():msg.getContent():msg.getMsg();
//        Map<String, String> map = msg.getKeyValue();
//        if (!AppUtils.isEmpty(map)) {
//            if ("1".equals(map.get("isPlay"))) {
//                TTsUtil.initTTs(context).speak(content);
//            }
//        }
//        OkLogger.d("推送内容"+content);
//    }
//
//    @Override
//    public void onCommandResult(Context context, OnePushCommand command) {
//        //注册消息推送失败，再次注册
//        if (command.getType() == OnePush.TYPE_REGISTER && command.getResultCode()== OnePush.RESULT_ERROR) {
//            OnePush.register();
//        }
//        if (AppUtils.isEmpty(HostApplication.pushToken)) {
//            HostApplication.pushToken = command.getToken();
//        }
//        if (!AppUtils.isEmpty(HostApplication.pushToken)) {
//            if ("1104a89792ac05a1bc0".length()< HostApplication.pushToken.length()){
//                HostApplication.pushType=2;
//            }else {
//                HostApplication.pushType=4;
//            }
//        }
//    }
//
//}
