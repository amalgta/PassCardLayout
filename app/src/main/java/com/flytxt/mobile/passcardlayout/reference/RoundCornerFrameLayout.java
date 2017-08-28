package com.flytxt.mobile.passcardlayout.reference;

/**
 * Created by amal.george on 28-08-2017
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.flytxt.mobile.passcardlayout.R;
import com.flytxt.mobile.passcardlayout.ui.Utils;

/**
 * Frame layout that has rounded corners (it clips content too).
 *
 * @author Anton Chekulaev
 */
public class RoundCornerFrameLayout extends FrameLayout {

    private Path stencilPath = new Path();
    private float cornerRadius = 0;

    public RoundCornerFrameLayout(Context context) {
        this(context, null);
    }

    public RoundCornerFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundCornerFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray attrArray = context.obtainStyledAttributes(attrs, R.styleable.RoundCornerFrameLayout, 0, 0);
        try {
            cornerRadius = attrArray.getDimension(R.styleable.RoundCornerFrameLayout_corner_radius, 0f);
        } finally {
            attrArray.recycle();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // compute the path
        stencilPath.reset();
        //stencilPath = Utils.RoundedRect(0, 0, w, h, cornerRadius, cornerRadius, true, true, true, true);
        stencilPath.close();

    }

    @Override
    protected void dispatchDraw(@NonNull Canvas canvas) {
        int save = canvas.save();
        canvas.clipPath(stencilPath);
        super.dispatchDraw(canvas);
        canvas.restoreToCount(save);
    }
}