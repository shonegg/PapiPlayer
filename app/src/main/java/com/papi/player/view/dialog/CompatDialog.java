package com.papi.player.view.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.papi.player.R;
import com.papi.player.ui.common.AbsRecyclerViewAdapter;

/**
 * Author   Shone
 * Date     30/06/16.
 * Github   https://github.com/shonegg
 */
public class CompatDialog extends Dialog {

    private Context context;

    public CompatDialog(Context context) {
        super(context);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = this.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        int marginBorder = Util.dip2px(context, 27);
        params.width = Util.getScreenWidth(context) - marginBorder * 2;
        window.setAttributes(params);
    }


    @SuppressLint({"NewApi", "InflateParams"})
    public static class Builder {

        private CompatDialog dialog;
        private Context context;
        private String[] titles;
        private CharSequence title;
        private int markPosition;
        private OnClickListener choiceListener;

        public Builder(Context context, String[] titles) {
            dialog = new CompatDialog(context);
            this.context = context;
            this.titles = titles;
        }


        public Context getContext() {
            return context;
        }

        public Builder setTitle(CharSequence title) {
            this.title = title;
            return this;
        }

        public Builder setTitle(@StringRes int titleResId) {
            this.title = context.getResources().getString(titleResId);
            return this;
        }

        public Builder setSelectedPosition(int position) {
            this.markPosition = position;
            return this;
        }

        public Builder setItemClickListener(OnClickListener listener){
            this.choiceListener = listener;
            return this;
        }

        @SuppressLint("InflateParams")
        public CompatDialog create() {
            if (dialog == null) {
                return null;
            }

            View mView = LayoutInflater.from(context).inflate(R.layout.dialog_list_view, null);
            TextView titleTv = (TextView) mView.findViewById(R.id.title);
            LinearLayout content = (LinearLayout) mView.findViewById(R.id.content);
            AppCompatTextView cancelBt = (AppCompatTextView) mView.findViewById(R.id.cancel);

            if (!TextUtils.isEmpty(title)) {
                titleTv.setText(title);
            }

            if (titles != null) {
                for (int i = 0; i < titles.length; i++) {
                    View v = LayoutInflater.from(getContext()).inflate(R.layout.item_dialog_list, null);
                    AppCompatRadioButton _radioBt = (AppCompatRadioButton) v.findViewById(R.id.checkbox);
                    AppCompatTextView _title = (AppCompatTextView) v.findViewById(R.id.title);
                    _title.setText(titles[i]);
                    if (this.markPosition == i) {
                        _radioBt.setChecked(true);
                    } else {
                        _radioBt.setChecked(false);
                    }
                    final int pos = i;
                    _radioBt.setOnClickListener(new View.OnClickListener(){

                        @Override
                        public void onClick(View v) {
                            if (choiceListener != null){
                                choiceListener.onClick(dialog, pos);
                            }
                        }
                    });
                    content.addView(v);
                }
            }

            cancelBt.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setContentView(mView);
            return dialog;
        }




        public CompatDialog show() {
            create().show();
            return dialog;
        }

        @Deprecated
        private class InnerRecyclerAdapter extends AbsRecyclerViewAdapter {
            private String[] titles;

            public InnerRecyclerAdapter(RecyclerView recyclerView, String[] titles) {
                super(recyclerView);
                this.titles = titles;
            }

            @Override
            public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                bindContext(parent.getContext());
                View v = LayoutInflater.from(getContext())
                        .inflate(R.layout.item_dialog_list, parent, false);
                return new CardHolder(v);
            }

            @Override
            public void onBindViewHolder(ClickableViewHolder cvh, int position) {
                super.onBindViewHolder(cvh, position);
                if (cvh instanceof CardHolder) {
                    CardHolder holder = (CardHolder) cvh;
                    holder.mTitleView.setText(titles[position]);
                    holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        }
                    });
                }
            }


            @Override
            public int getItemCount() {
                return titles == null ? 0 : titles.length;
            }

            public class CardHolder extends ClickableViewHolder {

                public AppCompatTextView mTitleView;
                public AppCompatCheckBox mCheckBox;

                public CardHolder(View itemView) {
                    super(itemView);
                    mTitleView = $(R.id.title);
                    mCheckBox = $(R.id.checkbox);
                }
            }
        }
    }

    public interface OnClickListener{
        void onClick(Dialog dialog, int which);
    }
}
