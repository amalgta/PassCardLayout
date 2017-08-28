package com.flytxt.mobile.passcardlayout.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.FrameLayout;

/**
 * Created by amalg on 26-08-2017.
 */

public class HeaderView extends FrameLayout {
    private final static float CORNER_RADIUS = 20.0f;

    private Path stencilPath = new Path();

    private Paint paint;
    private float cornerRadius;

    public HeaderView(Context context) {
        super(context);
        init(context, null, 0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // compute the path
        stencilPath.reset();
        //stencilPath.addRoundRect(0, 0, w, h, cornerRadius, cornerRadius, Path.Direction.CW);
        stencilPath=Utils.RoundedRect(0, 0, w, h, cornerRadius, cornerRadius, true, true, false, false);
        stencilPath.close();
    }

    @Override
    protected void dispatchDraw(@NonNull Canvas canvas) {
        int save = canvas.save();
        canvas.clipPath(stencilPath);
        super.dispatchDraw(canvas);
        canvas.restoreToCount(save);
    }

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public HeaderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
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
        //Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        //canvas.drawARGB(0, 0, 0, 0);
        //Rect rect = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());
        //RectF rectF = new RectF(rect);

        //paint.setColor(Color.BLACK);
        //canvas.drawRoundRect(rectF, CORNER_RADIUS, CORNER_RADIUS, paint);

        //paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //paint.setXfermode(null);

        //canvas.drawPath();
        super.draw(canvas);
    }
}