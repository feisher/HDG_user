package com.yusong.plugin_regist.entity.g;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ruanjian on 2017/12/19.
 */

public class Pay2 implements Serializable{
        @SerializedName("package")
        public String packageX;
        public String appid;
        public String sign;
        public String prepayid;
        public String partnerid;
        public String noncestr;
        public String timestamp;
        public String orderId;
}
