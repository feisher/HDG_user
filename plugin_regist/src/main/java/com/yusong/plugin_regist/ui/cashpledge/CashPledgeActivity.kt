package com.yusong.plugin_regist.ui.cashpledge


import android.app.Dialog
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.cuieney.rxpay_annotation.WX
import com.cuieney.sdk.rxpay.RxPay
import com.hss01248.dialog.StyledDialog
import com.hss01248.dialog.config.ConfigBean
import com.sdsmdg.tastytoast.TastyToast
import com.yusong.plugin_regist.R
import com.yusong.plugin_regist.entity.g.*
import com.yusong.plugin_regist.entity.i.Deposit
import com.yusong.plugin_regist.entity.s.PayForOrder
import com.yusong.plugin_regist.ui.cashpledge.adapter.CashPledgeAdapter
import com.yusong.plugin_regist.ui.cashpledge.adapter.CouponAdapter
import com.yusong.plugin_regist.ui.cashpledge.adapter.SetMealAdapter
import com.yusong.yslib.http.GsonUtil
import com.yusong.yslib.mvp.MVPBaseActivity
import com.yusong.yslib.toast
import com.yusong.yslib.utils.AnimUtils
import com.yusong.yslib.view.SmoothCheckBox
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_pay_cash_pledge.*
import org.json.JSONObject


import java.text.DecimalFormat

/**
 * @author feisher.qq:458079442
 * @explain 支付押金界面（押金充值）
 * @time 2017/11/6 15:26.
 */
@WX(packageName = "com.yusong.plugin_regist")
@Route(path = "/user/CashPledgeActivity")
class CashPledgeActivity : MVPBaseActivity<CashPledgeContract.View, CashPledgePresenter>(), CashPledgeContract.View, BaseQuickAdapter.OnItemClickListener, View.OnClickListener {

    private var cashPledgeAdapter: CashPledgeAdapter? = null
    private var setMealAdapter: SetMealAdapter? = null
    private var couponAdapter: CouponAdapter? = null
    @Autowired
    @JvmField var deposit: Deposit? = null
    private var mCashPledge: Double = 0.toDouble()
    private var mSetMeal: Double = 0.toDouble()
    private var mCoupon: Double = 0.toDouble()
    private val payForOrder = PayForOrder()
    private var countValue: Double = 0.toDouble()
    private var isMustBuy: Int = 0

    override fun layoutId(): Int {
        return R.layout.activity_pay_cash_pledge
    }

    override fun initData() {
        ARouter.getInstance().inject(this)
        val tv_back = titleLayout.findViewById<TextView>(R.id.tv_back)
        val tv_title = titleLayout.findViewById<TextView>(R.id.tv_title)
        tv_title.text = "手机认证"
        tv_back.visibility = View.VISIBLE
        tv_back.setOnClickListener(this)
        mPresenter!!.queryCashPledge()
        mPresenter!!.querySetMeal(deposit!!)
        mPresenter!!.couponTicket(1)
        initAdapter()
    }

    override fun onClick(v: View?) {
        AnimUtils.clickAnimator(v)
        when (v!!.id) {
            R.id.tv_back -> finish()
            R.id.tv_commit -> if (countValue == 0.0) {
                mPresenter!!.payByBalance(payForOrder, isMustBuy)
            } else {
                showPayType()
            }
        }
    }


    override fun initListener() {
        super.initListener()
        tv_commit.setOnClickListener(this)
    }

    private fun showPayType() {

        val inflate = View.inflate(this, R.layout.dialog_pay, null)
        val configBean = StyledDialog.buildCustom(inflate, Gravity.BOTTOM).setActivity(this).setForceWidthPercent(1.0f)
        val show = configBean.show()
        show.setCanceledOnTouchOutside(true)
        val bpay = show.findViewById(R.id.scb_balance_pay) as SmoothCheckBox
        val wpay = show.findViewById(R.id.scb_wx_pay) as SmoothCheckBox
        val apay = show.findViewById(R.id.scb_alipay) as SmoothCheckBox
        val btnPay = show.findViewById(R.id.btn_pay) as Button

        show.findViewById<LinearLayout>(R.id.ll_balance_pay).setOnClickListener(View.OnClickListener { bpay.isChecked = true })
        show.findViewById<LinearLayout>(R.id.ll_wx_pay).setOnClickListener(View.OnClickListener { wpay.isChecked = true })
        show.findViewById<LinearLayout>(R.id.ll_alipay).setOnClickListener(View.OnClickListener { apay.isChecked = true })
        apay.setOnCheckedChangeListener { checkBox, isChecked ->
            if (isChecked) {
                bpay.isChecked = !isChecked
                wpay.isChecked = !isChecked
            }
        }
        bpay.setOnCheckedChangeListener { checkBox, isChecked ->
            if (isChecked) {
                apay.isChecked = !isChecked
                wpay.isChecked = !isChecked
            }
        }
        wpay.setOnCheckedChangeListener { checkBox, isChecked ->
            if (isChecked) {
                bpay.isChecked = !isChecked
                apay.isChecked = !isChecked
            }
        }
        btnPay.setOnClickListener {
            if (bpay.isChecked) {
                mPresenter!!.payByBalance(payForOrder, isMustBuy)
            }
            if (wpay.isChecked) {
                mPresenter!!.payByWeChat(payForOrder, isMustBuy)
            }
            if (apay.isChecked) {
                mPresenter!!.payByAliPay(payForOrder, isMustBuy)
            }
            show.dismiss()
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        //        AnimUtils.clickAnimator(view);
        //        SmoothCheckBox smoothCheckBox = (SmoothCheckBox) view.findViewById(R.id.scb);
        //        smoothCheckBox.setChecked(!smoothCheckBox.isChecked(), true);
        if (adapter is CashPledgeAdapter) {
            val data = adapter.data
            for (dataBean in data) {
                dataBean.isChecked = false
            }
            data[position].isChecked = true
            deposit!!.batteryType = if (data[position].isChecked) data[position].type.toString() else "0"
            mCashPledge = if (data[position].isChecked) data[position].money else 0.0
            payForOrder.foregiftMoney = if (data[position].isChecked) data[position].money else 0.0
            payForOrder.batteryType = data[position].type
            mPresenter!!.querySetMeal(deposit!!)
            mSetMeal = 0.0
        }
        if (adapter is SetMealAdapter) {
            val data = adapter.data
            for (dataBean in data) {
                dataBean.isChecked = false
            }
            data[position].isChecked = true
            mSetMeal = (if (data[position].isChecked) data[position].price else 0).toDouble()
            payForOrder.packetPeriodPrice = (if (data[position].isChecked) data[position].price else 0).toDouble()
            payForOrder.packetPeriodPriceId = (if (data[position].isChecked) data[position].id else 0).toLong()
        }
        if (adapter is CouponAdapter) {
            val data = adapter.data
            for (dataBean in data) {
                dataBean.isChecked = false
            }
            data[position].isChecked = true
            mCoupon = if (data[position].isChecked) data[position].money else 0.0
            payForOrder.couponTicketId = (if (data[position].isChecked) data[position].id else 0).toLong()
        }
        countValue = mCashPledge + mSetMeal - mCoupon
        if (countValue <= 0) {
            countValue = 0.0
        }
        tv_count.setText("￥" + DecimalFormat("0.00").format(countValue / 100))
        adapter.notifyDataSetChanged()
    }

    private fun initAdapter() {
        val layoutManager = LinearLayoutManager(this)
        cashPledgeAdapter = CashPledgeAdapter(R.layout.item_cash_pledge, ArrayList())
        rv_cash_pledge_list.setLayoutManager(layoutManager)
        rv_cash_pledge_list.setAdapter(cashPledgeAdapter)
        cashPledgeAdapter!!.bindToRecyclerView(rv_cash_pledge_list)
        cashPledgeAdapter!!.setEmptyView(R.layout.layout_no_data)

        val layoutManager2 = LinearLayoutManager(this)
        setMealAdapter = SetMealAdapter(R.layout.item_setmeal, ArrayList())
        rv_set_meal_list.setLayoutManager(layoutManager2)
        rv_set_meal_list.setAdapter(setMealAdapter)
        setMealAdapter!!.bindToRecyclerView(rv_set_meal_list)
        setMealAdapter!!.setEmptyView(R.layout.layout_no_data)

        val layoutManager3 = LinearLayoutManager(this)
        rv_coupon_list.setLayoutManager(layoutManager3)
        couponAdapter = CouponAdapter(R.layout.item_useable_coupon, ArrayList())
        rv_coupon_list.setAdapter(couponAdapter)
        couponAdapter!!.bindToRecyclerView(rv_coupon_list)
        couponAdapter!!.setEmptyView(R.layout.layout_no_data)

        cashPledgeAdapter!!.setOnItemClickListener(this)
        setMealAdapter!!.setOnItemClickListener(this)
        couponAdapter!!.setOnItemClickListener(this)

    }

    override fun queryCashPledgeCallback(data: List<CashPledge1>) {
        var position = 0
        var pos = 0
        for (d in data) {
            if (deposit!!.batteryType == d.type.toString()) {
                position = pos
                d.isChecked = true
                payForOrder.batteryType = d.type
                mCashPledge = d.money
                tv_count.setText("￥" + DecimalFormat("0.00").format(d.money / 100.0))
                countValue = d.money
            }
            pos++
        }

        payForOrder.foregiftMoney = if (data[position].isChecked) data[position].money else 0.0
        cashPledgeAdapter!!.setNewData(data)
    }

    override fun setMealCallback(data: TimeSetMeal1) {
        isMustBuy = data.isMustBuy
        setMealAdapter!!.setNewData(data.list)
    }

    override fun couponTicketCallback(data: List<Coupon>) {
        couponAdapter!!.setNewData(data)
    }

    override fun payByBalanceCallback() {
        toast("支付成功", TastyToast.SUCCESS)
        ARouter.getInstance().build("/user/PayFinishActivity")
                .withString("","亲，您的押金和换电套餐支付成功")
                .navigation()
        finish()
    }

    override fun payByWeChatCallback(data: Pay2) {
        RxPay(this).requestWXpay(JSONObject(GsonUtil.toJson(data)))
                .subscribe( Consumer<Boolean>{
                    if (it) {
                        toast("付款成功",TastyToast.SUCCESS)
                        finish()
                    }
                },  Consumer<Throwable>() {
                    toast("付款失败："+it.message)
                })

    }

    override fun payByAliPayCallback(data: Pay1) {
        RxPay(this).requestAlipay(GsonUtil.toJson(data))
                .subscribe( Consumer<Boolean>{
                    if (it) {
                        toast("付款成功",TastyToast.SUCCESS)
                        finish()
                    }
                },  Consumer<Throwable>() {
                    toast("付款失败："+it.message)
                })
    }




}
