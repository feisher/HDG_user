package com.yusong.yslib.utils;

import android.text.TextUtils;

/**
 * @author Mr_Peng
 * @created at 2017-11-21.
 * @describe: 字符处理类
 */

public class StringUtil {
    /**
     * 手机号用****号隐藏中间数字
     *
     * @param phone
     * @return
     */
    public static String settingPhone(String phone) {
        String phone_s = "";
        if (!TextUtils.isEmpty(phone) && phone.length() == 11) {
            phone_s = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }
        return phone_s;
    }

    /**
     * 邮箱用****号隐藏前面的字母
     *
     * @return
     */
    public static String settingEmail(String email) {
        String emails = email.replaceAll("(\\w?)(\\w+)(\\w)(@\\w+\\.[a-z]+(\\.[a-z]+)?)", "$1****$3$4");
        return emails;
    }
}
