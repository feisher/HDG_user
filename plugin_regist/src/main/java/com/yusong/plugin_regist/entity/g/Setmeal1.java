package com.yusong.plugin_regist.entity.g;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/29/029.
 */

public class Setmeal1 implements Serializable {

    /**
     * id : 1
     * priceName : 包月订单
     * dayCount : 1
     * price : 20000
     * memo : 备注信息
     */

    public int id;
    public String priceName;
    public int dayCount;
    public int price;
    public String memo;
    //附加字段处理本地选中逻辑
    public boolean isChecked;
}
