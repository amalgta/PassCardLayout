package com.flytxt.mobile.passcardlayout.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.FrameLayout;

/**
 * Created by amalg on 26-08-2017.
 */

public class PassCardInternalContainer extends FrameLayout {
    private final static float CORNER_RADIUS = 20.0f;
    private Path stencilPath = new Path();
    private float cornerRadius;

    enum Mode {Header, Footer}

    /**
     * Default mode is header
     **/
    Mode mode = Mode.Header;

    public PassCardInternalContainer(Context context) {
        super(context);
        init(context, null, 0);
    }

    public void setCornerRadius(float cornerRadius) {
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        this.cornerRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, cornerRadius, metrics);
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        stencilPath.reset();
        boolean isHeader = mode == Mode.Header;
        Utils.addRoundedRect(stencilPath, 0, 0, w, h, cornerRadius, cornerRadius, isHeader, isHeader, !isHeader, !isHeader);
    }

    @Override
    protected void dispatchDraw(@NonNull Canvas canvas) {
        int save = canvas.save();
        canvas.clipPath(stencilPath);
        super.dispatchDraw(canvas);
        canvas.restoreToCount(save);
    }

    public PassCardInternalContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public PassCardInternalContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        cornerRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, CORNER_RADIUS, metrics);
    }
}