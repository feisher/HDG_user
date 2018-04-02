package com.yusong.yslib.event;

import java.io.Serializable;

/**事件总线  兼容直接使用数值实现通知刷新逻辑
 *
 * EventBus.getDefault().post(new EventCenter<>(Const.REFRESH_CODE));//通知刷新
 *
 *
 * create by feisher on 2017/9/19 13:13
 * Email：458079442@qq.com
 */
public class EventCenter<T> implements Serializable{
    public static final  int httpError = 400;
    public static final  int TOKEN_INVALID = 0x40121240;
    public static final  int NO_MAPAPP = 0x12154;
    public static final  int REFRESH_BROKEN = 400;
    public static final  int PAY_SUCESS = 1;
    public static final  int REFRESH_ORDER = 2;
    public int eventCode = -1;

    public T data;
    public EventCenter(int eventCode) {
        this.eventCode = eventCode;
    }

    public EventCenter(int eventCode, T data) {
        this.eventCode = eventCode;
        this.data = data;
    }


}