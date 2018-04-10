package com.yusong.yslib

import android.app.Activity
import android.app.Application
import android.app.Fragment
import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import com.sdsmdg.tastytoast.TastyToast

var gToast: Toast? = null
/**
 *
 * Created by Administrator on 2018/3/31/031.
 */
fun Context.toast(message: String, type: Int) {
    if (gToast != null) {
        gToast!!.cancel()
    }
    gToast = TastyToast.makeText(this, message , 0, type)
    gToast!!.show()
}

fun  Context.toast(message: String) {
    toast(message, TastyToast.DEFAULT)
}

//扩展属性
var Context.app: App
    get() {
        return applicationContext as App
    }
    set(value) {
        app = value
    }
//扩展方法
inline fun <reified T : Any> Gson.fromJson(json: String?): T {
    return Gson().fromJson(json, T::class.java)
}



