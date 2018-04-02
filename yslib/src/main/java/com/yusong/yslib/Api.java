package com.yusong.yslib;

/** 网络接口集合   （如模块功能复杂协议较多无交互，可单独设立新的api）
 * create by feisher on 2017/9/18
 * Email：458079442@qq.com
 */
public class Api {
//    47.96.163.130
//    public static final String BASEURL = "http://hdg.yusong.com.cn:8080";//
    public static final String BASEURL = "https://hdg.yusong.com.cn:8081";//
//    public static final String BASEURL = "http://47.96.163.130:8080";//
    public static  String STACIC_SERVER = "";//
    public static final String DICT_ITEM = BASEURL+"/api/v1/common/basic/dict_item/list.htm";//
    //1-刷新token
    public static final String REFRESH_TOKEN = BASEURL+"/api/v1/common/basic/token/refresh_token.htm";//
    //12-查询个人信息
    public static final String USER_INFO = BASEURL+"/api/v1/customer/basic/customer/info.htm";//

}
