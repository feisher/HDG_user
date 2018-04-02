package com.yusong.yslib

import android.app.Activity
import android.app.Application
import android.app.Fragment
import android.content.Context
import android.view.View
import android.widget.Toast
import com.sdsmdg.tastytoast.TastyToast
import com.yusong.yslib.mvp.BasePresenterImpl

var gToast: Toast? = null
/**
 *
 * Created by Administrator on 2018/3/31/031.
 */
fun Application.toast(message: String, type: Int) {
    if (gToast != null) {
        gToast!!.cancel()
    }
    gToast = TastyToast.makeText(this, message , 0, type)
    gToast!!.show()
}

fun  Application.toast(message: String) {
    toast(message, TastyToast.DEFAULT)
}

fun Activity.toast(message: String, type: Int) {
    if (gToast != null) {
        gToast!!.cancel()
    }
    gToast = TastyToast.makeText(this, message , 0, type)
    gToast!!.show()
}

fun  Activity.toast(message: String) {
    toast(message, TastyToast.DEFAULT)
}
fun Fragment.toast(message: String, type: Int) {
    if (gToast != null) {
        gToast!!.cancel()
    }
    gToast = TastyToast.makeText(this.activity, message , 0, type)
    gToast!!.show()
}

fun  Fragment.toast(message: String) {
    toast(message, TastyToast.DEFAULT)
}
var Context.app: ConfigApplication
    get() {
        return applicationContext as ConfigApplication
    }
    set(value) {
        app = value
    }



