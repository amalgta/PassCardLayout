package com.flytxt.mobile.passcardlayout.reference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.flytxt.mobile.passcardlayout.R;

/**
 * Created by amalg on 25-08-2017.
 */

public class PassCardLayout2 extends LinearLayout {
    public static final int INVALID_INT = -1;
    int headerLayout, footerLayout;

    public PassCardLayout2(Context context) {
        super(context);
        init();
    }

    public PassCardLayout2(Context context, AttributeSet attrs) {
        super(context, attrs);
        processAttrs(attrs);
        init();
    }

    public PassCardLayout2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        processAttrs(attrs);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PassCardLayout2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        processAttrs(attrs);
        init();
    }


    private void processAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PassCardLayout2);

        int headerLayout = typedArray.getResourceId(R.styleable.PassCardLayout2_headerLayout, INVALID_INT);
        int footerLayout = typedArray.getResourceId(R.styleable.PassCardLayout2_footerLayout, INVALID_INT);

        if (headerLayout != INVALID_INT)
            this.headerLayout = headerLayout;
        if (footerLayout != INVALID_INT)
            this.footerLayout = footerLayout;

        typedArray.recycle();
    }

    private void init() {
        setOrientation(VERTICAL);
        /**
        View headerLayout = inflate(getContext(), R.layout.pass_card_header, this);
        addView(headerLayout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        View seperatorLayout = inflate(getContext(), R.layout.pass_card_median, this);
        addView(seperatorLayout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        View footerLayout = inflate(getContext(), R.layout.pass_card_footer, this);
        addView(footerLayout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
         */
    }

}
