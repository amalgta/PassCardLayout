package com.flytxt.mobile.passcardlayout.reference;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.FrameLayout;

import com.flytxt.mobile.passcardlayout.ui.Utils;

/**
 * Created by amalg on 26-08-2017.
 */

public class FooterView extends FrameLayout {
    private final static float CORNER_RADIUS = 20.0f;
    private Path stencilPath = new Path();

    private Bitmap maskBitmap;
    private Paint paint;
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
        init(context, attrs, 0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        stencilPath.reset();
       // Utils.addRoundedRectBottom(stencilPath, 60, 0, 0, w, h, cornerRadius, cornerRadius, false, false, true, true);
    }

    @Override
    protected void dispatchDraw(@NonNull Canvas canvas) {
        int save = canvas.save();
        canvas.clipPath(stencilPath);
        super.dispatchDraw(canvas);
        canvas.restoreToCount(save);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        cornerRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, CORNER_RADIUS, metrics);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setWillNotDraw(false);
    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    @Override
    public void draw(Canvas canvas) {
        // canvas.drawPath(Utils.RoundedRect(0, 0, canvas.getWidth(), canvas.getHeight(), cornerRadius, cornerRadius, false, false, true, true), paint);
        super.draw(canvas);
    }
}