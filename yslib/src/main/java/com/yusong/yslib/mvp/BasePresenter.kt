package com.yusong.yslib.mvp

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

interface BasePresenter<V : BaseView> {
    fun attachView(view: V)
    fun detachView()

}
