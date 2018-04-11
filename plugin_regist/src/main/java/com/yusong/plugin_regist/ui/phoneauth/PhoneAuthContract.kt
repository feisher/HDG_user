package com.yusong.plugin_regist.ui.phoneauth

import com.yusong.yslib.mvp.BasePresenter
import com.yusong.yslib.mvp.BaseView
import android.widget.EditText
import android.widget.TextView
import java.io.File


class PhoneAuthContract {
    interface View : BaseView {
        /**
         * 注册成功回调此方法，
         */
        fun registCallback()

        fun getImageAuthCodeCallback(file: File)
    }

    internal interface Presenter : BasePresenter<View> {
        fun getAuthCode(etPhoneNumber: EditText, etImgAuthcode: EditText, tvAuthCode: TextView)

        fun regist(etPhoneNumber: EditText, etAuthCode: EditText)

        fun getImageAuthCode()
    }
}
