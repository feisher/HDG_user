package com.yusong.hdg_user

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.activity_main.*

@Route(path = "/test/MainActivity")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv.text = "点击跳转s测试页面";
        tv.setOnClickListener {
            ARouter.getInstance()
                    .build("/test/TestActivity")
                    .withString("name","从主页面传递的数据")
                    .navigation()
        }
    }
}
