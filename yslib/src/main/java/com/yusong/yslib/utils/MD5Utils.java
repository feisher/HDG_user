package com.yusong.yslib.utils;

import com.yusong.yslib.Const;

import java.security.MessageDigest;

/**
 * @author feisher
 */
public class MD5Utils {

    static final String PASSWORD_DELTA_1 = Const.Companion.getSALT();

    public static String md5Password(String src) {
        return md5(PASSWORD_DELTA_1 + sha(PASSWORD_DELTA_1 + src));
    }

    public static String sha(String src) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA");
            byte[] a = md.digest(src.getBytes("utf-8"));
            return byteToHex(a);

        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }

    public static String md5(String src) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] a = md.digest(src.getBytes("utf-8"));
            return byteToHex(a);

        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }

    public static String byteToHex(byte[] a) {
        StringBuilder buf = new StringBuilder();

        for (int i = 0; i < a.length; i++) {
            if (a[i] < 0) {
                buf.append(Integer.toHexString(a[i] & 0xff));
            } else if (a[i] < 16) {
                buf.append('0');
                buf.append(Integer.toHexString(a[i]));
            } else {
                buf.append(Integer.toHexString(a[i]));
            }
        }

        return buf.toString().toUpperCase();
    }

}