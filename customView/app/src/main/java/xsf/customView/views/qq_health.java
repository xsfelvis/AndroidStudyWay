package xsf.customView.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import xsf.customView.R;
import xsf.customView.util.Utils;

/**
 * @author xushangfei
 * @time created at 2016/4/19.
 * @email xsf_uestc_ncl@163.com
 */
public class qq_health extends View {
    private static String TAG = "qqHealthTest";
    private int mWidth;
    private int mHeight;
    private int mBackgroundCorner;//背景四角的角度

    private int mArcCenterX;
    private int mArcCenterY;
    private RectF mArcRect;

    private Paint mBackgroundPaint;
    private Paint mArcPaint;//最上面弧线的画笔
    private Paint mTextPaint;
    private Paint mDashLinePaint;//虚线的画笔
    private Paint mBarPaint;//竖条的画笔
    private Paint mAvatarPaint;//

    private int[] mSteps;
    private float mRatio;

    private Context mContext;

    private int mDefaultThemeColor;//默认主题色
    private int mDefaultUpBackgroundColor;//上层默认的背景色

    private int mThemeColor;
    private int mUpBackgroundColor;
    private float mArcWidth;
    private float mBarWidth;
    private int mMaxStep;
    private int mAverageStep;
    private int mTotalSteps;


    public qq_health(Context context) {
        this(context, null);
    }

    public qq_health(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public qq_health(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {

        setLayerType(LAYER_TYPE_SOFTWARE, null);//关闭硬件加速
        mRatio = 450.f / 525.f;//宽高比
        mBackgroundCorner = Utils.dp2px(mContext, 8);
        mDefaultThemeColor = Color.parseColor("#2EC3FD");
        mDefaultUpBackgroundColor = Color.WHITE;
        mThemeColor = mDefaultThemeColor;
        mUpBackgroundColor = mDefaultUpBackgroundColor;
        mSteps = new int[]{10050, 15280, 8900, 9200, 6500, 5660, 9450};
        calaulateStep();

        //背景画笔
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setColor(mThemeColor);

        //圆弧画笔
        mArcPaint = new Paint();
        mArcPaint.setColor(mThemeColor);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStyle(Paint.Style.STROKE);//空心
        mArcPaint.setDither(true);//防抖动
        mArcPaint.setStrokeJoin(Paint.Join.ROUND);//画笔连接处是圆润的
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);//画笔起始处是圆润的
        mArcPaint.setPathEffect(new CornerPathEffect(10));//圆形拐角效果

        //文字画笔
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);

        //虚线画笔
        mDashLinePaint = new Paint();
        mDashLinePaint.setAntiAlias(true);
        mDashLinePaint.setColor(Color.parseColor("#C1C1C1"));
        mDashLinePaint.setStyle(Paint.Style.STROKE);
        mDashLinePaint.setPathEffect(new DashPathEffect(new float[]{8, 4}, 0));

        //竖条画笔
        mBarPaint = new Paint();
        mBarPaint.setColor(mThemeColor);
        mBarPaint.setAntiAlias(true);
        mBarPaint.setStrokeCap(Paint.Cap.ROUND);

        //头像画笔
        mAvatarPaint = new Paint();
        mAvatarPaint.setAntiAlias(true);


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int defaultWidth = Integer.MAX_VALUE;
        int width, height;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST) {
            width = widthSize;
        } else {
            width = defaultWidth;
        }
        int defaultHeight = (int) (width * 1.f / mRatio);
        height = defaultHeight;
        setMeasuredDimension(width, height);
        Log.i(TAG, "width:" + width + "| height:" + height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mArcCenterX = (int) (mWidth / 2.f);
        mArcCenterY = (int) (160.f / 525.f * mHeight);
        mArcRect = new RectF();
        mArcRect.left = mArcCenterX - 125.f / 450.f * mWidth;
        mArcRect.top = mArcCenterY - 125.f / 525.f * mHeight;
        mArcRect.right = mArcCenterX + 125.f / 450.f * mWidth;
        mArcRect.bottom = mArcCenterY + 125.f / 525.f * mHeight;

        mArcWidth = 20.f / 450.f * mWidth;
        mBarWidth = 16.f / 450.f * mWidth;

        //设置画笔宽度
        mArcPaint.setStrokeWidth(mArcWidth);
        mBarPaint.setStrokeWidth(mBarWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //绘制最下层背景
        drawBelowBackground(0, 0, mWidth, mHeight, mBackgroundCorner, canvas, mBackgroundPaint);
        //绘制上层的背景
        mBackgroundPaint.setColor(mUpBackgroundColor);
        drawUpBackground(0, 0, mWidth, mWidth, mBackgroundCorner, canvas, mBackgroundPaint);

        //绘制圆弧
        //水平x轴为0度，顺时针方向，下面表示起始位置为120度，顺时针扫过300度方向
        canvas.drawArc(mArcRect, 120, 300, false, mArcPaint);

        //绘制圆弧内部的文字
        drawArcText(mArcCenterX, mArcCenterY, canvas, mTextPaint);

        //绘制圆弧下方的文字及虚线
        drawOutArcText(mArcCenterX, mArcCenterY, canvas, mTextPaint);
        //绘制下面的线条
        drawBars(canvas, mTextPaint);

        //绘制底部头像及文字
        drawBottom(canvas, mTextPaint);


    }

    private void drawBottom(Canvas canvas, Paint mTextPaint) {
        float xPos = 100.f / 450.f * mWidth;
        float yPos = (mHeight - mWidth) / 2.f + mWidth + 20.f / 450.f * mWidth / 2;
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(20.f / 450.f * mWidth);
        mTextPaint.setTextAlign(Paint.Align.LEFT);//绘制文字焦点在左边
        canvas.drawText("xsfelvis今日获得冠军", xPos, yPos, mTextPaint);

        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.portrait);
        RectF dst = new RectF();//绘制头像用的矩形
        int rectWidth = (int) (30.f / 525.f * mHeight);//矩形的宽度
        dst.left = xPos - 40.f / 450.f * mWidth;
        dst.top = (mHeight - mWidth) / 2 + mWidth - rectWidth / 2.f;
        dst.bottom = (int) ((mHeight - mWidth) / 2.f + mWidth + rectWidth / 2.f);
        dst.right = (int) (xPos - 10.f / 450 * mWidth);
        bitmap = toRoundBitmap(bitmap);//将原始图片转化为圆形图片
        canvas.drawBitmap(bitmap, null, dst, mAvatarPaint);//绘制头像

        xPos = 425.f / 450.f * mWidth;
        mTextPaint.setTextAlign(Paint.Align.RIGHT);
        mTextPaint.setTextSize(15.f / 450.f * mWidth);
        canvas.drawText("查看 >", xPos, yPos, mTextPaint);

    }

    /**
     * 将
     *
     * @param bitmap
     * @return
     */
    private Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int r;
        if (width > height) r = height;
        else r = width;
        Bitmap background = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(background);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        RectF rect = new RectF(0, 0, r, r);
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        canvas.drawRoundRect(rect, r / 2, r / 2, paint);
        return background;
    }

    private void drawBars(Canvas canvas, Paint mTextPaint) {
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(10.f / 450.f * mWidth);
        float startY = 388.f / 525.f * mHeight;
        for (int i = 0; i < mSteps.length; i++) {
            float barHeight = mSteps[i] * 1.f / mAverageStep * 30.f / 525.f * mHeight;
            float startX = 55.f / 450.f * mWidth + i * (57.f / 450.f * mWidth);
            float stopX = startX;
            float stopY = startY - barHeight;
            if (mSteps[i] < mAverageStep) mBarPaint.setColor(Color.parseColor("#C1C1C1"));
            else mBarPaint.setColor(mThemeColor);
            canvas.drawLine(startX, startY, stopX, stopY, mBarPaint);
        }


    }

    private void drawOutArcText(float mArcCenterX, float mArcCenterY, Canvas canvas, Paint mTextPaint) {
        float xPos = 25.f / 450.f * mWidth;
        float yPos = 320.f / 525.f * mHeight;
        mTextPaint.setTextAlign(Paint.Align.LEFT);//绘制文字焦点在左边
        mTextPaint.setColor(Color.parseColor("#C1C1C1"));
        mTextPaint.setTextSize(12.f / 450.f * mWidth);
        canvas.drawText("最近七天", xPos, yPos, mTextPaint);
        xPos = (int) ((450.f - 25.f) / 450.f * mWidth);
        yPos = (int) (320.f / 525.f * mHeight);
        mTextPaint.setTextAlign(Paint.Align.RIGHT);
        mTextPaint.setColor(Color.parseColor("#C1C1C1"));
        mTextPaint.setTextSize(12.f / 450.f * mWidth);
        canvas.drawText("平均" + mAverageStep + "步/天", xPos, yPos, mTextPaint);

        //画虚线
        xPos = 25.f / 450.f * mWidth;
        yPos = 352.f / 525.f * mHeight;

        float endX = xPos + (450.f - 50.f) / 450.f * mWidth;
        float endY = yPos;
        canvas.drawLine(xPos, yPos, endX, endY, mDashLinePaint);


    }

    private void drawArcText(float mArcCenterX, float mArcCenterY, Canvas canvas, Paint mTextPaint) {
        float xPos = mArcCenterX;
        float yPos = mArcCenterY - 45.f / 525.f * mHeight;
        mTextPaint.setTextAlign(Paint.Align.CENTER);//绘制文字在基准点中央
        mTextPaint.setTextSize(15.f / 450.f * mWidth);
        mTextPaint.setColor(Color.parseColor("#C1C1C1"));
        canvas.drawText("截止22:44分您已走", xPos, yPos, mTextPaint);

        mTextPaint.setTextSize(42.f / 450.f * mWidth);
        mTextPaint.setColor(mThemeColor);
        canvas.drawText(mSteps[mSteps.length - 1] + "", mArcCenterX, mArcCenterY, mTextPaint);

        mTextPaint.setColor(Color.parseColor("#C1C1C1"));
        mTextPaint.setTextSize(13.f / 450.f * mWidth);
        yPos = mArcCenterY + 50.f / 525.f * mHeight;
        canvas.drawText("好友平均步数7800", xPos, yPos, mTextPaint);

        xPos = mArcCenterX - 35.f / 450.f * mWidth;
        yPos = mArcCenterY + 120.f / 525.f * mHeight;
        canvas.drawText("第", xPos, yPos, mTextPaint);

        xPos = (int) (mArcCenterX + 35.f / 450.f * mWidth);
        canvas.drawText("名", xPos, yPos, mTextPaint);
        mTextPaint.setColor(mThemeColor);
        mTextPaint.setTextSize(24.f / 450.f * mWidth);
        canvas.drawText("10", mArcCenterX, yPos, mTextPaint);


    }

    private void drawUpBackground(int left, int top, int right, int bottom, int radius, Canvas canvas, Paint paint) {
        Path path = new Path();
        path.moveTo(left, top);
        //右上圆角
        path.lineTo(right - radius, top);
        path.quadTo(right, top, right, top + radius);
        //右下直角
        path.lineTo(right, bottom);
        //左下直角
        path.lineTo(left, bottom);
        //左上圆角
        path.lineTo(left, top + radius);
        path.quadTo(left, top, left + radius, top);

        canvas.drawPath(path, paint);


    }

    /**
     * 通过贝塞尔曲线的方式绘制圆角矩形
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @param radius
     * @param canvas
     * @param paint
     */
    private void drawBelowBackground(int left, int top, int right, int bottom, int radius, Canvas canvas, Paint paint) {
        Path path = new Path();
        path.moveTo(left, top);
        //画右上角圆弧
        path.lineTo(right - radius, top);
        path.quadTo(right, top, right, top + radius);
        //画右下角圆弧
        path.lineTo(right, bottom - radius);
        path.quadTo(right, bottom, right - radius, bottom);
        //画左下角圆弧
        path.lineTo(left + radius, bottom);
        path.quadTo(left, bottom, left, bottom - radius);
        //画右上角圆弧
        path.lineTo(left, top + radius);
        path.quadTo(left, top, left + radius, top);
        canvas.drawPath(path, paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        RectF rectF = new RectF();
        rectF.top = mWidth;
        rectF.left = 380.f / 450.f * mWidth;
        rectF.right = mWidth;
        rectF.bottom = mHeight;
        if (rectF.contains(event.getX(), event.getY())) {//当前点击的坐标在右下角的范围内
            //在这里可以做点击事件的监听
            Snackbar.make(this, "Click", Snackbar.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onTouchEvent(event);
        }
    }

    /**
     * 计算本周总共步数,最大步数，平均步数
     */
    private void calaulateStep() {
        mTotalSteps = 0;
        mMaxStep = 0;
        mAverageStep = 0;
        for (int i = 0; i < mSteps.length; i++) {
            mTotalSteps += mSteps[i];
            if (mMaxStep < mSteps[i])
                mMaxStep = mSteps[i];

        }

        mAverageStep = (int) (mTotalSteps * 1.f / mSteps.length);

    }

    /**
     * 对外提供的颜色接口
     *
     * @param color
     */
    public void setThemeColor(int color) {
        mThemeColor = color;
        mBackgroundPaint.setColor(mThemeColor);
        mArcPaint.setColor(mThemeColor);
        mBarPaint.setColor(mThemeColor);
        invalidate();
    }

    /**
     * 设置步数的数组
     *
     * @param steps
     */
    public void setSteps(int[] steps) {
        if (steps == null || steps.length == 0)
            throw new IllegalArgumentException("输入步数格式不正确");
        mSteps = steps;
        calaulateStep();
        invalidate();
    }

}
