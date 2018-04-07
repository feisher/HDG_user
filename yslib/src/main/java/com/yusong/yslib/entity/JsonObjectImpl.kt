package com.yusong.yslib.entity

import android.content.Context
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.SerializationService
import com.google.gson.Gson
import java.lang.reflect.Type

@Route(path = "/service/json")
 class JsonObjectImpl : SerializationService {
    override fun <T : Any?> parseObject(input: String?, clazz: Type?): T {
       return Gson().fromJson(input, clazz)
    }

    override fun init(context: Context?) {

    }


    //json字符串转换为对象
    override fun <T : Any?> json2Object(json: String?, clazz: Class<T>?): T {
        Log.e("@@","json:"+json)
        return Gson().fromJson(json, clazz)
    }


    //自定义对象转换为json字符串
    override fun object2Json(instance: Any?): String = Gson().toJson(instance)
}