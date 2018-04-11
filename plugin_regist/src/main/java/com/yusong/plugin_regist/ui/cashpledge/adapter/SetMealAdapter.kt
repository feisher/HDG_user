package com.yusong.plugin_regist.ui.cashpledge.adapter

import android.support.annotation.LayoutRes

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yusong.plugin_regist.R

import com.yusong.plugin_regist.entity.g.Setmeal1
import com.yusong.yslib.utils.AppUtils
import com.yusong.yslib.view.SmoothCheckBox

import java.text.DecimalFormat

/**
 * @authorfeisher on 2017/11/21.14:58
 * email:458079442@qq.com
 */

class SetMealAdapter(@LayoutRes layoutResId: Int, data: List<Setmeal1>?) : BaseQuickAdapter<Setmeal1, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: Setmeal1) {
        val format = DecimalFormat("0.00")
        helper.setText(R.id.tv_setmeal_name, (if (AppUtils.isEmpty(item.priceName)) "未命名套餐" else item.priceName) + item.dayCount + "天")
        helper.setText(R.id.tv_setmeal_explain, if (AppUtils.isEmpty(item.memo)) "无说明" else item.memo)
        helper.setText(R.id.tv_money, "￥" + format.format(item.price * 1.0 / 100))
        val smoothCheckBox = helper.getView<SmoothCheckBox>(R.id.scb)
        smoothCheckBox.setClickable(false)
        smoothCheckBox.setChecked(item.isChecked, false)
    }
}
