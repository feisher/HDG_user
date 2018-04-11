package com.yusong.plugin_regist.entity.s;

import java.io.Serializable;

/**
 * @authorfeisher on 2017/11/20.12:49
 * email:458079442@qq.com
 */

public class LoginByAuthCode implements Serializable{
    public String mobile;
    public String authCode;

    public LoginByAuthCode(String mobile, String authCode) {
        this.mobile = mobile;
        this.authCode = authCode;
    }
}
