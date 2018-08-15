package com.yusong.yslib

/** 网络接口集合   （如模块功能复杂协议较多无交互，可单独设立新的api）
 * create by feisher on 2017/9/18
 * Email：458079442@qq.com
 */
object Api {
       //todo  详细接口已删除，具体项目参考实际情况处理
    val BASEURL = "https://yusong.com.cn"
    var STACIC_SERVER = "http://com"
    var UPLOAD = "$STACIC_SERVER/security/upload/attachment.htm"

    // 1-刷新token
    val REFRESH_TOKEN = "$BASEURL/api/v1//basic//refresh_token.htm"
    //14-查询设置
    val SETTING = "$BASEURL/api/v1//basic//setting.htm"
    //16-查询字典项目
    val DICT_ITEM = "$BASEURL/api/v1///dict_item/list.htm"
    //12-查询个人信息
    val USER_INFO = "$BASEURL/api/v1//basic//info.htm"
    //11-实名认证
   

}
