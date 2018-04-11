package com.yusong.plugin_regist.ui.cashpledge


import com.lzy.okgo.OkGo
import com.lzy.okgo.model.Response
import com.sdsmdg.tastytoast.TastyToast

import com.yusong.plugin_regist.entity.g.CashPledge1
import com.yusong.plugin_regist.entity.g.Coupon
import com.yusong.plugin_regist.entity.g.Pay1
import com.yusong.plugin_regist.entity.g.Pay2
import com.yusong.plugin_regist.entity.g.TimeSetMeal1
import com.yusong.plugin_regist.entity.i.Deposit
import com.yusong.plugin_regist.entity.s.PayForOrder
import com.yusong.yslib.Api
import com.yusong.yslib.App.Companion.toast
import com.yusong.yslib.event.EventCenter
import com.yusong.yslib.http.GsonUtil
import com.yusong.yslib.http.HttpResult
import com.yusong.yslib.http.JsonCallback
import com.yusong.yslib.mvp.BasePresenterImpl
import com.yusong.yslib.utils.AppUtils


class CashPledgePresenter : BasePresenterImpl<CashPledgeContract.View>(), CashPledgeContract.Presenter {

    override fun queryCashPledge() {
        OkGo.post<HttpResult<List<CashPledge1>>>(Api.BATTERY_FOREGIFT)
                .execute(object : JsonCallback<HttpResult<List<CashPledge1>>>() {
                    override fun onSuccess(response: Response<HttpResult<List<CashPledge1>>>) {
                        mView!!.queryCashPledgeCallback(response.body().data!!)
                    }
                })
    }

    override fun querySetMeal(deposit: Deposit) {
        OkGo.post<HttpResult<TimeSetMeal1>>(Api.TIME_SETMEAL)
                .upJson(GsonUtil.toJson(deposit))
                .execute(object : JsonCallback<HttpResult<TimeSetMeal1>>() {
                    override fun onSuccess(response: Response<HttpResult<TimeSetMeal1>>) {
                        mView!!.setMealCallback(response.body().data!!)
                    }
                })
    }

    override fun couponTicket(type: Int) {
        OkGo.post<HttpResult<List<Coupon>>>(Api.COUPON_TICKET)
                //                .upJson(GsonUtil.toJson(new Type(type)))
                .execute(object : JsonCallback<HttpResult<List<Coupon>>>() {
                    override fun onSuccess(response: Response<HttpResult<List<Coupon>>>) {
                        mView!!.couponTicketCallback( response.body().data!!)
                    }
                })
    }

    override fun payByBalance(payForOrder: PayForOrder, isMustBuy: Int) {
        if (isMustBuy == 1 && payForOrder.packetPeriodPrice == 0.0) {
            toast("套餐为必选项",TastyToast.WARNING)
            return
        }
        OkGo.post<HttpResult<Void>>(Api.PAY_BY_BALANCE)
                .upJson(GsonUtil.toJson(payForOrder))
                .execute(object : JsonCallback<HttpResult<Void>>() {
                    override fun onSuccess(response: Response<HttpResult<Void>>) {
                        mView!!.payByBalanceCallback()
                    }
                })
    }

    override fun payByWeChat(payForOrder: PayForOrder, isMustBuy: Int) {
        if (isMustBuy == 1 && payForOrder.packetPeriodPrice == 0.0) {
            toast("套餐为必选项",TastyToast.WARNING)
            return
        }
        OkGo.post<HttpResult<Pay2>>(Api.PAY_BY_WECHAT)
                .upJson(GsonUtil.toJson(payForOrder))
                .execute(object : JsonCallback<HttpResult<Pay2>>() {
                    override fun onSuccess(response: Response<HttpResult<Pay2>>) {
                        val body = response.body()
                        if (body!!.code == 0) {
                            mView!!.payByWeChatCallback(body.data!!)
                        }
                    }
                })
    }

    override fun payByAliPay(payForOrder: PayForOrder, isMustBuy: Int) {
        if (isMustBuy == 1 && payForOrder.packetPeriodPrice == 0.0) {
            toast("套餐为必选项",TastyToast.WARNING)
            return
        }
        OkGo.post<HttpResult<Pay1>>(Api.PAY_BY_ALIPAY)
                .upJson(GsonUtil.toJson(payForOrder))
                .execute(object : JsonCallback<HttpResult<Pay1>>() {
                    override fun onSuccess(response: Response<HttpResult<Pay1>>) {
                        val body = response.body()
                        if (body!!.code == 0) {
                            mView!!.payByAliPayCallback(body.data!!)
                        }
                    }
                })
    }
}
