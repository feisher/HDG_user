package com.yusong.plugin_regist.entity.s;

import java.io.Serializable;

/**
 * @authorfeisher on 2017/11/20.17:09
 * email:458079442@qq.com
 */

public class RealNameAuth implements Serializable {

    /**
     * fullname : 李二狗
     * idCard : 310381192202016788
     * password : xxxxxxxxxxxxxxxxxxxxxxxxxx
     * company : 美团
     * batteryType : 1
     */

    public String fullname;
    public String idCard;
    public String password;
    public String company;
    public String batteryType;

    public RealNameAuth(String fullname, String idCard, String password, String company, String batteryType) {
        this.fullname = fullname;
        this.idCard = idCard;
        this.password = password;
        this.company = company;
        this.batteryType = batteryType;
    }
}
