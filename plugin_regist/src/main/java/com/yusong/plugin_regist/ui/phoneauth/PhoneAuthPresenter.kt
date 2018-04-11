package com.yusong.plugin_regist.ui.phoneauth


import android.graphics.Color
import com.yusong.yslib.mvp.BasePresenterImpl
import com.yusong.yslib.http.GsonUtil
import com.yusong.yslib.utils.SPUtils
import com.yusong.yslib.utils.AppUtils
import com.yusong.yslib.http.HttpResult
import com.yusong.yslib.http.JsonCallback
import com.yusong.yslib.Api
import com.lzy.okgo.OkGo
import android.support.v4.content.ContextCompat
import android.os.CountDownTimer
import android.widget.TextView
import com.lzy.okgo.callback.FileCallback
import com.yusong.yslib.entity.Login1
import com.sdsmdg.tastytoast.TastyToast
import com.yusong.yslib.utils.RegexUtils
import android.widget.EditText
import org.json.JSONException
import org.json.JSONObject
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import com.yusong.plugin_regist.R
import com.yusong.plugin_regist.entity.g.GetCodeUrl
import com.yusong.plugin_regist.entity.s.LoginByAuthCode
import com.yusong.plugin_regist.entity.s.PushToken
import com.yusong.plugin_regist.entity.s.SignGet
import com.yusong.yslib.App
import com.yusong.yslib.App.Companion.toast
import java.io.File


class PhoneAuthPresenter : BasePresenterImpl<PhoneAuthContract.View>(), PhoneAuthContract.Presenter{
    private var timer: CountDownTimer? = null
    private var sign: String? = null

    override fun getAuthCode(etPhoneNumber: EditText, etImgAuthcode: EditText, tvAuthCode: TextView) {
        val mAccount = etPhoneNumber.text.toString()
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
                                    toast("" + jsonObject.opt("message") as String)
                                }
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }

                        }
                    }
                })
    }

    override fun regist(etPhoneNumber: EditText, etAuthCode: EditText) {
        val mPhoneNumber = etPhoneNumber.text.toString()
        val mAuthCode = etAuthCode.text.toString()
        if (!RegexUtils.checkMobile(mPhoneNumber)) {
            toast("手机号不合法，请重新输入", TastyToast.WARNING)
            return
        }
        OkGo.post<HttpResult<Login1>>(Api.LOGIN_BY_CODE)
                .upJson(GsonUtil.toJson(LoginByAuthCode(mPhoneNumber, mAuthCode)))
                .execute(object : JsonCallback<HttpResult<Login1>>() {
                   override fun onSuccess(response: Response<HttpResult<Login1>>) {
                        if (AppUtils.isEmpty(mView)) {
                            return
                        }
                        val body = response.body()
                        if (!AppUtils.isEmpty(body)) {
                            if (!AppUtils.isEmpty(body.data)) {
                                SPUtils.put<String>(mView.context, SPUtils.K_TOKEN, body.data!!.token)
                                commitPushToken(App.pushType, App.pushToken)

                            }
                        }
                    }
                })
    }

    override fun getImageAuthCode() {
        OkGo.post<HttpResult<GetCodeUrl>>(Api.GET_AUTH_IMAGE_URL)
                .tag(this)
                .execute(object : JsonCallback<HttpResult<GetCodeUrl>>() {
                   override fun onSuccess(response: Response<HttpResult<GetCodeUrl>>) {
                        val body = response.body()
                        if (body.code == 0) {
                            val data = body.data
                            if (!AppUtils.isEmpty(data)) {
                                downLoadImg(data!!, Api.BASEURL)
                            }
                        }
                    }
                })
    }

    private fun downLoadImg(data: GetCodeUrl, baseUrl: String) {
        sign = data.sign
        OkGo.get<File>(baseUrl + data.url)
                .execute(object : FileCallback("imgCode.png") {
                  override  fun onSuccess(response: Response<File>) {
                        val file = response.body()
                        if (!AppUtils.isEmpty(file) && file.exists()) {
                            mView.getImageAuthCodeCallback(file)
                        }
                    }
                })
    }

    private fun initCountDownTimer(tvAuthCode: TextView?): CountDownTimer {
        return object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tvAuthCode!!.isEnabled = false
                tvAuthCode!!.setTextColor(Color.GRAY)
                tvAuthCode!!.setBackgroundResource(R.drawable.shape_round_rect_gray)
                tvAuthCode!!.text = "重新发送(" + millisUntilFinished / 1000 + ")"
            }

            override fun onFinish() {
                tvAuthCode!!.isEnabled = true
                tvAuthCode!!.setTextColor(ContextCompat.getColor(mView.context, R.color.white))
                tvAuthCode!!.setBackgroundResource(R.drawable.shape_round_rect_yellow)
                tvAuthCode!!.text = "获取验证码"
            }
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
                        mView!!.registCallback()
                    }
                })
    }

}
