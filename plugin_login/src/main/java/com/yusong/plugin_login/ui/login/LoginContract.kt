package com.yusong.plugin_login.ui.login

import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.yusong.yslib.mvp.BasePresenter
import com.yusong.yslib.mvp.BaseView

import java.io.File

class LoginContract {
    interface View : BaseView {
        fun gotoUserMain()
        fun gotoPhoneAuth(tel: String)
        fun gotoRealNameAuth()
        fun invalidateUseAuth()

        fun getImageAuthCodeCallback(file: File)
    }

    internal interface Presenter : BasePresenter<View> {

        fun login(etAccount: EditText, etPwd: EditText, tvAuthcode: TextView)

        fun invalidate(llImgAuthcode: LinearLayout, etAccount: EditText, etPwd: EditText, tvAuthcode: TextView, tvAuthcodeLogin: TextView, ivPwdAuthcode: ImageView)

        fun getAuthCode(account: EditText, etImgAuthcode: EditText, tvAuthCode: TextView)

        fun getImageAuthCode()
    }
}
