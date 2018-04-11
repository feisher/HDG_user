package com.yusong.plugin_regist.entity.g;

import java.io.Serializable;
import java.util.List;

/**
 * @authorfeisher on 2017/11/20.16:13
 * email:458079442@qq.com
 */

public class Setting1 implements Serializable{
    public String tel;
    public String staticUrl;
    public String registerProtocol;
    public String aboutUs;
    public String faq;
    public int lowVolume;
    public List<CustomerNoticeMessageTypeListBean> customerNoticeMessageTypeList;
    public List<UserNoticeMessageTypeListBean> userNoticeMessageTypeList;

    public static class CustomerNoticeMessageTypeListBean implements Serializable{
        public int value;
        public String name;
    }

    public static class UserNoticeMessageTypeListBean implements Serializable{
        public int value;
        public String name;
    }

}
