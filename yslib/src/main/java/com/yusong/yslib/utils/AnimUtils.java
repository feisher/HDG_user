package com.yusong.yslib.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * create by feisher on 2017/4/7
 * Email：458079442@qq.com
 */
public class AnimUtils {

    public static AnimatorSet animatorSet;
    public static ObjectAnimator rotation;
    public static View targetView;
    public static final long DEFULT_DURATION = 500;
    private static ValueAnimator valueAnimator;

    /**
     * 数值变化动画，用于处理界面中数字显示
     * @param targetView
     * @param targetValue 接收float & int 其他值转换为这两种类型
     */
    public static <E extends Number>ValueAnimator numberAnimator(final TextView targetView, final E targetValue){
        AnimUtils.targetView = targetView;
        if (targetValue instanceof Float||targetValue instanceof Double) {
            valueAnimator = ValueAnimator.ofFloat(0,targetValue.floatValue());
        }else if (targetValue instanceof Integer||targetValue instanceof Long) {
            valueAnimator = ValueAnimator.ofInt(0,targetValue.intValue());
        }
        valueAnimator.setDuration(DEFULT_DURATION);
        return valueAnimator;
    }
    /**
     * 数值变化动画，用于处理界面中数字显示
     * @param targetView
     * @param targetValue 接收float & int 其他值转换为这两种类型
     */
    public static <E extends Number>void numberAnimator(final TextView targetView, final String frontSelt, final E targetValue, final String backSelt){
        AnimUtils.targetView = targetView;
        ValueAnimator valueAnimator = null;
        if (targetValue instanceof Float||targetValue instanceof Double) {
            valueAnimator = ValueAnimator.ofFloat(0,targetValue.floatValue());
        }else if (targetValue instanceof Integer||targetValue instanceof Long) {
            valueAnimator = ValueAnimator.ofInt(0,targetValue.intValue());
        }

        valueAnimator.setDuration(DEFULT_DURATION);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (targetValue instanceof Float||targetValue instanceof Double) {
                    float animatedValue = (float) animation.getAnimatedValue();
                    targetView.setText(""+frontSelt+new DecimalFormat("0.00").format(animatedValue)+backSelt);
                }else if (targetValue instanceof Integer||targetValue instanceof Long) {
                    int animatedValue = (int) animation.getAnimatedValue();
                    targetView.setText(""+frontSelt+animatedValue+backSelt);
                }
            }
        });
        valueAnimator.start();
    }

    /**
     * 以中心y轴旋转并渐变
     * @param targetView 执行动画的view
     * @param duration 持续时长
     */
    public static void flipInYAnimator(View targetView, int duration){
        AnimUtils.targetView = targetView;
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(targetView, "rotationY", -90, 0),
                ObjectAnimator.ofFloat(targetView, "scaleY", 0.75f, 1),
                ObjectAnimator.ofFloat(targetView, "alpha", 0.33f,1),
                ObjectAnimator.ofFloat(targetView, "scaleX", 0.33f, 1));
        animatorSet.setDuration(duration);
        animatorSet.start();
    }
    public static void exitToRightAnimator(View targetView){
        AnimUtils.targetView = targetView;
        measureView(targetView);
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(targetView, "translationX", 0, targetView.getWidth()),
                ObjectAnimator.ofFloat(targetView, "rotationY",0, 90)
        );
        targetView.setPivotX(0);
        targetView.setPivotY(targetView.getHeight()>>1);
        animatorSet.setDuration(DEFULT_DURATION*4);
        animatorSet.start();
    }
    public static void exitToLeftAnimator(View targetView){
        AnimUtils.targetView = targetView;
        measureView(targetView);
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(targetView, "translationX",0,-targetView.getWidth()),
                ObjectAnimator.ofFloat(targetView, "rotationY",0, -90)
        );
        targetView.setPivotX(targetView.getWidth());
        targetView.setPivotY(targetView.getHeight()>>1);
        animatorSet.setDuration(DEFULT_DURATION*4);
        animatorSet.start();
    }

    public static void enterFromLeftAnimator(View targetView){
        AnimUtils.targetView = targetView;
        measureView(targetView);
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(targetView, "translationX", -targetView.getWidth(),0)
                , ObjectAnimator.ofFloat(targetView, "rotationY", -90,0)
        );
        targetView.setPivotX(targetView.getWidth());
        targetView.setPivotY(targetView.getHeight()>>1);
        animatorSet.setDuration(DEFULT_DURATION*4);
        animatorSet.start();
    }

    public static void enterFromRightAnimator(View targetView){
        AnimUtils.targetView = targetView;
        measureView(targetView);
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(targetView, "translationX", targetView.getWidth(),0),
                ObjectAnimator.ofFloat(targetView, "rotationY", 90,0)
        );
        targetView.setPivotX(0);
        targetView.setPivotY(targetView.getHeight()>>1);
        animatorSet.setDuration(DEFULT_DURATION*4);
        animatorSet.start();

    }
    private static void measureView(View targetView) {
        AnimUtils.targetView = targetView;
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        targetView.measure(w, h);
    }
    /**
     * 以中心旋转
     * @param targetView 执行动画的view
     */
    public static void startWhirlAnimator(View targetView){
        whirlAnimator(targetView);
        rotation.start();
    }

    public static void cancelAnimator(){
        if (null!=rotation) {
            rotation.removeAllListeners();
            AnimUtils.targetView.clearAnimation();
            rotation.cancel();
            rotation=null;

        }
        if (null!=animatorSet){
            animatorSet.removeAllListeners();
            AnimUtils.targetView.clearAnimation();
            animatorSet.cancel();
            animatorSet=null;
        }
        if (null!=valueAnimator){
            valueAnimator.removeAllUpdateListeners();
            valueAnimator.removeAllListeners();
            AnimUtils.targetView.clearAnimation();
            valueAnimator=null;
        }

    }

    /**
     * 以中心旋转
     * @param targetView 执行动画的view
     */
    public static void whirlAnimator(View targetView){
        AnimUtils.targetView = targetView;
        rotation = ObjectAnimator.ofFloat(targetView, "rotation", 360,0);
        rotation.setDuration(DEFULT_DURATION);
        rotation.setRepeatMode(ValueAnimator.RESTART);
        rotation.setRepeatCount(ObjectAnimator.INFINITE);
        rotation.setInterpolator(new LinearInterpolator());//匀速旋转，防止循环时卡顿
    }

    public static void zoomInRightAnimator(View targetView, int duration){
        AnimUtils.targetView = targetView;
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(targetView, "scaleX", 0.1f, 1f, 1f),
                ObjectAnimator.ofFloat(targetView, "scaleY", 0.1f, 1f, 1f),
                ObjectAnimator.ofFloat(targetView, "translationX", targetView.getWidth()>>1 , 0f, 0f),
                ObjectAnimator.ofFloat(targetView, "alpha", 0f, 1f, 1f));
        animatorSet.setDuration(duration);
        animatorSet.start();
    }

    public static void clickAnimator(View targetView){
        AnimUtils.targetView = targetView;
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(targetView, "scaleX", 0.9f, 1f),
                ObjectAnimator.ofFloat(targetView, "scaleY", 0.9f, 1f),
                ObjectAnimator.ofFloat(targetView, "alpha", 0.9f, 1f)
                );
        animatorSet.setInterpolator(new BounceInterpolator());
        animatorSet.setDuration(DEFULT_DURATION);
        animatorSet.start();
    }
    public static void touchDownAnimator(View targetView){
        AnimUtils.targetView = targetView;
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(targetView, "scaleX", 1f, 0.9f),
                ObjectAnimator.ofFloat(targetView, "alpha", 1f, 0.75f),
                ObjectAnimator.ofFloat(targetView, "scaleY", 1f,0.9f));
        animatorSet.setDuration(DEFULT_DURATION/4);
        animatorSet.start();
    }
    public static void touchUpAnimator(View targetView){
        AnimUtils.targetView = targetView;
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(targetView, "scaleX",  0.9f,1f),
                ObjectAnimator.ofFloat(targetView, "alpha",  0.75f,1f),
                ObjectAnimator.ofFloat(targetView, "scaleY", 0.9f,1f));
        animatorSet.setInterpolator(new BounceInterpolator());
        animatorSet.setDuration(DEFULT_DURATION);
        animatorSet.start();
    }
    public static void alphaInAnimator(View targetView, long duration){
        AnimUtils.targetView = targetView;
        ObjectAnimator.ofFloat(targetView, "alpha", 0, 1).setDuration(duration).start();
    }
    public static void alphaOutAnimator(View targetView, long duration){
        AnimUtils.targetView = targetView;
        ObjectAnimator.ofFloat(targetView, "alpha", 1, 0).setDuration(duration).start();
    }

    public static View getRootView(Activity context) {
        return ((ViewGroup)context.findViewById(android.R.id.content)).getChildAt(0);
    }
}
