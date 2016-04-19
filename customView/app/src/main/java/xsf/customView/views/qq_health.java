package xsf.customView.views;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author xushangfei
 * @time created at 2016/4/19.
 * @email xsf_uestc_ncl@163.com
 */
public class qq_health extends View {
    private static String Tag = "qqHealthTest";
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

    private int[] mSteps;
    private float mRatio;

    private Context mContext;




    public qq_health(Context context) {
        super(context);
    }

    public qq_health(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public qq_health(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
