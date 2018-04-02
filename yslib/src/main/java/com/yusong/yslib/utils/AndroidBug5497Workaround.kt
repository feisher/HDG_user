package com.yusong.yslib.utils

//Workaround to get adjustResize functionality for input methos when the fullscreen mode is on
//found by Ricardo
//taken from http://stackoverflow.com/a/19494006

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout


class AndroidBug5497Workaround private constructor(activity: Activity) {
    internal var statusBarH = -1
    var anInt: Int = 0

    private val mChildOfContent: View
    private var usableHeightPrevious: Int = 0
    private val frameLayoutParams: FrameLayout.LayoutParams

    init {
        /**
         * 获取状态栏高度——方法1
         */
        //获取status_bar_height资源的ID
        val resourceId = activity.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarH = activity.resources.getDimensionPixelSize(resourceId)
        }

        val content = activity.findViewById<View>(android.R.id.content) as FrameLayout
        mChildOfContent = content.getChildAt(0)
        mChildOfContent.viewTreeObserver.addOnGlobalLayoutListener { possiblyResizeChildOfContent() }
        frameLayoutParams = mChildOfContent.layoutParams as FrameLayout.LayoutParams
    }

    private fun possiblyResizeChildOfContent() {
        val usableHeightNow = computeUsableHeight()
        if (usableHeightNow != usableHeightPrevious) {
            val usableHeightSansKeyboard = mChildOfContent.rootView.height
            val heightDifference = usableHeightSansKeyboard - usableHeightNow
            anInt = 4
            if (heightDifference > usableHeightSansKeyboard / anInt) {
                // keyboard probably just became visible
                frameLayoutParams.height = usableHeightSansKeyboard - heightDifference + statusBarH
            } else {
                // keyboard probably just became hidden
                frameLayoutParams.height = usableHeightSansKeyboard
            }
            mChildOfContent.requestLayout()
            usableHeightPrevious = usableHeightNow
        }
    }

    private fun computeUsableHeight(): Int {
        val r = Rect()
        mChildOfContent.getWindowVisibleDisplayFrame(r)
        if (r.top == 0) {
            r.top = statusBarH//状态栏目的高度
        }
        return r.bottom - r.top
    }

    companion object {
        // For more information, see https://code.google.com/p/android/issues/detail?id=5497
        // To use this class, simply diaLogInvoke assistActivity() on an Activity that already has its content view set.

        fun assistActivity(activity: Activity) {
            AndroidBug5497Workaround(activity)
        }
    }

} 