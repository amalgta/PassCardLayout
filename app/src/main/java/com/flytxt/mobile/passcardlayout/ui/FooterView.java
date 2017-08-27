package com.flytxt.mobile.passcardlayout.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.FrameLayout;

/**
 * Created by amalg on 26-08-2017.
 */

public class FooterView extends FrameLayout {
    private final static float CORNER_RADIUS = 40.0f;

    private Bitmap maskBitmap;
    private Paint paint, maskPaint;
    private float cornerRadius;

    public FooterView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public FooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public FooterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        cornerRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, CORNER_RADIUS, metrics);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        maskPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        setWillNotDraw(false);
    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    @Override
    public void draw(Canvas canvas) {
        Bitmap offscreenBitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
        //Canvas offscreenCanvas = new Canvas(offscreenBitmap);

        //super.draw(offscreenCanvas);

        canvas.drawPath(Utils.RoundedRect(0, 0, canvas.getWidth(), canvas.getHeight(), CORNER_RADIUS, CORNER_RADIUS, false, false, true, true), paint);
        super.draw(canvas);
        /**
         if (maskBitmap == null) {
         maskBitmap = createMask(canvas.getWidth(), canvas.getHeight());
         }

         offscreenCanvas.drawBitmap(maskBitmap, 0f, 0f, maskPaint);
         canvas.drawBitmap(offscreenBitmap, 0f, 0f, paint);
         */
    }
}