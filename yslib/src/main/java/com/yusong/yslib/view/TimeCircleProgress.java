package com.yusong.yslib.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.text.DecimalFormat;

/**  环形进度条
 * @explain  圆环会自动根据宽度高度取小边的大小的比例 你也可以自己设置圆环的大小
 * @see #scrollBy(int, int)
 * @author feisher.qq:458079442
 * @time 2018/1/2 13:46.
 */
public class TimeCircleProgress extends View {

    private int width;// 控件的宽度
    private int height;// 控件的高度
    private int radius;// 圆形的半径
    private int socktwidth = dp2px(8);// 圆环进度条的宽度
    private Paint paint = new Paint();
    private Rect rec = new Rect();
    private float time = 0f;// 充电时长 h;
    private float totalTime = 12;// 总时长;
    private int value = (int) (time*100/totalTime);// 百分比0~100;
    private int textSize = dp2px(30);// 文字大小
    private int preColor = Color.parseColor("#EF88B6");// 进度条未完成的颜色
    private int progressColor = Color.parseColor("#F02F85");// 进度条颜色
    private int textColor = progressColor;// 文字颜色
    private onProgressListener monProgress;// 进度时间监听
    private int startAngle = 270;
    RectF rectf = new RectF();

    public TimeCircleProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public TimeCircleProgress(Context context) {
        this(context,null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        width = getWidth();
        height = getHeight();
        radius = height > width ? width : height;
        Paint paint1  = new Paint();
        rectf.set(socktwidth,socktwidth,radius-socktwidth,radius-socktwidth);
        paint1.setAntiAlias(true);
        //画笔形状 圆形
        paint1.setStrokeCap(Paint.Cap.ROUND);
        //描边，用来画圆环
        paint1.setStyle(Paint.Style.STROKE);
        // 绘制圆环的背景颜色（未走到的进度）
        paint1.setColor(preColor);
        paint1.setStrokeWidth(socktwidth);
        canvas.drawArc(rectf,0, 100 * 3.6f, false, paint1);
        //绘制进度圆弧(真实进度)
        paint1.setColor(progressColor);
        paint1.setStrokeWidth(socktwidth*2);
        canvas.drawArc(rectf, startAngle, value * 3.6f, false, paint1);

        String showTime = time%1==0?(int)time+"小时":time+"小时";
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.getTextBounds(showTime, 0, showTime.length(), rec);
        int textwidth = rec.width();
        int textheight = rec.height();
        // 绘制中间文字
//        canvas.drawText(showTime, (width - textwidth) / 2, ((height + textheight) / 2), paint);
        //处理 提供的view宽高不是正方形的情况
        canvas.drawText(showTime, (radius - textwidth) / 2, ((radius + textheight) / 2), paint);
        super.onDraw(canvas);
    }

    public int dp2px(int dp) {
        return (int) ((getResources().getDisplayMetrics().density * dp) + 0.5);
    }

    /**
     * 设置进度 
     *
     * @param value
     *            <p> 
     *            ps: 百分比 0~100; 
     */
    public void setValue(int value) {
        if (value > 100) {
            return;
        }
        this.value = value;
        invalidate();
        if (monProgress != null) {
            monProgress.onProgress(value);
        }
    }

    /**
     *  设置充电总时长和使用时长
     * @param time 使用时长
     * @param totalTime 套餐总时长
     */
    public  void setTime(float time, final float totalTime){
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, time).setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                String format = new DecimalFormat("0.0").format(animatedValue);
                TimeCircleProgress.this.time = Float.parseFloat(format);
                int value = (int) (animatedValue * 100 / totalTime);
                setValue(value);
            }
        });
        valueAnimator.start();
    }


    /**
     * 设置圆环进度条的宽度 px 
     */
    public TimeCircleProgress setProdressWidth(int width) {
        this.socktwidth = width;
        return this;
    }

    /**
     * 设置文字大小 
     *
     * @param value
     */
    public TimeCircleProgress setTextSize(int value) {
        textSize = value;
        return this;
    }

    /**
     * 设置文字颜色
     * @param color
     */
    public TimeCircleProgress setTextColor(int color) {
        this.textColor = color;
        return this;
    }

    /**
     * 设置进度条之前的颜色 
     *
     * @param precolor
     */
    public TimeCircleProgress setPreProgress(int precolor) {
        this.preColor = precolor;
        return this;
    }

    /**
     * 设置进度颜色 
     *
     * @param color
     */
    public TimeCircleProgress setProgress(int color) {
        this.progressColor = color;
        return this;
    }


    /**
     * 设置开始的位置 
     *
     * @param startAngle
     *            0~360 
     *            <p> 
     *            ps 0代表在最右边 90 最下方 按照然后顺时针旋转 
     */
    public TimeCircleProgress setStartAngle(int startAngle) {
        this.startAngle = startAngle;
        return this;
    }

    public interface onProgressListener {
        void onProgress(int value);
    }
}  