package com.example.sildemune.slidemunu;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.sildemune.R;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by guozhk on 16-5-3.
 */
public class MySlidemenu extends HorizontalScrollView {

    private static final int DEFAULT_MENU_RIGHT_PADDING = 5;
    private int mScreenWidth;
    private int mMenuRightPadding = DEFAULT_MENU_RIGHT_PADDING;
    /**
     * 菜单的宽度
     */
    private int mMenuWidth;
    //滑动显示菜单的临界值
    private int mScrollWidth = 50;

    //第一次加载布局
    private boolean once = false;
    private ViewGroup mMenu;
    private ViewGroup mContent;

    public MySlidemenu(Context context) {
        this(context, null);
    }

    public MySlidemenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySlidemenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getmScreenWidth();
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.SlidingMenu, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.SlidingMenu_rightPadding:
                    // 默认50
                    mMenuRightPadding = a.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_DIP, DEFAULT_MENU_RIGHT_PADDING,
                                    getResources().getDisplayMetrics()));// 默认为10DP
                    break;
            }
        }
        a.recycle();


    }


    private int getmScreenWidth() {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        mScreenWidth = wm.getDefaultDisplay().getWidth();
        return mScreenWidth;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!once) {
            LinearLayout wrapper = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) wrapper.getChildAt(0);
            mContent = (ViewGroup) wrapper.getChildAt(1);
            mMenuWidth = mScreenWidth - mMenuRightPadding;
            mScrollWidth = mMenuWidth / 2;
            mMenu.getLayoutParams().width = mMenuWidth;
            mContent.getLayoutParams().width = mScreenWidth;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            smoothScrollTo(mMenuWidth, 0);
            once = true;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                if (scrollX > mScrollWidth)
                {
                    smoothScrollTo(0, 0);
                    isOpen = true;
                } else
                {

                    smoothScrollTo(mMenuWidth, 0);
                    isOpen = false;

                }
                return true;
            default:
            break;
        }
        return super.onTouchEvent(ev);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float scale = l * 1.0f / mMenuWidth;
        float leftScale = 1 - 0.3f * scale;
        float rightScale = 0.8f + scale * 0.2f;

      //  ViewHelper.setScaleX(mMenu, leftScale);
       // ViewHelper.setScaleY(mMenu, leftScale);
        ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
        ViewHelper.setTranslationX(mMenu, mMenuWidth * scale * 0.6f);
/*
        ViewHelper.setPivotX(mContent, 0);
        ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);
        ViewHelper.setScaleX(mContent, rightScale);
        ViewHelper.setScaleY(mContent, rightScale);*/
    }

    boolean isOpen = false;

    public void openMenu() {
        if (isOpen) {
            return;
        }

        smoothScrollTo(0, 0);
        isOpen = true;
    }

    public void closeMenu() {
        if (isOpen) {
            smoothScrollTo(mMenuWidth, 0);
            isOpen = false;
        }
    }

    public void toggle() {
        if (isOpen) {
            closeMenu();
        } else {
            openMenu();
        }
    }
}
