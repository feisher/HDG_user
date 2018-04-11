package com.yusong.plugin_regist.entity.s;

import java.io.Serializable;

/**
 * @author feisher on 2018/2/5.15:30
 * email:458079442@qq.com
 */

public class SignGet implements Serializable {

    public String mobile;
    public String sign;
    public String imageAuthCode;

    public SignGet(String mobile, String sign, String imageAuthCode) {
        this.mobile = mobile;
        this.sign = sign;
        this.imageAuthCode = imageAuthCode;
    }
}
