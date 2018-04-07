package com.yusong.yslib.http;


import com.google.gson.stream.JsonReader;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.exception.HttpException;
import com.lzy.okgo.request.base.Request;
import com.yusong.yslib.App;
import com.yusong.yslib.event.EventCenter;
import com.yusong.yslib.utils.SPUtils;


import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * Created by feisher on 2018/1/26.
 */

public abstract class JsonCallback<T> extends AbsCallback<T> {

    private Type rawType;

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        request.headers("Authorization","Bearer "+ SPUtils.get(App.Companion.getContext(),"token",""));
    }
    @Override
    public void onError(com.lzy.okgo.model.Response<T> response) {
        super.onError(response);
        Throwable exception = response.getException();
        if (exception!=null) {
            exception.printStackTrace();
        }
        if (exception instanceof UnknownHostException || exception  instanceof ConnectException) {
            App.Companion.toast("连接失败，请检查网络！");
        }else if (exception instanceof SocketTimeoutException){
            App.Companion.toast("网络连接超时！");
        }else if (exception instanceof HttpException){
            App.Companion.toast(((HttpException) exception).code()+"服务器开小差了！");
        }else{
            App.Companion.toast(""+exception.getMessage());
        }
    }
    @Override
    public T convertResponse(Response response) throws Throwable {
        //获取到泛型真实类型
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
        if (!(type instanceof ParameterizedType)) {
            //无封装HttpResult<T>，直接填写了一个对象的情况
//            throw new IllegalAccessError("没有填写泛型参数");
        }else {
            //获取到包装HttpResult<T>的data的真实类型
            rawType = ((ParameterizedType) type).getRawType();
            Type typeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];
        }


        ResponseBody body = response.body();
        if (body == null) {
            return null;
        }
        JsonReader jsonReader = new JsonReader(body.charStream());
        if (rawType != HttpResult.class) {
            T data = GsonUtil.fromJson(jsonReader, type);
            response.close();
            return data;
        }else {
            HttpResult o = GsonUtil.fromJson(jsonReader, type);
            response.close();
            int code = o.code;
            if (code==0||code==5||code==6) {
                return (T)o;
            }else if(code ==3){
                EventBus.getDefault().post(new EventCenter<String>(EventCenter.TOKEN_INVALID,"登陆超时"));
                SPUtils.put(App.Companion.getContext(),"token", "");
                SPUtils.put(App.Companion.getContext(),SPUtils.K_SESSION_TIMEOUT, true);
                throw new IllegalStateException(""+o.message);
            }else if(code ==7){
                SPUtils.put(App.Companion.getContext(),SPUtils.K_SESSION_TIMEOUT, true);
                EventBus.getDefault().post(new EventCenter<String>(EventCenter.TOKEN_INVALID,"账号在其他设备登陆"));
                SPUtils.put(App.Companion.getContext(),"token", "");
                throw new IllegalStateException(""+o.message);//账号在其他设备登陆
            }else {
                throw new IllegalStateException(""+o.message);
            }
        }
    }
}
