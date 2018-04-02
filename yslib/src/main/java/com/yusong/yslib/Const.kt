package com.yusong.yslib

import android.content.Context
import android.content.Intent

/**
 * @author:feisher on 2017/10/17 9:49
 * Email：458079442@qq.com
 */
class Const private constructor() {
    //用户协议
    var hotline = "400-636-2775"
    var registerProtocol: String? = null
    var faq: String? = null
    var aboutUs: String? = null
    var lowVolume: Int = 0


    private object SingletonHolder {
        val INSTANCE = Const()
    }

    companion object {

        val DESIGN_WIDTH = 750f //绘制页面时参照的设计图宽度
        val DESIGN_HIGHT = 1334f //绘制页面时参照的设计图宽度
        val REFRESH_CODE = 1
        val LOADMORE_CODE = 2
        val RESULT_CAPTURE_IMAGE = 100// 照相的requestCode
        val REQUEST_CODE_TAKE_VIDEO = 101// 摄像的照相的requestCode
        val RESULT_CAPTURE_RECORDER_SOUND = 102// 录音的requestCode
        val RESULT_LOCATION_CODE = 103// 定位的requestCode
        val MOBAPI_APP_KEY = "1a1956ca75731"//天气预报，老黄历
        val ACTION_INIT_APP = "com.yusong.club.service.action.INIT"
        val SALT = "67884E9%^&*67899A26C18DC28" //标准协议上没有*

        val LOOP_TTS_LIMIT = 1 //轮询播报语音次数

        val instance: Const
            get() = SingletonHolder.INSTANCE

        //SP 常量
        val USER_NAME = "user_name"
        val PASS_WORD = "pass_word"
        val TOKEN = "token"

        val TEST_TOKEN = "7730cd3eadef2a49a8abbe4a249ffb51904b"

        var authStep: Int = 0
    }

}
