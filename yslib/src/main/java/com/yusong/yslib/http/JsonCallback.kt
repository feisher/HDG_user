package com.yusong.yslib.http


import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.stream.JsonReader
import com.lzy.okgo.callback.AbsCallback
import com.lzy.okgo.exception.HttpException
import com.lzy.okgo.request.base.Request
import com.sdsmdg.tastytoast.TastyToast
import com.yusong.yslib.App
import com.yusong.yslib.App.Companion.toast
import com.yusong.yslib.event.EventCenter
import com.yusong.yslib.utils.SPUtils


import org.greenrobot.eventbus.EventBus

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

import okhttp3.Response
import okhttp3.ResponseBody


/**
 * Created by feisher on 2018/1/26.
 */

abstract class JsonCallback<T> : AbsCallback<T>() {

    private var rawType: Type? = null

    override fun onStart(request: Request<T, out Request<*, *>>?) {
        super.onStart(request)
        request!!.headers("Authorization", "Bearer " + SPUtils.get(App.context, "token", ""))
    }

    override fun onError(response: com.lzy.okgo.model.Response<T>) {
        super.onError(response)
        val exception = response.exception
        exception?.printStackTrace()
        if (exception is UnknownHostException || exception is ConnectException) {
            toast("连接失败，请检查网络！")
        } else if (exception is SocketTimeoutException) {
            toast("网络连接超时！")
        } else if (exception is HttpException) {
            toast(exception.code().toString() + "服务器开小差了！")
        } else {
            toast("" + exception!!.message)
        }
    }

    @Throws(Throwable::class)
    override fun convertResponse(response: Response): T? {
        //获取到泛型真实类型
        val genType = javaClass.genericSuperclass
        val params = (genType as ParameterizedType).actualTypeArguments
        val type = params[0]
        if (type !is ParameterizedType) {
            //无封装HttpResult<T>，直接填写了一个对象的情况
            //            throw new IllegalAccessError("没有填写泛型参数");
            rawType = type
        } else {
            //获取到包装HttpResult<T>的data的真实类型
            rawType = type.rawType
//            val typeArgument = type.actualTypeArguments[0]
        }


        val body = response.body() ?: return null
        val jsonReader = JsonReader(body.charStream())
        if (rawType !== HttpResult::class.java) {
            val data = GsonUtil.fromJson<T>(jsonReader, type)
            response.close()
            return data
        } else {
            val o = GsonUtil.fromJson<HttpResult<*>>(jsonReader, type)
            response.close()
            val code = o!!.code
            if (code == 0 || code == 5 || code == 6) {
                return o as T
            } else if (code == 3) {
                toast("登陆超时",TastyToast.WARNING)
                ARouter.getInstance().build("/login/LoginActivity").navigation()
                throw IllegalStateException("" + o.message!!)
            } else if (code == 7) {
                toast("账号在其他设备登陆",TastyToast.WARNING)
                ARouter.getInstance().build("/login/LoginActivity").navigation()
                throw IllegalStateException("" + o.message!!)//账号在其他设备登陆
            } else {
                throw IllegalStateException("" + o.message!!)
            }
        }
    }
}
