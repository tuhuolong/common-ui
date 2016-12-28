
package com.chenhao.lib.commonui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.chenhao.lib.commonui.R;

/**
 * Created by chenhao on 16/12/28.
 */

public class CircleProgressBar extends View {

    private static final int DEFAULT_MAX_VALUE = 100;
    private static final int DEFAULT_PAINT_WIDTH = 10;
    private static final int DEFAULT_PAINT_COLOR = 0xFFFFFFFF;
    private static final int DEFAULT_INSIDE_INDENT = 0;

    private CircleAttribute mCircleAttribute = new CircleAttribute();

    private Drawable mBackgroundPicture = null;

    private int mMax = DEFAULT_MAX_VALUE;
    private int mCurrent = 0;

    public CircleProgressBar(Context context) {
        super(context);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.CircleProgressBar);
        mMax = array.getInteger(R.styleable.CircleProgressBar_max, DEFAULT_MAX_VALUE);
        mCircleAttribute.setPaintWidth(
                array.getInt(R.styleable.CircleProgressBar_paint_width, DEFAULT_PAINT_WIDTH));
        mCircleAttribute.setPaintColor(array
                .getColor(R.styleable.CircleProgressBar_paint_color, DEFAULT_PAINT_COLOR));
        mCircleAttribute.insideIndent = array
                .getInt(R.styleable.CircleProgressBar_inside_indent, DEFAULT_INSIDE_INDENT);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        mBackgroundPicture = getBackground();
        if (mBackgroundPicture != null) {
            width = mBackgroundPicture.getMinimumWidth();
            height = mBackgroundPicture.getMinimumHeight();
        }

        setMeasuredDimension(resolveSize(width, widthMeasureSpec),
                resolveSize(height, heightMeasureSpec));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mCircleAttribute.resize(w, h);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mBackgroundPicture == null) {
            canvas.drawArc(mCircleAttribute.roundOval, 0, 360, true,
                    mCircleAttribute.backgroundPainter);
        }

        float rate = (float) mCurrent / mMax;
        float sweep = 360 * rate;
        canvas.drawArc(mCircleAttribute.roundOval, mCircleAttribute.startDegrees, sweep, false,
                mCircleAttribute.progressPainter);
    }

    public synchronized int getMax() {
        return mMax;
    }

    public synchronized void setMax(int max) {
        mMax = max;
        if (mMax < 0) {
            mMax = 0;
        }
        if (mMax < mCurrent) {
            mMax = mCurrent;
        }

        invalidate();
    }

    public synchronized int getProgress() {
        return mCurrent;
    }

    public synchronized void setProgress(int progress) {
        mCurrent = progress;
        if (mCurrent < 0) {
            mCurrent = 0;
        }
        if (mCurrent > mMax) {
            mCurrent = mMax;
        }

        invalidate();
    }

    class CircleAttribute {

        public RectF roundOval;
        public int insideIndent;
        public int paintWidth;
        public int paintColor;
        public int startDegrees;

        public Paint progressPainter;
        public Paint backgroundPainter;

        public CircleAttribute() {
            roundOval = new RectF();
            insideIndent = DEFAULT_INSIDE_INDENT;
            paintWidth = 0;
            paintColor = DEFAULT_PAINT_COLOR;
            startDegrees = -90;

            progressPainter = new Paint();
            progressPainter.setAntiAlias(true);
            progressPainter.setStyle(Paint.Style.STROKE);
            progressPainter.setStrokeWidth(paintWidth);
            progressPainter.setColor(paintColor);

            backgroundPainter = new Paint();
            backgroundPainter.setAntiAlias(true);
            backgroundPainter.setStyle(Paint.Style.STROKE);
            backgroundPainter.setStrokeWidth(paintWidth);
            backgroundPainter.setColor(Color.GRAY);
        }

        public void setPaintWidth(int width) {
            paintWidth = width;

            progressPainter.setStrokeWidth(width);
            backgroundPainter.setStrokeWidth(width);
        }

        public void setPaintColor(int color) {
            paintColor = color;

            progressPainter.setColor(color);
        }

        public void resize(int w, int h) {
            if (insideIndent != 0) {
                roundOval.set(paintWidth / 2 + insideIndent, paintWidth / 2 + insideIndent,
                        w - paintWidth / 2 - insideIndent, h - paintWidth / 2 - insideIndent);
            } else {
                int sl = getPaddingLeft();
                int sr = getPaddingRight();
                int st = getPaddingTop();
                int sb = getPaddingBottom();
                roundOval.set(sl + paintWidth / 2, st + paintWidth / 2, w - sr - paintWidth / 2,
                        h - sb - paintWidth / 2);
            }
        }

    }

}
