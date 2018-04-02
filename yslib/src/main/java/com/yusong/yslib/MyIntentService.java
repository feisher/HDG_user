package com.yusong.yslib;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.wanjian.cockroach.Cockroach;

/**  优化应用启动速度
 * create by feisher on 2017/8/9
 * Email：458079442@qq.com
 */
public class MyIntentService extends IntentService {

    public MyIntentService() {
        super(Const.Companion.getACTION_INIT_APP());
    }

    public static  void start(Context context){
        Intent intent = new Intent(context,MyIntentService.class);
        intent.setAction(Const.Companion.getACTION_INIT_APP());
        context.startService(intent);
    }

    /**
     * 处理第三方框架等操作的初始化
     * @param intent
     */
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (action.equals(Const.Companion.getACTION_INIT_APP())) {
                performInit();
            }
        }
    }
    public void performInit() {
        Context mContext = getApplicationContext();
        Cockroach.install(new Cockroach.ExceptionHandler() {
            @Override
            public void handlerException(final Thread thread, final Throwable throwable) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //建议使用下面方式在控制台打印异常，这样就可以在Error级别看到红色log
                            Log.e("AndroidRuntime", "--->CockroachException:" + thread + "<---", throwable);
                        } catch (Throwable e) {

                        }
                    }
                });
            }
        });
    }
}
