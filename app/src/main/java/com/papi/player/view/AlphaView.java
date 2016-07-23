package com.papi.player.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.papi.player.R;
import moe.feng.material.statusbar.util.ViewHelper;

/**
 * Author   Shone
 * Date     01/07/16.
 * Github   https://github.com/shonegg
 */
public class AlphaView extends View {

    private float mInitHeight;

    public AlphaView(Context context) {
        this(context, null);
    }

    public AlphaView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlphaView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray attr = getTypedArray(context, attrs, R.styleable.AlphaView);
        if (attr != null) {
            try {
                mInitHeight = attr.getDimension(R.styleable.AlphaView_alpha_height, 0);
            } finally {
                attr.recycle();
            }
        }
    }

    private TypedArray getTypedArray(Context context, AttributeSet attributeSet, int[] attr) {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
    }


    @Override
    public void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        adjustHeight();
    }

    @Override
    public void invalidate() {
        super.invalidate();
        adjustHeight();
    }

    public void adjustHeight() {
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = ViewHelper.getStatusBarHeight(getContext()) + (int) mInitHeight;
    }

}