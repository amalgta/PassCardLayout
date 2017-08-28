package com.flytxt.mobile.passcardlayout.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

import com.flytxt.mobile.passcardlayout.R;

/**
 * Created by amalg on 25-08-2017.
 */

public class MedianView extends View {

    interface Defaults {
        int headerColor = Color.GREEN;
        int footerColor = Color.RED;
        int dividerColor = Color.BLACK;
    }

    private int circleRadius, customPadding, dividerWidth;

    private Paint headerPaint, footerPaint, centerDividerPaint;

    private int headerColor, footerColor, dividerColor;

    public MedianView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MedianView(Context context) {
        super(context);
        init(null);
    }

    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        centerDividerPaint.setColor(dividerColor);
        invalidate();
    }

    public void setDividerWidth(int dividerWidth) {
        this.dividerWidth = dividerWidth;
        centerDividerPaint.setStrokeWidth(dividerWidth);
        invalidate();
    }

    private void init(AttributeSet attrs) {
        setSaveEnabled(true);
        if (attrs != null) {
            TypedArray ta = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.MedianView, 0, 0);
            headerColor = ta.getColor(R.styleable.MedianView_headerColor, Defaults.headerColor);
            footerColor = ta.getColor(R.styleable.MedianView_footerColor, Defaults.footerColor);
            circleRadius = ta.getDimensionPixelSize(R.styleable.MedianView_circleRadius, 0);
            customPadding = ta.getDimensionPixelSize(R.styleable.MedianView_customPadding, 0);
            dividerColor = ta.getColor(R.styleable.MedianView_dividerColor, Defaults.dividerColor);
            dividerWidth = ta.getDimensionPixelSize(R.styleable.MedianView_dividerWidth, 0);
            ta.recycle();
        }
        headerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        headerPaint.setColor(headerColor);

        footerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        footerPaint.setColor(footerColor);

        centerDividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerDividerPaint.setStrokeWidth(dividerWidth);
        centerDividerPaint.setARGB(255, 0, 0, 0);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        centerDividerPaint.setStyle(Paint.Style.STROKE);
        centerDividerPaint.setPathEffect(new DashPathEffect(new float[]{8, 8}, 0));
        centerDividerPaint.setColor(dividerColor);
    }

    public void setCircleRadius(int circleRadius) {
        this.circleRadius = circleRadius;
        invalidate();
    }

    public void setCustomPadding(int customPadding) {
        this.customPadding = customPadding;
        invalidate();
    }

    public void setHeaderColor(int headerColor) {
        headerPaint.setColor(headerColor);
        invalidate();
    }

    public void setFooterColor(int footerColor) {
        footerPaint.setColor(footerColor);
        invalidate();
    }

    private int measureHeight(int measureSpec) {
        int size = getPaddingTop() + getPaddingBottom();
        size += circleRadius * 2;
        size += customPadding * 2;
        return resolveSizeAndState(size, measureSpec, 0);
    }

    private int measureWidth(int measureSpec) {
        int size = getPaddingLeft() + getPaddingRight();
        Rect bounds = new Rect();
        size += bounds.width();
        return resolveSizeAndState(size, measureSpec, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawMedian(canvas);
    }

    private void drawMedian(Canvas canvas) {
        float barCenter = getBarCenter();

        Path p = new Path();
        p.addCircle(0, barCenter, circleRadius, Path.Direction.CW);
        p.addCircle(getWidth(), barCenter, circleRadius, Path.Direction.CW);
        canvas.clipPath(p, Region.Op.DIFFERENCE);

        p.addRect(0, 0, getWidth(), getHeight() / 2, Path.Direction.CW);
        canvas.drawPath(p, headerPaint);

        Path p2 = new Path();
        p2.addCircle(0, barCenter, circleRadius, Path.Direction.CW);
        p2.addCircle(getWidth(), barCenter, circleRadius, Path.Direction.CW);
        canvas.clipPath(p2, Region.Op.DIFFERENCE);

        p2.addRect(0, getHeight() / 2, getWidth(), getHeight(), Path.Direction.CW);
        canvas.drawPath(p2, footerPaint);

        canvas.drawLine(circleRadius * 2 + 12, getBarCenter(), getWidth() - circleRadius * 2 - 12, getBarCenter(), centerDividerPaint);
    }

    private float getBarCenter() {
        return (float) ((getHeight() - getPaddingTop() - getPaddingBottom()) / 2);
    }
}