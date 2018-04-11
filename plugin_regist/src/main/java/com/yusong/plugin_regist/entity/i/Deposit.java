package com.yusong.plugin_regist.entity.i;

import java.io.Serializable;

/**
 * @authorfeisher on 2017/11/24.16:21
 * email:458079442@qq.com
 */

public class Deposit implements Serializable {
    public int baiduCityId;
    public String districtName;
    public String batteryType;
    public int setmealType;

    public Deposit(int baiduCityId, String districtName, String batteryType) {
        this.baiduCityId = baiduCityId;
        this.districtName = districtName;
        this.batteryType = batteryType;
    }

    public Deposit(int baiduCityId, String districtName, String batteryType, int setmealType) {
        this.baiduCityId = baiduCityId;
        this.districtName = districtName;
        this.batteryType = batteryType;
        this.setmealType = setmealType;
    }
}
