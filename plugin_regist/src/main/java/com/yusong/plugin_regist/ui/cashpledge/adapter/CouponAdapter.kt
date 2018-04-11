package com.yusong.plugin_regist.ui.cashpledge.adapter

import android.support.annotation.LayoutRes

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yusong.plugin_regist.R
import com.yusong.plugin_regist.entity.g.Coupon
import com.yusong.yslib.view.SmoothCheckBox

import java.text.DecimalFormat

/**
 * @authorfeisher on 2017/11/21.14:58
 * email:458079442@qq.com
 */

class CouponAdapter(@LayoutRes layoutResId: Int, data: List<Coupon>?) : BaseQuickAdapter<Coupon, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: Coupon) {
        val format = DecimalFormat("0.00")
        helper.setText(R.id.tv_setmeal_name, format.format(item.money / 100) + "元优惠券可用")
        val smoothCheckBox = helper.getView<SmoothCheckBox>(R.id.scb)
        smoothCheckBox.setClickable(false)
        smoothCheckBox.setChecked(item.isChecked, false)
    }
}
