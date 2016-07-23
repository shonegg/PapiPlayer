package com.papi.player.view;

import android.content.Context;
import android.graphics.Canvas;
import android.view.Gravity;
import android.widget.TextView;

/**
 * author   Shone
 * date     15/06/16.
 * github   https://github.com/shonegg
 */
public class SimpleTextView extends TextView {
    public SimpleTextView(Context context) {
        super(context);
        setClickable(true);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setGravity(Gravity.CENTER);
    }
}
