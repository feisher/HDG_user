package com.yusong.plugin_regist.ui.cashpledge


import com.yusong.plugin_regist.entity.g.CashPledge1
import com.yusong.plugin_regist.entity.g.Coupon
import com.yusong.plugin_regist.entity.g.Pay1
import com.yusong.plugin_regist.entity.g.Pay2
import com.yusong.plugin_regist.entity.g.TimeSetMeal1
import com.yusong.plugin_regist.entity.i.Deposit
import com.yusong.plugin_regist.entity.s.PayForOrder
import com.yusong.yslib.mvp.BasePresenter
import com.yusong.yslib.mvp.BaseView

class CashPledgeContract {
    interface View : BaseView {

        fun queryCashPledgeCallback(data: List<CashPledge1>)

        fun setMealCallback(data: TimeSetMeal1)

        fun couponTicketCallback(data: List<Coupon>)

        fun payByBalanceCallback()

        fun payByWeChatCallback(data: Pay2)

        fun payByAliPayCallback(data: Pay1)
    }

    internal interface Presenter : BasePresenter<View> {
        /**
         * 查询换电押金
         */
        fun queryCashPledge()

        /**
         * 8-查询充值套餐
         */
        fun querySetMeal(deposit: Deposit)

        fun couponTicket(type: Int)

        fun payByBalance(payForOrder: PayForOrder, isMustBuy: Int)

        fun payByWeChat(payForOrder: PayForOrder, isMustBuy: Int)

        fun payByAliPay(payForOrder: PayForOrder, isMustBuy: Int)
    }
}
