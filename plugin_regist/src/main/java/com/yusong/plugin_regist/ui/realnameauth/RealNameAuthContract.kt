package com.yusong.plugin_regist.ui.realnameauth


import android.widget.EditText

import com.yusong.plugin_regist.entity.g.Dict1
import com.yusong.plugin_regist.entity.g.UserInfo1
import com.yusong.yslib.mvp.BasePresenter
import com.yusong.yslib.mvp.BaseView

class RealNameAuthContract {
    interface View : BaseView {
        fun getCompanyDataCallback(data: List<Dict1>)
        fun realNameAuthCallback(battereyType: String)

        fun getBatteryTypeDataCallback(data: List<Dict1>)

        fun queryUserInfoCallback(body: UserInfo1)
    }

    internal interface Presenter : BasePresenter<View> {
        /**
         * 获取所属公司列表
         */
        fun getCompanyData()

        fun realNameAuth(etRealName: EditText, etIdNumber: EditText, etPwd: EditText, etPwd2: EditText, battereyType: String?, company: String?)

        fun getBatteryTypeData()

        fun querySettings()

        fun queryUserInfo()
    }
}
