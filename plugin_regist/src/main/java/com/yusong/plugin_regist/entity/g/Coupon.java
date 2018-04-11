package com.yusong.plugin_regist.entity.g;

import java.io.Serializable;

/**
 * @authorfeisher on 2017/11/21.14:44
 * email:458079442@qq.com
 */

public class Coupon implements Serializable{
    public int id;
    public String ticketName;
    public double money;
    public String expireTime;
    public boolean isChecked;

}
