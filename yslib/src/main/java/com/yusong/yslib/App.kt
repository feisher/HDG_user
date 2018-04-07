package com.yusong.yslib

import android.app.Activity
import android.content.Context
import android.os.Environment
import android.support.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.hss01248.dialog.StyledDialog
import com.lzy.okgo.https.HttpsUtils
import com.yusong.yslib.utils.AppUtils
import okhttp3.OkHttpClient
import java.io.Serializable
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class App : MultiDexApplication(), Serializable {
    private val sslParams3: HttpsUtils.SSLParams? = null
    lateinit var okHttpClient: OkHttpClient

    override fun onCreate() {
        super.onCreate()
        instans = this
        context = instans!!.applicationContext
//        MultiDex.install(this)
//        //将可以延时初始化的框架放入服务中
//        MyIntentService.start(this)
//
//        val sslParams1 = HttpsUtils.getSslSocketFactory()
//        val builder = OkHttpClient.Builder()
//        builder.hostnameVerifier { hostname, session -> true }
//        val loggingInterceptor = HttpLoggingInterceptor("OkGo")
//        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY)
//        loggingInterceptor.setColorLevel(Level.INFO)
//        builder.addInterceptor(loggingInterceptor)
//        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager)
//        okHttpClient = builder.build()
//        OkGo.getInstance().init(this).okHttpClient = okHttpClient
        //        Stetho.initializeWithDefaults(this);
        CACHA_URL = if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) Environment.getExternalStorageDirectory().absolutePath else Environment.getDownloadCacheDirectory().absolutePath
        ARouter.init(this)
        initLifecycle()
//        DensityHelper(this, Const.DESIGN_WIDTH).activate()
    }


    private fun initLifecycle() {
        activityStack = Stack()
        StyledDialog.init(this)
        var alcb = AcLifeCB()
        registerActivityLifecycleCallbacks(alcb)
    }

    private object SingletonHolder {

        val INSTANCE = instans
    }

    companion object {
        var activityStack: Stack<Activity>? = null
        var poolExecutor: ExecutorService = ThreadPoolExecutor(3, 5,
                1, TimeUnit.SECONDS, LinkedBlockingDeque(128))
        var TOKEN_CREATE_TIME: Long = 0
        private var instans: App? = null
        var context: Context? = null
            private set
        lateinit var CACHA_URL: String

         val instance: App?
            get() = SingletonHolder.INSTANCE
        fun toast(s: String) {
            this.instance!!.toast(s)
        }
        fun toast(s: String,i: Int) {
            this.instance!!.toast(s,i)
        }
        // 遍历所有Activity并finish
        fun exit(isRealExit: Boolean) {
            if (!AppUtils.isEmpty(activityStack)) {
                if (activityStack != null) {
                    for (act in activityStack!!) {
                        act.finish()
                    }
                }
                if (isRealExit) {
                    //当点击退出登录和token超时不是真的退出
                    android.os.Process.killProcess(android.os.Process.myPid())
                    System.exit(0)
                }

            }
        }
    }
}
