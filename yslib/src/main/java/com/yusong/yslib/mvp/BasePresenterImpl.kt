package com.yusong.yslib.mvp

import android.app.Activity
import android.content.Context
import android.widget.Toast

import com.hss01248.dialog.StyledDialog
import com.sdsmdg.tastytoast.TastyToast

import com.yusong.yslib.ConfigApplication
import com.yusong.yslib.event.EventCenter
import com.yusong.yslib.toast

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

import java.lang.ref.Reference
import java.lang.ref.WeakReference


/**改写MVPPlus插件presenter基类实现和rxJava 结合
 * create by ds on 2017/6/13 13:13
 * Email：458079442@qq.com
 */
abstract class BasePresenterImpl<V : BaseView> : BasePresenter<V> {
    lateinit var mView: V
    protected lateinit var mContext: Context
    protected var viewRef: Reference<V>? = null

    override fun attachView(view: V) {
        viewRef = WeakReference(view)
        mView = viewRef!!.get() as V
        mContext = view.context
        if (useEvenBus()) {
            EventBus.getDefault().register(this)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMainThread(center: EventCenter<*>?) {
        if (null != center) {
            if (center.eventCode == EventCenter.TOKEN_INVALID) {
                ConfigApplication.exit(false)
                try {
                    //                    YsRouter.gotoActivity((Activity) mContext,ConfigApplication.LOGIN_PATH,YsRouter.NORMAL,"",true);
                } catch (e: Exception) {
                }

            }
            onEventComing(center)
        }
    }

    /**
     * EventBus接收消息
     * @param center 获取事件总线信息
     */
    protected fun onEventComing(center: EventCenter<*>) {

    }

    override fun detachView() {
        if (useEvenBus()) {
            EventBus.getDefault().unregister(this)
        }
        if (viewRef != null) {
            viewRef!!.clear()
            viewRef = null
        }
    }

    protected fun useEvenBus(): Boolean {
        return true
    }

    companion object {
        fun showLoading() {
            StyledDialog.buildLoading().show()
        }
        fun hideLoading() {
            StyledDialog.dismissLoading()
        }
        fun toast(message: String, type: Int) {
                    ConfigApplication.instance!!.toast(message,type)
        }
        fun toast(message: String) {
            ConfigApplication.instance!!.toast(message,TastyToast.DEFAULT)
        }
    }

}
