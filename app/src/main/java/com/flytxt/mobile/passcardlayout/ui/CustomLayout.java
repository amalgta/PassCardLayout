package com.flytxt.mobile.passcardlayout.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.flytxt.mobile.passcardlayout.R;


/**
 * Created by amalg on 26-08-2017.
 */

@RemoteViews.RemoteView
public class CustomLayout extends ViewGroup {
    int headerColor, footerColor;

    interface Defaults {
        int headerColor = Color.GREEN;
        int footerColor = Color.RED;
    }

    private HeaderView headerLayout;
    private FooterView footerLayout;

    private MedianView medianView;

    /**
     * The amount of space used by children in the left gutter.
     */
    private int mLeftWidth;

    /**
     * The amount of space used by children in the right gutter.
     */
    private int mRightWidth;

    /**
     * These are used for computing child frames based on their gravity.
     */
    private final Rect mTmpContainerRect = new Rect();
    private final Rect mTmpChildRect = new Rect();

    public CustomLayout(Context context) {
        super(context);
    }

    public CustomLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(attrs);
    }

    public CustomLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PassCardLayout);
            headerColor = typedArray.getColor(R.styleable.PassCardLayout_headerColor, PassCardLayout.Defaults.headerColor);
            footerColor = typedArray.getColor(R.styleable.PassCardLayout_footerColor, PassCardLayout.Defaults.footerColor);

            typedArray.recycle();
        }

        headerLayout = new HeaderView(getContext());
        headerLayout.setColor(headerColor);
        headerLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        medianView = new MedianView(getContext());
        medianView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        medianView.setCircleRadius(10);
        medianView.setCustomPadding(10);
        medianView.setHeaderColor(headerColor);
        medianView.setFooterColor(footerColor);

        footerLayout = new FooterView(getContext());
        footerLayout.setColor(footerColor);
        footerLayout.setLayoutParams(new LayoutParams(MarginLayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params, boolean preventRequestLayout) {
        return super.addViewInLayout(setChildren(child), index, params, preventRequestLayout);
    }

    @Override
    public void addView(View child) {
        super.addView(setChildren(child));
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(setChildren(child), index, params);
    }

    @Override
    public void addView(View child, int index) {
        super.addView(setChildren(child), index);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(setChildren(child), params);
    }

    @Override
    public void addView(View child, int width, int height) {
        super.addView(setChildren(child), width, height);
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params) {
        return super.addViewInLayout(setChildren(child), index, params);
    }

    /**
     * Any layout manager that doesn't scroll will want this.
     */
    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }


    /**
     * Ask all children to measure themselves and compute the measurement of this
     * layout based on the children.
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();

        /*
        These keep track of the space we are using on the left and right for
        views positioned there; we need member variables so we can also use
        these for layout later.
        */
        mLeftWidth = 0;
        mRightWidth = 0;

        /* Measurement will ultimately be computing these values. */
        int maxHeight = 0;
        int maxWidth = 0;
        int childState = 0;

        /*
        Iterate through all children, measuring them and computing our dimensions
        from their size.
        */
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                //Measure the child.
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);

                //Update our size information based on the layout params.  Children
                //that asked to be positioned on the left or right go in those gutters.
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
                int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;

                //maxWidth += childWidth;
                maxWidth = Math.max(maxWidth, childWidth);
                maxHeight += childHeight;
                //maxHeight = Math.max(maxHeight, childHeight);

                childState = combineMeasuredStates(childState, child.getMeasuredState());
            }
        }
        /* Check against our minimum height and width */
        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());

        /* Report our final dimensions. */
        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, childState),
                resolveSizeAndState(maxHeight, heightMeasureSpec,
                        childState << MEASURED_HEIGHT_STATE_SHIFT));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        addView(medianView, 1);
    }

    private View setChildren(View view) {
        if (view instanceof HeaderView || view instanceof MedianView || view instanceof FooterView)
            return view;
        switch (getChildCount()) {
            case 0:
                headerLayout.setId(R.id.headerLayout);
                headerLayout.setPadding(20, 20, 20, 20);
                headerLayout.addView(view);
                return headerLayout;
            case 1:
                footerLayout.setId(R.id.footerLayout);
                footerLayout.setPadding(20, 20, 20, 20);
                footerLayout.addView(view);
                return footerLayout;
        }
        return view;
    }

    /**
     * Position all children within this layout.
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int count = getChildCount();


        // These are the far left and right edges in which we are performing layout.
        int leftPos = getPaddingLeft();
        int rightPos = right - left - getPaddingRight();

        // These are the top and bottom edges in which we are performing layout.
        final int parentTop = getPaddingTop();
        final int parentBottom = bottom - top - getPaddingBottom();

        int currentHeight = 0;

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();

                final int width = child.getMeasuredWidth();
                final int height = child.getMeasuredHeight();


                mTmpContainerRect.left = leftPos + lp.leftMargin;
                mTmpContainerRect.right = rightPos - lp.rightMargin;

                mTmpContainerRect.top = parentTop + currentHeight + lp.topMargin;
                currentHeight = mTmpContainerRect.bottom = mTmpContainerRect.top + height + lp.bottomMargin;


                // mTmpContainerRect.top = parentTop + lp.topMargin;
                // mTmpContainerRect.bottom = parentBottom - lp.bottomMargin;
                //Use the child's gravity and size to determine its final
                //frame within its container.

                Gravity.apply(lp.gravity, width, height, mTmpContainerRect, mTmpChildRect);

                //Place the child.
                child.layout(mTmpChildRect.left, mTmpChildRect.top,
                        mTmpChildRect.right, mTmpChildRect.bottom);
            }
        }

    }



    /*
    ----------------------------------------------------------------------
    The rest of the implementation is for custom per-child layout parameters.
    If you do not need these (for example you are writing a layout manager
    that does fixed positioning of its children), you can drop all of this.
    */


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new CustomLayout.LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    /**
     * Custom per-child layout information.
     */
    public static class LayoutParams extends MarginLayoutParams {
        /**
         * The gravity to apply with the View to which these layout parameters
         * are associated.
         */
        public int gravity = Gravity.TOP | Gravity.START;


        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);

            /*
            Pull the layout param values from the layout XML during
            inflation.  This is not needed if you don't care about
            changing the layout behavior in XML.
            */
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.CustomLayoutLP);
            gravity = a.getInt(R.styleable.CustomLayoutLP_android_layout_gravity, gravity);
            a.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }
}