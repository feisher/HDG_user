package com.yusong.plugin_regist.entity.s;

import java.io.Serializable;

/**
 * @authorfeisher on 2017/12/23.10:59
 * email:458079442@qq.com
 */

public class PushToken implements Serializable{

    public int pushType;
    public String pushToken;

    public PushToken(int pushType, String pushToken) {
        this.pushType = pushType;
        this.pushToken = pushToken;
    }
}
