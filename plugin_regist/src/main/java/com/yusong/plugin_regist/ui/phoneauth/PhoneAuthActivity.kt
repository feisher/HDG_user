package com.yusong.plugin_regist.ui.phoneauth


import com.yusong.yslib.mvp.MVPBaseActivity
import com.yusong.yslib.utils.AnimUtils
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.signature.StringSignature
import com.bumptech.glide.Glide
import com.yusong.plugin_regist.R
import kotlinx.android.synthetic.main.activity_phone_auth.*
import java.io.File

@Route(path = "/user/PhoneAuthActivity")
class PhoneAuthActivity : MVPBaseActivity<PhoneAuthContract.View, PhoneAuthPresenter>(), PhoneAuthContract.View, View.OnClickListener {
    @Autowired
    @JvmField var tel: String? = ""

    override fun layoutId(): Int {
        return R.layout.activity_phone_auth
    }

    override fun initData() {
        ARouter.getInstance().inject(this)
        val tv_back = titleLayout.findViewById<TextView>(R.id.tv_back)
        val tv_title = titleLayout.findViewById<TextView>(R.id.tv_title)
        tv_title.text = "手机认证"
        tv_back.visibility = View.VISIBLE
        tv_back.setOnClickListener(this)
        et_phone_number.setText(tel)
        mPresenter!!.getImageAuthCode()
    }

    override fun initListener() {
        super.initListener()
        tv_get_auth_code.setOnClickListener(this)
        btn_next.setOnClickListener(this)
        iv_img_authcode.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        AnimUtils.clickAnimator(v)
        when (v!!.id) {
            R.id.tv_back -> finish()
            R.id.tv_get_auth_code -> mPresenter!!.getAuthCode(et_phone_number, et_img_authcode, tv_get_auth_code)
            R.id.btn_next -> mPresenter!!.regist(et_phone_number, et_auth_code)
            R.id.iv_img_authcode -> mPresenter!!.getImageAuthCode()
        }
    }

    override fun registCallback() {
        ARouter.getInstance().build("/user/RealNameAuthActivity").withString("tel","").navigation()
        finish()
    }

    override fun getImageAuthCodeCallback(file: File) {
        Glide.with(this)
                .load<File>(file)
                .signature(StringSignature(System.currentTimeMillis().toString()))
                .into(iv_img_authcode)
    }

    fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN //手指按下
            -> AnimUtils.touchDownAnimator(v)
            MotionEvent.ACTION_UP //手指抬起
                , MotionEvent.ACTION_MOVE //滑动
                , MotionEvent.ACTION_CANCEL //取消
            -> AnimUtils.touchUpAnimator(v)
        }
        return false
    }
}
