package com.cdh.lodingviewlibary;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by c27235 on 2018/4/27.
 */

public class DigitalLoadingView extends View implements ValueAnimator.AnimatorUpdateListener {

    private Paint mArcPaint;
    private Paint mCirclePaint;
    private Paint mTextPaint;

    private RectF mArcRectF;

    private int mCircelRadius;

    private int mArcColor;
    private int mCircleColor;
    private int mTextColor;

    private int mTextSize;

    private int mWidth;

    private int mHeight;

    private int mArcWidth = 20;

    private float mStartAngle = 0;

    private float mSweepAngle = 0;

    private ValueAnimator mValueAnimator;

    private int mDuration;

    private String mPercentText = "0%";

    private Rect mBound;


    public DigitalLoadingView(Context context) {
        this(context, null);
    }

    public DigitalLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DigitalLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        mArcColor = Color.CYAN;
        mCircleColor = Color.BLUE;
        mTextColor = Color.WHITE;
        mTextSize = 50;
        mDuration = 1000;


        mCircelRadius = 150;
        mArcRectF = new RectF(-mCircelRadius - mArcWidth / 2, -mCircelRadius - mArcWidth / 2, mCircelRadius + mArcWidth / 2, mCircelRadius + mArcWidth / 2);

        mArcPaint = new Paint();
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setColor(mArcColor);
        mArcPaint.setStrokeWidth(20);

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(mCircleColor);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);

        mValueAnimator = ValueAnimator.ofInt(0, 100);
        mValueAnimator.setDuration(mDuration);
        mValueAnimator.addUpdateListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(mCircelRadius + mArcWidth, mCircelRadius + mArcWidth);

        // 画圆
        canvas.drawCircle(0, 0, mCircelRadius, mCirclePaint);
        // 画外圆弧
        canvas.drawArc(mArcRectF, mStartAngle, mSweepAngle, false, mArcPaint);
        // 画百分比
        mBound = new Rect();
        mTextPaint.getTextBounds(mPercentText, 0, mPercentText.length(), mBound);
        canvas.drawText(mPercentText, -mBound.width() / 2, mBound.height() / 2, mTextPaint);
    }

    public void start() {
        if (!mValueAnimator.isRunning()) {
            mValueAnimator.start();
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(widthMeasureSpec);

        int width = 0;
        int height = 0;
        if (widthMode == MeasureSpec.EXACTLY) { // match_parent和精确的值
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) { // 如果是 wrap_center
            width = getPaddingLeft() + mCircelRadius * 2 + mArcWidth * 2 + getPaddingRight();
        }

        if (heightMode == MeasureSpec.EXACTLY) { // match_parent和精确的值
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) { // 如果是 wrap_center
            height = getPaddingTop() + mCircelRadius * 2 + mArcWidth * 2 + getPaddingBottom();
        }

        setMeasuredDimension(width, height);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        int curValue = (int) valueAnimator.getAnimatedValue();
//        Log.d("chengdh", "CurV: " + curValue);
        mPercentText = curValue + "%";

        float space = 360.0f * curValue / 100.0f;
        mSweepAngle = space;
        Log.d("chengdh", "SweepAngle: " + mSweepAngle);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        start();
        return super.onTouchEvent(event);
    }
}
