package com.flytxt.mobile.passcardlayout.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.flytxt.mobile.passcardlayout.R;

/**
 * Created by amalg on 25-08-2017.
 */

public class PassCardLayout extends RelativeLayout {
    public static final int INVALID_INT = -1;
    private FrameLayout headerLayout, footerLayout;
    private View headerView, footerView;
    private MedianView medianLayout;
    private int parameterHeaderLayout, parameterFooterLayout;

    interface Defaults {
        int headerColor = Color.GREEN;
        int footerColor = Color.RED;
    }

    int headerColor, footerColor;

    public PassCardLayout(Context context) {
        super(context);
        init(null);
    }

    public PassCardLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public PassCardLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PassCardLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PassCardLayout);
            headerColor = typedArray.getColor(R.styleable.PassCardLayout_headerColor, Defaults.headerColor);
            footerColor = typedArray.getColor(R.styleable.PassCardLayout_footerColor, Defaults.footerColor);

            parameterHeaderLayout = typedArray.getResourceId(R.styleable.PassCardLayout_headerLayout, -1);
            parameterFooterLayout = typedArray.getResourceId(R.styleable.PassCardLayout_footerLayout, -1);
            typedArray.recycle();
        }

        headerLayout = new HeaderView(getContext());
        //headerLayout = (FrameLayout) inflate(getContext(), R.layout.pass_card_header, null);

        medianLayout = new MedianView(getContext());
        medianLayout.setCircleRadius(10);
        medianLayout.setCustomPadding(10);
        medianLayout.setHeaderColor(headerColor);
        medianLayout.setFooterColor(footerColor);

//        footerLayout = (FrameLayout) inflate(getContext(), R.layout.pass_card_footer, null);
        footerLayout = new FooterView(getContext());

        headerView = inflate(getContext(), parameterHeaderLayout, null);
        footerView = inflate(getContext(), parameterFooterLayout, null);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
/*
        if (getChildCount() != 2) {
            throw new IllegalStateException("PassCardLayout can hold only two direct children");
        }
*/
        LayoutParams headerLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        headerLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        headerLayout.setId(R.id.headerLayout);
        headerLayout.setPadding(20, 20, 20, 20);
        headerLayout.addView(headerView);
        addView(headerLayout, headerLayoutParams);

        LayoutParams medianLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        medianLayoutParams.addRule(RelativeLayout.BELOW, headerLayout.getId());
        medianLayout.setId(R.id.medianLayout);
        addView(medianLayout, medianLayoutParams);

        LayoutParams footerLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        footerLayoutParams.addRule(RelativeLayout.BELOW, medianLayout.getId());
        footerLayout.setId(R.id.footerLayout);
        footerLayout.setPadding(20, 20, 20, 20);
        footerLayout.addView(footerView);
        addView(footerLayout, footerLayoutParams);

    }

    class HeaderView extends FrameLayout {
        private final static float CORNER_RADIUS = 40.0f;

        private Bitmap maskBitmap;
        private Paint paint, maskPaint;
        private float cornerRadius;

        public HeaderView(Context context) {
            super(context);
            init(context, null, 0);
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

            maskPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
            maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

            setWillNotDraw(false);
        }

        @Override
        public void draw(Canvas canvas) {
            Bitmap offscreenBitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
            //Canvas offscreenCanvas = new Canvas(offscreenBitmap);

            //super.draw(offscreenCanvas);
            canvas.drawPath(Utils.RoundedRect(0, 0, canvas.getWidth(), canvas.getHeight(), CORNER_RADIUS, CORNER_RADIUS, true, true, false, false), paint);
            super.draw(canvas);
            /**
             if (maskBitmap == null) {
             maskBitmap = createMask(canvas.getWidth(), canvas.getHeight());
             }

             offscreenCanvas.drawBitmap(maskBitmap, 0f, 0f, maskPaint);
             canvas.drawBitmap(offscreenBitmap, 0f, 0f, paint);
             */
        }

        private Bitmap createMask(int width, int height) {
            Bitmap mask = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8);
            Canvas canvas = new Canvas(mask);

            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.WHITE);

            canvas.drawRect(0, 0, width, height, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            canvas.drawRoundRect(new RectF(0, 0, width, height), cornerRadius, cornerRadius, paint);

            return mask;
        }
    }

    class FooterView extends FrameLayout {
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

        private Bitmap createMask(int width, int height) {
            Bitmap mask = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8);
            Canvas canvas = new Canvas(mask);

            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.WHITE);

            canvas.drawRect(0, 0, width, height, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            canvas.drawRoundRect(new RectF(0, 0, width, height), cornerRadius, cornerRadius, paint);

            return mask;
        }


    }
}
