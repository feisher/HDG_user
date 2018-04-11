package com.yusong.plugin_regist.ui.realnameauth


import android.widget.EditText

import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import com.sdsmdg.tastytoast.TastyToast
import com.yusong.plugin_regist.entity.g.Dict1
import com.yusong.plugin_regist.entity.s.Dict
import com.yusong.yslib.Api
import com.yusong.yslib.http.GsonUtil
import com.yusong.yslib.mvp.BasePresenterImpl
import com.yusong.yslib.utils.AppUtils
import org.json.JSONException
import org.json.JSONObject
import com.yusong.yslib.utils.SPUtils
import com.yusong.yslib.utils.MD5Utils
import com.yusong.yslib.utils.RegexUtils
import com.yusong.plugin_regist.entity.g.Setting1
import com.yusong.plugin_regist.entity.g.UserInfo1
import com.yusong.plugin_regist.entity.s.RealNameAuth
import com.yusong.yslib.App.Companion.toast
import com.yusong.yslib.Const
import com.yusong.yslib.http.HttpResult
import com.yusong.yslib.http.JsonCallback



class RealNameAuthPresenter : BasePresenterImpl<RealNameAuthContract.View>(), RealNameAuthContract.Presenter{
    override fun getCompanyData() {
        OkGo.post<HttpResult<List<Dict1>>>(Api.DICT_ITEM)
                .upJson(GsonUtil.toJson(Dict(1)))
                .execute(object : JsonCallback<HttpResult<List<Dict1>>>() {
                    override fun onSuccess(response: Response<HttpResult<List<Dict1>>>) {
                        if (AppUtils.isEmpty(mView)) {
                            return
                        }
                        val body = response.body()
                        if (!AppUtils.isEmpty(body)) {
                            if (!AppUtils.isEmpty(body.data)) {
                                mView.getCompanyDataCallback(body.data!!)
                            }
                        }
                    }
                })
    }

    override fun getBatteryTypeData() {
        OkGo.post<HttpResult<List<Dict1>>>(Api.DICT_ITEM)
                .upJson(GsonUtil.toJson(Dict(2)))
                .execute(object : JsonCallback<HttpResult<List<Dict1>>>() {
                    override fun onSuccess(response: Response<HttpResult<List<Dict1>>>) {
                        if (AppUtils.isEmpty(mView)) {
                            return
                        }
                        val body = response.body()
                        if (!AppUtils.isEmpty(body)) {
                            if (!AppUtils.isEmpty(body.data)) {
                                mView.getBatteryTypeDataCallback(body.data!!)
                            }
                        }
                    }
                })
    }

    override fun querySettings() {
        OkGo.post<HttpResult<Setting1>>(Api.SETTING)
                .execute(object : JsonCallback<HttpResult<Setting1>>() {
                    override fun onSuccess(response: Response<HttpResult<Setting1>>) {
                        if (AppUtils.isEmpty(mView)) {
                            return
                        }
                        val body = response.body()
                        if (body.code == 0) {
                            if (!AppUtils.isEmpty(body.data)) {
                                Api.STACIC_SERVER = body.data!!.staticUrl
                                Const.instance.registerProtocol = body.data!!.registerProtocol
                            }
                        }
                    }
                })
    }

    override fun queryUserInfo() {
        OkGo.post<HttpResult<UserInfo1>>(Api.USER_INFO)
                .execute(object : JsonCallback<HttpResult<UserInfo1>>() {
                    override fun onSuccess(response: Response<HttpResult<UserInfo1>>) {

                        if (!AppUtils.isEmpty(response.body())) {
                            if (!AppUtils.isEmpty(mView)) {
                                mView.queryUserInfoCallback(response.body().data!!)
                            }
                        }
                    }
                })
    }

    override fun realNameAuth(etRealName: EditText, etIdNumber: EditText, etPwd: EditText, etPwd2: EditText, battereyType: String?, company: String?) {
        val mRealName = etRealName.text.toString()
        val mIdNumber = etIdNumber.text.toString()
        val mPwd = etPwd.text.toString()
        val mPwd2 = etPwd2.text.toString()
        if (AppUtils.isEmpty(mRealName)) {
            toast("姓名不能为空", TastyToast.ERROR)
            return
        }
        if (!RegexUtils.checkIdCard(mIdNumber)) {
            toast("身份号码不合法，请重新输入", TastyToast.ERROR)
            return
        }
        if (mPwd != mPwd2) {
            toast("密码不一致", TastyToast.ERROR)
            return
        }
        val realNameAuth = RealNameAuth(mRealName, mIdNumber, MD5Utils.md5Password(mPwd), company, battereyType)
        OkGo.post<String>(Api.REALNAME_AUTH)
                .headers("Authorization", "Bearer " + SPUtils.get(mView.context, "token", ""))
                .upJson(GsonUtil.toJson(realNameAuth))
                .execute(object : StringCallback() {
                    override fun onSuccess(response: Response<String>) {
                        if (AppUtils.isEmpty(mView)) {
                            return
                        }
                        val body = response.body()
                        if (!AppUtils.isEmpty(body)) {
                            try {
                                val jsonObject = JSONObject(body)
                                val code = jsonObject.opt("code") as Int
                                if (code == 0) {
                                    toast(jsonObject.optString("message"), TastyToast.SUCCESS)
                                    mView.realNameAuthCallback(battereyType!!)
                                }
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }

                        }
                    }
                })
    }
}

