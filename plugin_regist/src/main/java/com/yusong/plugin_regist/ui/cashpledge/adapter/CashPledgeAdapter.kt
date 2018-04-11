package com.yusong.plugin_regist.ui.cashpledge.adapter

import android.support.annotation.LayoutRes

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yusong.plugin_regist.R

import com.yusong.plugin_regist.entity.g.CashPledge1
import com.yusong.yslib.view.SmoothCheckBox

import java.text.DecimalFormat

/**
 * @authorfeisher on 2017/11/21.14:58
 * email:458079442@qq.com
 */

class CashPledgeAdapter(@LayoutRes layoutResId: Int, data: List<CashPledge1>?) : BaseQuickAdapter<CashPledge1, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: CashPledge1) {
        helper.setText(R.id.tv_name, if (item.name != null) item.name else "**")
        val format = DecimalFormat("0.00")
        helper.setText(R.id.tv_money, "￥" + format.format(item.money / 100))
        val smoothCheckBox = helper!!.getView<SmoothCheckBox>(R.id.scb)
        smoothCheckBox.setClickable(false)
        smoothCheckBox.setChecked(item.isChecked, false)
    }
}
