package com.yusong.hdg_user

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.activity_test.*

@Route(path = "/test/TestActivity")
class TestActivity : AppCompatActivity() {
    //注意此处注解，必须
    @Autowired
    @JvmField var name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        textView.text = name+"\n 点击返回"
        textView.setOnClickListener {
            finish()
        }
    }
}
