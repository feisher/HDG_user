package com.yusong.plugin_regist.ui.realnameauth

import com.yusong.yslib.mvp.MVPBaseActivity
import com.yusong.yslib.utils.AnimUtils
import android.view.MotionEvent
import com.yusong.plugin_regist.entity.g.UserInfo1
import com.yusong.plugin_regist.entity.g.Dict1
import com.yusong.yslib.utils.SPUtils
import com.hss01248.dialog.interfaces.MyItemDialogListener
import com.hss01248.dialog.StyledDialog
import com.sdsmdg.tastytoast.TastyToast
import com.yusong.yslib.utils.AppUtils
import android.widget.LinearLayout
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.just.library.AgentWeb
import com.yusong.plugin_regist.R
import com.yusong.yslib.Const
import com.yusong.yslib.toast
import kotlinx.android.synthetic.main.activity_real_name_auth.*

@Route(path = "/regist/RealNameAuthActivity")
class RealNameAuthActivity : MVPBaseActivity<RealNameAuthContract.View, RealNameAuthPresenter>(), RealNameAuthContract.View, View.OnClickListener {

    var companyData: MutableList<String> = ArrayList()
    var batteryData: MutableList<String> = ArrayList()
    var companyDataList: MutableList<Dict1> = ArrayList()
    var batteryDataList: MutableList<Dict1> = ArrayList()
    var batteryType: String? =""
    var company: String? =""
    private var webView: LinearLayout? = null
    private var mAgentWeb: AgentWeb? = null

    override fun layoutId(): Int {
        return R.layout.activity_real_name_auth
    }

    override fun initData() {
        ARouter.getInstance().inject(this)
        val tv_back = titleLayout.findViewById<TextView>(R.id.tv_back)
        val tv_title = titleLayout.findViewById<TextView>(R.id.tv_title)
        tv_title.text = "实名认证"
        tv_back.visibility = View.VISIBLE
        tv_back.setOnClickListener(this)
        mPresenter!!.getCompanyData()
        mPresenter!!.getBatteryTypeData()
        mPresenter!!.querySettings()
        mPresenter!!.queryUserInfo()
    }

    override fun initListener() {
        super.initListener()
        ll_company.setOnClickListener(this)
        ll_batterey_type.setOnClickListener(this)
        btn_next.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        AnimUtils.clickAnimator(v)
        when (v!!.id) {
            R.id.tv_back -> finish()
            R.id.ll_company -> showCompanyDialog()
            R.id.btn_next -> showRegisterProtocol()
            R.id.ll_batterey_type -> showBatteryDialog()
        }
    }

    /**
     * 用户协议
     */
    private fun showRegisterProtocol() {
        val view = View.inflate(this, R.layout.dialog_register_protocal, null)
        val show = StyledDialog.buildCustom(view, Gravity.CENTER).setActivity(this).setForceWidthPercent(1.0f).show()
        webView = view.findViewById(R.id.webviwe)
        mAgentWeb = AgentWeb.with(this@RealNameAuthActivity)
                .setAgentWebParent(webView, LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .defaultProgressBarColor()
                .createAgentWeb()
                .ready()
                .go(Const.instance.registerProtocol)
        view.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            mAgentWeb!!.getWebLifeCycle().onDestroy()
            show.dismiss()
        }
        view.findViewById<Button>(R.id.btn_affirm).setOnClickListener{
            mPresenter!!.realNameAuth(et_real_name, et_id_number, et_pwd, et_pwd2, batteryType, company)
            mAgentWeb!!.getWebLifeCycle().onDestroy()
            show.dismiss()
        }
    }

    override fun onDestroy() {
        if (!AppUtils.isEmpty(mAgentWeb)) {
            mAgentWeb!!.getWebLifeCycle().onDestroy()
        }
        super.onDestroy()
    }

    private fun showCompanyDialog() {
        if (AppUtils.isEmpty(companyData)) {
            toast("无可选公司", TastyToast.WARNING)
            return
        }
        StyledDialog.buildIosSingleChoose(companyData, object : MyItemDialogListener() {
            override fun onItemClick(charSequence: CharSequence, i: Int) {
                tv_company.setText(charSequence)
                company = companyDataList[i].itemValue
            }
        }).show()
    }

    private fun showBatteryDialog() {
        if (AppUtils.isEmpty(batteryData)) {
            toast("无可选电池型号", TastyToast.WARNING)
            return
        }
        StyledDialog.buildIosSingleChoose(batteryData, object : MyItemDialogListener() {
            override fun onItemClick(charSequence: CharSequence, i: Int) {
                tv_batterey_type.setText(charSequence)
                batteryType = batteryDataList[i].itemValue
                SPUtils.put<String?>(
                        this@RealNameAuthActivity,
                        SPUtils.K_BATTERYTYPE,
                        batteryType!!
                )
            }
        }).show()
    }

    override fun getCompanyDataCallback(data: List<Dict1>) {
        companyData.clear()
        for (dataBean in data) {
            companyData.add(dataBean.itemName)
        }
        companyDataList.clear()
        companyDataList.addAll(data)
    }

    override fun getBatteryTypeDataCallback(data: List<Dict1>) {
        batteryData.clear()
        for (dataBean in data) {
            batteryData.add(dataBean.itemName)
        }
        batteryDataList.clear()
        batteryDataList.addAll(data)
    }

    override fun queryUserInfoCallback(body: UserInfo1) {
        batteryType = body.batteryType.toString()
    }

    override fun realNameAuthCallback(battereyType: String) {
        ARouter.getInstance().build("/user/MainActivity").navigation()
        finish()
    }

}
