package com.yusong.yslib.mvp

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import com.yusong.yslib.utils.AndroidBug5497Workaround
import com.yusong.yslib.utils.AnimUtils

import java.lang.reflect.ParameterizedType


/**改写MVPPlus插件acticity基类
 * @author:feisher on 2017/10/17 9:50
 * Email：458079442@qq.com
 */
abstract class MVPBaseActivity<V : BaseView, T : BasePresenterImpl<V>> : AppCompatActivity(), BaseView {
    var mPresenter: T? = null
    lateinit var decorView: View
    var isSystemUiVisibility = true
    override val context: Context
        get() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = getInstance<T>(this, 1)
        mPresenter!!.attachView(this as V)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        decorView = window.decorView
        decorView.fitsSystemWindows = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT//竖屏
        if (layoutId() != 0) {
            setContentView(layoutId())
            AndroidBug5497Workaround.assistActivity(this)//软键盘问题
            adaptiveSystemVersions()
        }
        //        overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_right);
        initView()
        initData()
        initListener()
    }

    protected fun initListener() {
        decorView.setOnSystemUiVisibilityChangeListener { visibility ->
            if (visibility == 0) {//系统状态栏处于显示状态
                onWindowFocusChanged(true)
            } else {
                onWindowFocusChanged(false)
            }
        }
    }

    protected abstract fun layoutId(): Int

    abstract fun initData()

    fun initView() {

    }

    //沉浸式状态栏处理
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isSystemUiVisibility == false) {
                decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            } else {
                decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            }

        }
    }

    override fun onStop() {
        AnimUtils.cancelAnimator()
        super.onStop()
    }

    override fun onDestroy() {
        if (mPresenter != null) {
            mPresenter!!.detachView()
            mPresenter = null
        }
        super.onDestroy()
    }

    //用于适配titlebar
    protected fun adaptiveSystemVersions() {
        val iv = findViewById<View>(resources.getIdentifier("iv_adaptive_down_api18", "id", packageName))
                ?: return
        iv.visibility = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) View.VISIBLE else View.GONE
    }

    fun <T> getInstance(o: Activity, i: Int): T? {
        try {
            return (o::class.typeParameters[i] as Class<T>).newInstance()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

}
