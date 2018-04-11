package com.yusong.plugin_regist.entity.s;

import java.io.Serializable;

/**
 * @authorfeisher on 2017/11/20.17:17
 * email:458079442@qq.com
 */

public class Dict implements Serializable{
    //1:客户单位  , 2:电池类型,3:电池品牌, 4:故障类型 5 包时段退单理由 6 换电订单退单理由
    public int categoryId;

    public Dict(int categoryId) {
        this.categoryId = categoryId;
    }
}
