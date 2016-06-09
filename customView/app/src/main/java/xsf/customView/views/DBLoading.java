package xsf.customView.views;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * Author: xsf
 * Time: created at 2016/6/9.
 * Email: xsf_uestc_ncl@163.com
 * <p>
 * 豆瓣练习https://github.com/Idtk/Blog
 */
public class DBLoading extends View {
    private Context mContext;
    private Paint mPaint;
    private ValueAnimator animator;
    private float animatedValue;
    private long animatorDuration = 5000;
    private TimeInterpolator timeInterpolator = new DecelerateInterpolator();

    private int mWidth, mHeight;

    public DBLoading(Context context) {
        this(context, null);
    }

    public DBLoading(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DBLoading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        mPaint = generatePaint(Color.BLUE, Paint.Style.STROKE, 15);
        initAnimator(animatorDuration);

    }
    //--------------------------------------------系统函数--------------------------------

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2, mHeight / 2); //移动坐标系到中心
        float point = Math.min(mWidth, mHeight) * 0.2f / 2;
        float r = point * (float) Math.sqrt(2);
        RectF rectF = new RectF(-r, -r, r, r);
        canvas.save();
        // rotate
        if (animatedValue >= 135) {
            canvas.rotate(animatedValue - 135);
        }

        // draw mouth
        float startAngle = 0, sweepAngle = 0;
        if (animatedValue < 135) {
            startAngle = animatedValue + 5;
            sweepAngle = 170 + animatedValue / 3;
        } else if (animatedValue < 270) {
            startAngle = 135 + 5;
            sweepAngle = 170 + animatedValue / 3;
        } else if (animatedValue < 630) {
            startAngle = 135 + 5;
            sweepAngle = 260 - (animatedValue - 270) / 5;
        } else if (animatedValue < 720) {
            startAngle = 135 - (animatedValue - 630) / 2 + 5;
            sweepAngle = 260 - (animatedValue - 270) / 5;
        } else {
            startAngle = 135 - (animatedValue - 630) / 2 - (animatedValue - 720) / 6 + 5;
            sweepAngle = 170;
        }

        //转动弧
        canvas.drawArc(rectF, startAngle, sweepAngle, false, mPaint);

        //笑脸

        canvas.drawPoints(new float[]{point, -point, -point, -point}, mPaint);
        canvas.restore();


    }

    //--------------------------------------------功能函数---------------------------------

    //产生画笔
    private Paint generatePaint(int color, Paint.Style style, int width) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(style);
        paint.setStrokeWidth(width);
        paint.setAntiAlias(true);//抗锯齿
        paint.setStrokeCap(Paint.Cap.ROUND);//圆角笔触
        return paint;
    }


    private void initAnimator(long duration) {
        if (animator != null && animator.isRunning()) {
            animator.cancel();
            animator.start();
        } else {
            animator = ValueAnimator.ofFloat(0, 855).setDuration(duration);
            animator.setInterpolator(timeInterpolator);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    animatedValue = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            animator.start();
        }
    }


}
