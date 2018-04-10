package com.yusong.plugin_login.ui.login


import android.support.v4.content.ContextCompat
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.StringSignature
import com.yusong.plugin_login.R

import com.yusong.yslib.App
import com.yusong.yslib.mvp.MVPBaseActivity
import com.yusong.yslib.utils.AnimUtils

import java.io.File

import kotlinx.android.synthetic.main.activity_login.*

@Route(path = "/user/LoginActivity")
class LoginActivity : MVPBaseActivity<LoginContract.View, LoginPresenter>(), LoginContract.View, View.OnClickListener {

    override fun layoutId(): Int {
        return R.layout.activity_login
    }

   
    override fun initData() {
        val tv_back = titleLayout.findViewById<TextView>(R.id.tv_back)
        val tv_title = titleLayout.findViewById<TextView>(R.id.tv_title)
        tv_back.visibility = View.VISIBLE
        tv_back.setOnClickListener(this)
        val d = ContextCompat.getDrawable(this, R.mipmap.iv_title_white)
        d!!.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight())
        tv_title.setCompoundDrawables(d, null, null, null)
        tv_title.text = ""
        val d1 = ContextCompat.getDrawable(this, R.mipmap.iv_title)
        d1!!.setBounds(0, 0, d1.getIntrinsicWidth(), d1.getIntrinsicHeight())
        tv_app_name.setCompoundDrawables(d1, null, null, null)
        tv_app_name.text = ""
//        mPresenter!!.onEventComing(null)
    }

    override fun onClick(v: View?) {
        AnimUtils.clickAnimator(v)
        when (v?.id) {
            R.id.tv_back ->
             finish()
            R.id.tv_authcode_login ->
                mPresenter!!.invalidate(ll_img_authcode,et_account, et_pwd, tv_authcode, tv_authcode_login, iv_pwd_authcode)
            R.id.btn_login ->
                mPresenter!!.login(et_account, et_pwd, tv_authcode)
            R.id.tv_authcode ->
                mPresenter!!.getAuthCode(et_account,et_img_authcode, tv_authcode)
            R.id.iv_img_authcode ->
                mPresenter!!.getImageAuthCode()

        }
    }

    override fun gotoUserMain() {
        App.exit(false)
        ARouter.getInstance().build("/user/MainActivity").navigation()
        finish()
    }

    override fun gotoPhoneAuth(tel: String) {
        ARouter.getInstance().build("/user/PhoneAuthActivity").navigation()
        finish()
    }

    override fun gotoRealNameAuth() {
        ARouter.getInstance().build("/user/RealNameAuthActivity").navigation()
        finish()
    }

    override fun invalidateUseAuth() {
        mPresenter!!.invalidate(ll_img_authcode, et_account, et_pwd, tv_authcode, tv_authcode_login, iv_pwd_authcode)
    }

    override fun getImageAuthCodeCallback(file: File) {
                    Glide.with(this)
                    .load(file)
                    .signature(StringSignature(System.currentTimeMillis().toString()))
                    .into(iv_img_authcode)

    }

    override fun initListener() {
        super.initListener()
        tv_authcode_login.setOnClickListener(this)
        btn_login.setOnClickListener(this)
        tv_authcode.setOnClickListener(this)
        iv_img_authcode.setOnClickListener(this)
        et_pwd.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                mPresenter!!.login(et_account, et_pwd, tv_authcode)
            }
            false
        })
    }

    private fun scroll() {
        sv.scrollTo(0, sv.height)
    }
}
