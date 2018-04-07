package com.yusong.yslib

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.hss01248.dialog.ActivityStackManager
import com.yusong.yslib.utils.DensityHelper

/**
 * Created by Administrator on 2018/4/7 0007.
 */
class AcLifeCB : Application.ActivityLifecycleCallbacks{

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        App.activityStack?.add(activity)
        DensityHelper.resetDensity(App.instance, Const.DESIGN_WIDTH)
        DensityHelper.resetDensity(activity, Const.DESIGN_WIDTH)
    }
    override fun onActivityResumed(activity: Activity?) {
//在这里保存顶层activity的引用(内部以软引用实现)
        ActivityStackManager.getInstance().addActivity(activity)
    }
    override fun onActivityStarted(activity: Activity?) {}
    override fun onActivityPaused(activity: Activity?) { }
    override fun onActivityDestroyed(activity: Activity?) {
        ActivityStackManager.getInstance().removeActivity(activity)
        App.activityStack?.remove(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}

    override fun onActivityStopped(activity: Activity?) { }



}
