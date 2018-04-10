package com.yusong.yslib

/** 网络接口集合   （如模块功能复杂协议较多无交互，可单独设立新的api）
 * create by feisher on 2017/9/18
 * Email：458079442@qq.com
 */
object Api {
    //    47.96.163.130
    //    public static final String BASEURL = "http://hdg.yusong.com.cn:8080";
    //    public static final String BASEURL = "https://hdg.yusong.com.cn:8081";
    val BASEURL = "https://hdg.yusong.com.cn:443"
    //    public static final String BASEURL = "http://192.9.198.241:8090";
    var STACIC_SERVER = "http://hdg.yusong.com.cn:8140"
    var UPLOAD = "$STACIC_SERVER/security/upload/attachment.htm"

    // 1-刷新token
    val REFRESH_TOKEN = "$BASEURL/api/v1/common/basic/token/refresh_token.htm"
    //14-查询设置
    val SETTING = "$BASEURL/api/v1/common/basic/query/setting.htm"
    //16-查询字典项目
    val DICT_ITEM = "$BASEURL/api/v1/common/basic/dict_item/list.htm"
    //12-查询个人信息
    val USER_INFO = "$BASEURL/api/v1/customer/basic/customer/info.htm"
    //11-实名认证
    val REALNAME_AUTH = "$BASEURL/api/v1/customer/basic/customer/authentication.htm"
    //2-客户登录
    val LOGIN = "$BASEURL/api/v1/customer/basic/customer/login.htm"
    //3-客户验证码登录
    val LOGIN_BY_CODE = "$BASEURL/api/v1/customer/basic/customer/login_by_auth_code.htm"
    //4-发送短信验证码
    val GET_AUTH_CODE = "$BASEURL/api/v1/common/basic/auth_code/get.htm"
    //15-查询换电押金
    val BATTERY_FOREGIFT = "$BASEURL/api/v1/customer/hdg/battery_foregift/list.htm"
    //8-查询充值套餐
    val CUSTOMER_DEPOSIT_GIFT = "$BASEURL/api/v1/customer/basic/customer_deposit_gift/list.htm"
    //9-查询可用优惠券
    val COUPON_TICKET = "$BASEURL/api/v1/customer/basic/customer_coupon_ticket/list.htm"
    //7-查询包时段套餐
    val TIME_SETMEAL = "$BASEURL/api/v1/customer/hdg/active_packet_period_price/list.htm"
    //10-查询救援
    val RESCUE_LIST = "$BASEURL/api/v1/customer/hdg/relief_station/list.htm"
    //14-查询附近充电柜(关键字)
    val SEARCH_BY_KEYWORD = "$BASEURL/api/v1/customer/hdg/cabinet/search_by_keyword.htm"
    //14-查询附近充电柜(地图)
    val NEAR_SITE = "$BASEURL/api/v1/customer/hdg/cabinet/nearest.htm"
    //12-故障报修
    val FAULT_FEEDBACK = "$BASEURL/api/v1/customer/hdg/fault_feedback/create.htm"
    //5-押金支付(余额)
    val PAY_BY_BALANCE = "$BASEURL/api/v1/customer/basic/customer_foregift_order/create_by_balance.htm"
    //5-押金支付(微信)
    val PAY_BY_WECHAT = "$BASEURL/api/v1/customer/basic/customer_foregift_order/create_by_weixin.htm"
    //5-押金支付(支付宝)
    val PAY_BY_ALIPAY = "$BASEURL/api/v1/customer/basic/customer_foregift_order/create_by_alipay.htm"
    //3-扫描柜子上的登录二维码(扫描)
    val SCAN_LOGIN_QRCODE = "$BASEURL/api/v1/customer/basic/customer/scan_login_qrcode.htm"
    //4-扫描柜子上的登录二维码(登录)
    val SCAN_LOGIN = "$BASEURL/api/v1/customer/basic/customer/confirm_login_qrcode.htm"
    //15-查询充电柜详情
    val CABINET_INFO = "$BASEURL/api/v1/customer/hdg/cabinet/info.htm"
    //5-查询账单
    val BILL_LIST = "$BASEURL/api/v1/customer/basic/customer_in_out_money/list.htm"
    //28-查询我的优惠券
    val MY_COUPON = "$BASEURL/api/v1/customer/basic/customer/coupon_ticket_list.htm"
    //27-查询我的余额
    val BALANCE = "$BASEURL/api/v1/customer/basic/customer/balance.htm"
    //8-查询充值套餐
    val RECHARGE_SET = "$BASEURL/api/v1/customer/basic/customer_deposit_gift/list.htm"
    //9-充值(微信)
    val RECHARG_WX = "$BASEURL/api/v1/customer/basic/customer_deposit_order/create_by_weixin.htm"
    //   9-充值(支付宝)
    val RECHARG_ZFB = "$BASEURL/api/v1/customer/basic/customer_deposit_order/create_by_alipay.htm"

    //20-支付旧电费用(支付宝)
    val CHARGE_PAY_ZFB = "$BASEURL/api/v1/customer/hdg/battery_order/pay_by_alipay.htm"
    //20-支付旧电费用(微信)
    val CHARGE_WX = "$BASEURL/api/v1/customer/hdg/battery_order/pay_by_weixin.htm"
    //20-支付旧电费用(余额)
    val CHARGE_BLANCE = "$BASEURL/api/v1/customer/hdg/battery_order/pay_by_balance.htm"

    //23-查询包时段订单
    val PACKET_PERIOD_ORDER = "$BASEURL/api/v1/customer/hdg/packet_period_order/list.htm"
    //24-根据包时段查询换电记录
    val BATTERY_ORDER_LIST = "$BASEURL/api/v1/customer/hdg/packet_period_order/battery_order_list.htm"
    //37-查询换电记录
    val LIST_BY_CUSTOMER = "$BASEURL/api/v1/customer/hdg/battery_order/list_by_customer.htm"
    //25-包月订单申请退款
    val APPLY_REFUND = "$BASEURL/api/v1/customer/hdg/packet_period_order/apply_refund.htm"
    //31-申请退还电池押金
    val REFUND = "$BASEURL/api/v1/customer/basic/customer_foregift_order/apply_refund.htm"
    //32-查询可退租电池的柜子列表
    val BACK_CABINET_LIST = "$BASEURL/api/v1/customer/hdg/cabinet/back_cabinet_list.htm"
    //33-申请退租电池
    val BACK_BATTERY_ORDER = "$BASEURL/api/v1/customer/hdg/back_battery_order/create.htm"
    //34-查询退租电池订单明细
    val BACK_BATTERY_ORDER_DETAIL = "$BASEURL/api/v1/customer/hdg/back_battery_order/detail.htm"
    //35-取消退电池
    val BACK_BATTERY_ORDER_CANEL = "$BASEURL/api/v1/customer/hdg/back_battery_order/cancel.htm"
    //    36-退租订单开空箱
    val OPEN_EMPTY_BOX = "$BASEURL/api/v1/customer/hdg/back_battery_order/open_empty_box.htm"
    //38-取消申请退电池押金
    val CANCEL_APPLY_REFUND = "$BASEURL/api/v1/customer/basic/customer_foregift_order/cancel_apply_refund.htm"
    //39-查询电池押金状态
    val QUERY_DEPOSIT_STATUS = "$BASEURL/api/v1/customer/basic/customer_foregift_order/status.htm"

    //13-扫柜子二维码
    val QRCODE_SCAN = "$BASEURL/api/v1/customer/basic/qrcode/scan.htm"

    //17-修改个人信息
    val UPDATE_INFO = "$BASEURL/api/v1/customer/basic/customer/update_info.htm"
    //21-查询新电并开箱
    val BATTERY_ORDER_OPEN_FULL_BOX = "$BASEURL/api/v1/customer/hdg/cabinet/battery_order_open_full_box.htm"
    //22-查询新电并重新开箱
    @Deprecated("")
    val BATTERY_ORDER_REOPEN_FULL_BOX = "$BASEURL/api/v1/customer/hdg/cabinet/battery_order_reopen_full_box.htm"
    //16-重新开箱(放旧电)
    val BATTERY_ORDER_REOPEN_EMPTY_BOX = "$BASEURL/api/v1/customer/hdg/cabinet/battery_order_reopen_empty_box.htm"
    //17-查询空箱号并打开箱门(放旧电)
    val BATTERY_ORDER_OPEN_EMPTY_BOX = "$BASEURL/api/v1/customer/hdg/cabinet/battery_order_open_empty_box.htm"
    //    41-换电轮询
    val LOOP_EXCHANGE_BATTERY = "$BASEURL/api/v1/customer/hdg/cabinet/loop_exchange_battery.htm"
    //    43-旧电在箱轮询
    @Deprecated("")
    val LOOP_OLD_BATTERY_IN_BOX = "$BASEURL/api/v1/customer/hdg/cabinet/loop_old_battery_in_box.htm"
    //    40-查询箱门状态
    val BOX_STATUS = "$BASEURL/api/v1/customer/hdg/cabinet/box_status.htm"

    //18-查询租赁电池信息
    val BATTERY_INFO = "$BASEURL/api/v1/customer/basic/customer/battery_info.htm"

    //10-上报推送标识
    val REPORT_PUSH_TOKEN = "$BASEURL/api/v1/customer/basic/customer/report_push_token.htm"
    //11-换电站位置纠错
    val CORRECT_SITE_INFO = "$BASEURL/api/v1/customer/hdg/cabinet_address_correction/create.htm"
    //19-查询最新版本
    val QUERE_UPDATE = "$BASEURL/api/v1/common/basic/upgrade/latest_version.htm"
    //21-查询通知消息列表
    val QUERY_MASSAGE_LIST = "$BASEURL/api/v1/customer/basic/customer_notice_message/list.htm"
    //18-取出旧电
    val BATTERY_ORDER_TAKE_OLD_BATTERY = "$BASEURL/api/v1/customer/hdg/cabinet/battery_order_take_old_battery.htm"
    //14-修改密码
    val UPDATE_PASSWORD2 = "$BASEURL/api/v1/customer/basic/customer/update_password2.htm"
    //    8-购买包时段套餐(余额)
    val RECHARGE_SETMEAL_BALANCE = "$BASEURL/api/v1/customer/hdg/packet_period_order/create_by_balance.htm"
    //  8-  购买包时段套餐(微信)
    val RECHARGE_SETMEAL_WEIXIN = "$BASEURL/api/v1/customer/hdg/packet_period_order/create_by_weixin.htm"
    //  8-  购买包时段套餐(支付宝)
    val RECHARGE_SETMEAL_ALIPY = "$BASEURL/api/v1/customer/hdg/packet_period_order/create_by_alipay.htm"
    //1-获取图片验证码地址
    //    public static final String GET_AUTH_IMAGE_URL = "https://hdg.yusong.com.cn:8081" + "/api/v1/common/basic/auth_code/get_auth_image_url.htm";
    val GET_AUTH_IMAGE_URL = "$BASEURL/api/v1/common/basic/auth_code/get_auth_image_url.htm"
    //4-发送短信验证码2
    //    public static final String SIGN_GET = "https://hdg.yusong.com.cn:8081" + "/api/v1/common/basic/auth_code/sign_get.htm";
    val SIGN_GET = "$BASEURL/api/v1/common/basic/auth_code/sign_get.htm"

    //7-意见反馈
    val FEEDBACK = "$BASEURL/api/v1/customer/basic/feedback/create.htm"

    //    45-查询电池充电订单列表
    val CHONGDIAN_ORDER_LIST = "$BASEURL/api/v1/customer/hdg/battery_charge_order/list_by_customer.htm"
    //    42-查询电池充电价格设置
    val CHARGE_PRICE_SETTING = "$BASEURL/api/v1/customer/hdg/battery/charge_price_setting.htm"
    //43-查询电池充电订单明细
    val CHONGDIAN_ORDER_DETAIL = "$BASEURL/api/v1/customer/hdg/battery_charge_order/detail.htm"
    //    44-查询电池充电功率列表
    val POWER_LIST = "$BASEURL/api/v1/customer/hdg/battery_charge_order/power_list.htm"
    //    46-创建电池充电订单(余额)
    val CREATE_BY_BALANCE = "$BASEURL/api/v1/customer/hdg/battery_charge_order/create_by_balance.htm"
    //    46-创建电池充电订单(微信)
    val CREATE_BY_WEIXIN = "$BASEURL/api/v1/customer/hdg/battery_charge_order/create_by_weixin.htm"
    //    46-创建电池充电订单(支付宝)
    val CREATE_BY_ALIPAY = "$BASEURL/api/v1/customer/hdg/battery_charge_order/create_by_alipay.htm"
    //    46-创建电池充电订单(充电包月套餐)
    val CREATE_PACKET = "$BASEURL/api/v1/customer/hdg/battery_charge_order/create_by_packet.htm"

    //47-充电包时段订单申请退款
    val APPLY_REFUND2 = "$BASEURL/api/v1/customer/hdg/charge_packet_order/apply_refund.htm"
    //  48-查询充电包时段套餐列表
    val CHARGE_PACKET_PERIOD_PRICE_LIST = "$BASEURL/api/v1/customer/hdg/charge_packet_period_price/list.htm"
    //49-购买包时段套餐(微信)
    val CHARGE_PACKET_ORDER_WEIXIN_PAY = "$BASEURL/api/v1/customer/hdg/charge_packet_order/create_by_weixin.htm"
    //49-购买包时段套餐(余额)
    val CHARGE_PACKET_ORDER_BALANCE_PAY = "$BASEURL/api/v1/customer/hdg/charge_packet_order/create_by_balance.htm"
    //49-购买包时段套餐(支付宝)
    val CHARGE_PACKET_ORDER_ALPAY = "$BASEURL/api/v1/customer/hdg/charge_packet_order/create_by_alipay.htm"
    // 50-查询充电包时段订单
    val CHARGE_PACKET_ORDER_LIST = "$BASEURL/api/v1/customer/hdg/charge_packet_order/list.htm"

    // 51-根据充电包时段查询充电记录
    val CHARGE_ORDER_LIST = "$BASEURL/api/v1/customer/hdg/charge_packet_order/charge_order_list.htm"
    //52-查询电池充电订单状态信息《轮询》
    val STATUS_INFO = "$BASEURL/api/v1/customer/hdg/battery_charge_order/status_info.htm"

}
