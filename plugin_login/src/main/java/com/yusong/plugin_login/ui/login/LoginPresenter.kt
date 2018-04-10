package com.yusong.plugin_login.ui.login


import android.graphics.Color
import android.os.CountDownTimer
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.google.gson.Gson
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.FileCallback
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import com.lzy.okgo.utils.OkLogger
import com.sdsmdg.tastytoast.TastyToast
import com.yusong.plugin_login.R
import com.yusong.plugin_login.entity.g.GetCodeUrl
import com.yusong.plugin_login.entity.s.Login
import com.yusong.plugin_login.entity.s.LoginByAuthCode
import com.yusong.plugin_login.entity.s.PushToken
import com.yusong.plugin_login.entity.s.SignGet
import com.yusong.yslib.Api

import com.yusong.yslib.App
import com.yusong.yslib.App.Companion.toast
import com.yusong.yslib.entity.Login1
import com.yusong.yslib.event.EventCenter
import com.yusong.yslib.http.GsonUtil
import com.yusong.yslib.http.HttpResult
import com.yusong.yslib.http.JsonCallback
import com.yusong.yslib.mvp.BasePresenterImpl
import com.yusong.yslib.utils.*

import org.json.JSONException
import org.json.JSONObject

import java.io.File

class LoginPresenter : BasePresenterImpl<LoginContract.View>(), LoginContract.Presenter {
    private var isAuthCodeLogin: Boolean = false
    private var timer: CountDownTimer? = null
    private var sign: String? = null

    override fun onEventComing(center: EventCenter<*>?) {
        super.onEventComing(center)
    }

    override fun getAuthCode(account: EditText, etImgAuthcode: EditText, tvAuthCode: TextView) {
        val mAccount = account.text.toString()
        val imgAuthcode = etImgAuthcode.text.toString()
        if (AppUtils.isEmpty(mAccount)) {
            toast("手机号不能为空", TastyToast.WARNING)
            return
        }
        if (AppUtils.isEmpty(imgAuthcode)) {
            toast("请输入图片验证码", TastyToast.WARNING)
            return
        }
        if (!RegexUtils.checkMobile(mAccount)) {
            toast("手机号不合法，请重新输入", TastyToast.ERROR)
            return
        }

        OkGo.post<String>(Api.SIGN_GET)
                .upJson(GsonUtil.toJson(SignGet(mAccount, sign, imgAuthcode)))
                .execute(object : StringCallback() {
                    override fun onSuccess(response: Response<String>) {
                        val body = response.body()
                        if (!AppUtils.isEmpty(body) && body.startsWith("{")) {
                            try {
                                val jsonObject = JSONObject(body)
                                val code = jsonObject.opt("code") as Int
                                if (code == 0) {
                                    timer = initCountDownTimer(tvAuthCode)
                                    timer!!.start()
                                    toast("验证码已发送", TastyToast.SUCCESS)
                                } else {
                                    toast(jsonObject.opt("message") as String)
                                }
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }

                        }
                    }
                })
    }

    override fun login(etAccount: EditText, etPwd: EditText, tvAuthcode: TextView) {

        val mAccount = etAccount.text.toString()
        val mPwd = etPwd.text.toString()
        if (AppUtils.isEmpty(mAccount)) {
            toast("请输入账号后重试", TastyToast.WARNING)
            return
        }
        if (AppUtils.isEmpty(mPwd)) {
            if (tvAuthcode.visibility == View.VISIBLE) {
                toast("请先输入验证码", TastyToast.WARNING)
            } else {
                toast("请先输入密码", TastyToast.WARNING)
            }
            return
        }
        if (tvAuthcode.visibility == View.VISIBLE) {
            OkGo.post<HttpResult<Login1>>(Api.LOGIN_BY_CODE)
                    .upJson(Gson().toJson(LoginByAuthCode(mAccount, mPwd)))
                    .execute(object : JsonCallback<HttpResult<Login1>>() {
                        override  fun onSuccess(response: Response<HttpResult<Login1>>) {
                            if (AppUtils.isEmpty(mView)) {
                                return
                            }
                            val body = response.body()
                            if (!AppUtils.isEmpty(body)) {
                                if (body.code === 0) {
                                    val data = body.data
                                    if (!AppUtils.isEmpty(data)) {
                                        val token = data.token
                                        val expireIn = data.expireIn
                                        val timeMillis = System.currentTimeMillis()
                                        App.TOKEN_CREATE_TIME = timeMillis
                                        SPUtils.put<String>(mView.context, SPUtils.K_TOKEN, token)
                                        SPUtils.put<Int>(mView.context, SPUtils.K_EXPIREIN, expireIn)
                                        SPUtils.put(mView.context, SPUtils.K_TOKENCREATETIME, timeMillis)
                                        toast("登录成功", TastyToast.SUCCESS)
                                        commitPushToken(App.pushType, App.pushToken)
                                        //                                        mView.gotoRealNameAuth();
                                    }
                                } else {
                                    toast(body.message)
                                }
                            }
                        }

                    })
        } else {

            OkGo.post<HttpResult<Login1>>(Api.LOGIN)
                    .upJson(GsonUtil.toJson(Login(mAccount, MD5Utils.md5Password(mPwd))))
                    .execute(object : JsonCallback<HttpResult<Login1>>() {
                        override fun onSuccess(response: Response<HttpResult<Login1>>) {
                            if (AppUtils.isEmpty(mView)) {
                                return
                            }
                            val body = response.body()

                            if (!AppUtils.isEmpty(body)) {
                                if (body.code === 0) {
                                    val data = body.data
                                    if (!AppUtils.isEmpty(data)) {
                                        val token = data.token
                                        val expireIn = data.expireIn
                                        val timeMillis = System.currentTimeMillis()
                                        App.TOKEN_CREATE_TIME = timeMillis
                                        SPUtils.put<String>(mView.context, SPUtils.K_TOKEN, token)
                                        SPUtils.put<Int>(mView.context, SPUtils.K_EXPIREIN, expireIn)
                                        SPUtils.put(mView.context, SPUtils.K_TOKENCREATETIME, timeMillis)

                                        toast("登录成功", TastyToast.SUCCESS)
                                        commitPushToken(App.pushType, App.pushToken)

                                        //                                        mView.gotoRealNameAuth();
                                    }
                                } else if (body.code === 6) {
                                    toast(body.message + ",请使用验证码登陆", TastyToast.WARNING)
                                    if (!AppUtils.isEmpty(mView)) {

                                        mView.invalidateUseAuth()
                                    }
                                    etPwd.setText("")
                                } else {
                                    if (!AppUtils.isEmpty(mView)) {

                                        toast(body.message, TastyToast.WARNING)
                                        mView.gotoPhoneAuth(mAccount)
                                    }
                                }
                            }

                        }
                    })
        }

    }

    /**
     * 上报推送标识
     * @param pushType
     * @param pushToken
     */
    private fun commitPushToken(pushType: Int, pushToken: String) {
        if (!AppUtils.isEmpty(timer)) {
            timer!!.cancel()
        }
        OkGo.post<HttpResult<Void>>(Api.REPORT_PUSH_TOKEN)
                .upJson(GsonUtil.toJson(PushToken(pushType, pushToken)))
                .execute(object : JsonCallback<HttpResult<Void>>() {
                    override fun onSuccess(response: Response<HttpResult<Void>>) {
                        SPUtils.put(App.context, SPUtils.K_SESSION_TIMEOUT, false)
                        OkLogger.d("上报推送标识成功:" + GsonUtil.toJson(PushToken(pushType, pushToken))!!)
                        if (!AppUtils.isEmpty(mView)) {
                            mView.gotoUserMain()
                        }
                    }
                })
    }

    private fun initCountDownTimer(tvAuthCode: TextView?): CountDownTimer {
        return object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (AppUtils.isEmpty(mView)) {
                    return
                }
                if (tvAuthCode != null) {
                    tvAuthCode.isEnabled = false
                    tvAuthCode.setTextColor(Color.GRAY)
                    tvAuthCode.setBackgroundResource(R.drawable.shape_round_rect_gray)
                    tvAuthCode.text = "重新发送(" + millisUntilFinished / 1000 + ")"
                }
            }

            override fun onFinish() {
                if (AppUtils.isEmpty(mView)) {
                    return
                }
                if (tvAuthCode != null) {
                    tvAuthCode.isEnabled = true
                    tvAuthCode.setBackgroundResource(R.drawable.shape_round_rect_yellow)
                    tvAuthCode.setTextColor(ContextCompat.getColor(mView.context, R.color.white))
                    tvAuthCode.text = "获取验证码"
                }
            }
        }
    }

    override fun invalidate(llImgAuthcode: LinearLayout, etAccount: EditText, etPwd: EditText, tvAuthcode: TextView, tvAuthcodeLogin: TextView, ivPwdAuthcode: ImageView) {
        isAuthCodeLogin = !isAuthCodeLogin
        if (isAuthCodeLogin) {
            //验证码登陆
            AnimUtils.clickAnimator(tvAuthcode)
            etPwd.hint = "输入验证码"
            ivPwdAuthcode.setBackgroundResource(R.mipmap.ic_sms)
            tvAuthcode.visibility = View.VISIBLE
            tvAuthcodeLogin.text = "账号密码登陆"
            llImgAuthcode.visibility = View.VISIBLE
            getImageAuthCode()
        } else {
            //密码登陆
            AnimUtils.alphaOutAnimator(tvAuthcode, AnimUtils.DEFULT_DURATION)
            etPwd.hint = "输入密码"
            ivPwdAuthcode.setBackgroundResource(R.mipmap.ic_password)
            tvAuthcode.visibility = View.GONE
            tvAuthcodeLogin.text = "短信验证登陆"
            llImgAuthcode.visibility = View.GONE
        }
    }

    override fun getImageAuthCode() {
        OkGo.post<HttpResult<GetCodeUrl>>(Api.GET_AUTH_IMAGE_URL)
                .tag(this)
                .execute(object : JsonCallback<HttpResult<GetCodeUrl>>() {
                    override fun onSuccess(response: Response<HttpResult<GetCodeUrl>>) {
                        val body = response.body()
                        if (body.code === 0) {
                            val data = body.data
                            if (!AppUtils.isEmpty(data)) {
                                downLoadImg(data, Api.BASEURL)
                            }
                        }
                    }
                })
    }

    private fun downLoadImg(data: GetCodeUrl, baseUrl: String) {
        sign = data.sign
        OkGo.get<File>(baseUrl + data.url)
                .execute(object : FileCallback("imgCode.png") {
                    override fun onSuccess(response: Response<File>) {
                        val file = response.body()
                        if (!AppUtils.isEmpty(file) && file.exists()) {
                            mView.getImageAuthCodeCallback(file)
                        }
                    }
                })
    }

}
