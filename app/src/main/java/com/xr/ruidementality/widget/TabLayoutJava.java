package com.xr.ruidementality.widget;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by Sage on 2017/10/25.
 * Description:
 */

public class TabLayoutJava extends TabLayout {
    public TabLayoutJava(Context context) {
        super(context);
        initSome();
    }

    public TabLayoutJava(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSome();
    }

    public TabLayoutJava(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSome();
    }

    private void initSome() {
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                handleIndicator();
            }
        });
    }

    private float factor = 1.1f;

    private void handleIndicator() {

        Class<?> tabLayout =getClass().getSuperclass();
        Field tabStrip = null;
        try {
            System.out.println("========="+tabLayout.getSimpleName());
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return;
        }

        tabStrip.setAccessible(true);
        LinearLayout ll_tab = null;
        try {
            ll_tab = (LinearLayout) tabStrip.get((TabLayout)this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return;
        }
        int widthTab = getWidth() / 2;
        for (int i = 0; i < ll_tab.getChildCount(); i++) {
            View child = ll_tab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            try {
                TextView tv = (TextView) ((LinearLayout) child).getChildAt(1);
                int margin = (int) ((widthTab - tv.getWidth() * factor) / 2);
                System.out.println("java i==" + i + "==widthTab=" + widthTab + "==child w=" + tv.getWidth() + "==margin=" + margin);
                if (margin < 0) {
                    margin = 0;
                }
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, tv.getWidth());
                params.leftMargin = margin;
                params.rightMargin = margin;
                child.setLayoutParams(params);
                child.invalidate();
                continue;
            } catch (Exception e) {
                e.printStackTrace();
            }
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
//            params.leftMargin = left;
//            params.rightMargin = right;
//            child.setLayoutParams(params);
//            child.invalidate();
        }
    }
}
