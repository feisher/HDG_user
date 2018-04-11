package com.yusong.yslib.http

/**
 * Created by feisher on 2018/1/26.
 */

class HttpResult<T> {
    var data: T? = null
    var code: Int = 0
    var message: String? = null

}
