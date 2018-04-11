package com.yusong.plugin_regist.entity.s;

import java.io.Serializable;

/**
 * @authorfeisher on 2017/11/28.15:06
 * email:458079442@qq.com
 */

public class PayForOrder implements Serializable {

    public double foregiftMoney;
    public int foregiftType = 1;
    public int batteryType = 1;
    public long packetPeriodPriceId;
    public double packetPeriodPrice;
    public long couponTicketId;

}
