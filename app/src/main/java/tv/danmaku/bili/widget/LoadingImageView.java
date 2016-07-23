package tv.danmaku.bili.widget;

import android.content.Context;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.papi.player.R;

public class LoadingImageView
        extends RelativeLayout {
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private TextView mTextView;

    public LoadingImageView(Context context) {
        super(context);
        init(context);
    }

    public LoadingImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public static LoadingImageView a(FrameLayout paramFrameLayout) {
        LoadingImageView localLoadingImageView = new LoadingImageView(paramFrameLayout.getContext());
        FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-2, -2);
        localLayoutParams.gravity = 1;
        localLayoutParams.topMargin = ((int) TypedValue.applyDimension(1, 30.0F, paramFrameLayout.getResources().getDisplayMetrics()));
        localLoadingImageView.setLayoutParams(localLayoutParams);
        paramFrameLayout.addView(localLoadingImageView);
        return localLoadingImageView;
    }

    public TextView getTextView() {
        return this.mTextView;
    }

    public void showProgress() {
        this.mImageView.setVisibility(View.GONE);
        this.mProgressBar.setVisibility(View.VISIBLE);
        this.mTextView.setVisibility(View.GONE);
    }

    public void show(@StringRes int paramInt) {
        this.mTextView.setText(paramInt);
        this.mTextView.setVisibility(View.VISIBLE);
    }

    void init(Context paramContext) {
        LayoutInflater.from(paramContext).inflate(R.layout.bili_app_layout_loading_view2, this);
        this.mImageView = ((ImageView) findViewById(R.id.image));
        this.mProgressBar = ((ProgressBar) findViewById(R.id.progress_bar));
        this.mTextView = ((TextView) findViewById(R.id.text));
    }

    public void hideAll() {
        this.mImageView.setVisibility(View.GONE);
        this.mProgressBar.setVisibility(View.GONE);
        this.mTextView.setVisibility(View.GONE);
    }

    public void showError() {
        hideAll();
        this.mImageView.setImageResource(R.drawable.img_tips_error_load_error);
        this.mImageView.setVisibility(View.VISIBLE);
        show(R.string.tips_load_error);
    }

    public void d() {
        hideAll();
        this.mImageView.setVisibility(View.GONE);
        this.mTextView.setVisibility(View.GONE);
    }

    public void showText() {
        this.mTextView.setVisibility(View.VISIBLE);
    }

    public void hideText() {
        this.mTextView.setVisibility(View.GONE);
    }

    public void setImageResource(int paramInt) {
        this.mImageView.setImageResource(paramInt);
        this.mImageView.setVisibility(View.VISIBLE);
    }
}
