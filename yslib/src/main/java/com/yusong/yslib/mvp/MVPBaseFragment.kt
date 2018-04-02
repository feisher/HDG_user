package com.yusong.yslib.mvp

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yusong.yslib.app
import com.yusong.yslib.toast
import com.yusong.yslib.utils.AnimUtils
import java.lang.reflect.ParameterizedType

/**
 * 改写MVPPlus插件Fragment基类
 * create by feisher on 2017/6/13 10:51
 * Email：458079442@qq.com
 */
abstract class MVPBaseFragment<V : BaseView, T : BasePresenterImpl<V>> : Fragment(), BaseView {
    var mPresenter: T? = null
    var mContext: Context? = null
    var mActivity: Activity? = null
    lateinit var mFragmentView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = getInstance<T>(this, 1)
        mPresenter!!.attachView(this as V)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = getContext()
        mActivity = activity
        mFragmentView = initView(inflater, container)
        initData()
        initListener()
        return mFragmentView
    }


    abstract fun initView(inflater: LayoutInflater, container: ViewGroup?): View

    abstract fun initData()

    fun initListener() {
    }

    override fun onDestroy() {
        if (mPresenter != null) {
            mPresenter!!.detachView()
        }
        AnimUtils.cancelAnimator()
        super.onDestroy()
    }

    override fun getContext(): FragmentActivity? = activity

    fun <T> getInstance(o: Fragment, i: Int): T? {
        try {
            return (o::class.typeParameters[i] as Class<T>).newInstance()
        } catch (e: Fragment.InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: ClassCastException) {
            e.printStackTrace()
        } catch (e: java.lang.InstantiationException) {
            e.printStackTrace()
        }

        return null
    }

}
