package com.yusong.plugin_regist.entity.s;

import java.io.Serializable;

/**
 * @authorfeisher on 2017/11/13.16:54
 * email:458079442@qq.com
 */

public class Login implements Serializable{
    String mobile;
    String password;

    public Login(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }
}
