package com.yusong.plugin_login.ui

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.yusong.plugin_login.R
import kotlinx.android.synthetic.main.activity_test2.*

class Test2Activity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test2)
        textView.text =""

    }
}
