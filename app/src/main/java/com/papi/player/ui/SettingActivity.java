package com.papi.player.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.papi.player.AppContext;
import com.papi.player.R;
import com.papi.player.base.Constants;
import com.papi.player.base.Settings;
import com.papi.player.ui.common.AbsActivity;
import com.papi.player.util.Toastor;
import com.papi.player.view.dialog.CompatDialog;
import com.papi.player.view.foreground.ForegroundLinearLayout;
import com.papi.player.view.foreground.ForegroundRelativeLayout;

/**
 * Author   Shone
 * Date     29/06/16.
 * Github   https://github.com/shonegg
 */
public class SettingActivity extends AbsActivity implements View.OnClickListener {

    private ForegroundLinearLayout mItemChoice;
    private ForegroundLinearLayout mItemNextAction;
    private ForegroundRelativeLayout mItemNScreen;
    private SwitchCompat mItemSwitchCompat;
    private ForegroundLinearLayout mItemClear;
    private ForegroundLinearLayout mItemAbout;
    private TextView mQinXiDuTv;
    private TextView mPlayActionTv;

    public static void launch(AppCompatActivity activity) {
        Intent intent = new Intent(activity, SettingActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarTintColor(Constants.color_primary);
        setContentView(R.layout.activity_setting);
    }

    @Override
    protected void setUpViews() {
        mToolbar.setTitle("设置");
        setSupportActionBar(mToolbar);

        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayUseLogoEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mItemChoice = (ForegroundLinearLayout) $(R.id.setting_item_choice);
        mItemNextAction = (ForegroundLinearLayout) $(R.id.setting_item_nextaction);
        mItemNScreen = (ForegroundRelativeLayout) $(R.id.setting_item_screen);
        mItemSwitchCompat = (SwitchCompat) $(R.id.setting_switch);
        mItemClear = (ForegroundLinearLayout) $(R.id.setting_item_clear);
        mItemAbout = (ForegroundLinearLayout) $(R.id.setting_item_about);

        mItemNScreen.setOnClickListener(this);
        mItemChoice.setOnClickListener(this);
        mItemNextAction.setOnClickListener(this);
        mItemClear.setOnClickListener(this);
        mItemAbout.setOnClickListener(this);

        mQinXiDuTv = (TextView) $(R.id.setting_qinxidu);
        mPlayActionTv = (TextView) $(R.id.setting_playaction);

        updateQinxidu();
        updatePlayAction();
    }


    private void updateQinxidu() {
        String[] descs = getResources().getStringArray(R.array.setting_qinxidu_desc);
        int pos = new Settings(this).getQinXiDuSp();
        mQinXiDuTv.setText(descs[pos]);
    }


    private void updatePlayAction() {
        String[] descs = getResources().getStringArray(R.array.setting_play_action_desc);
        int pos = new Settings(this).getPlayActionSp();
        mPlayActionTv.setText(descs[pos]);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_item_screen: {
                mItemSwitchCompat.toggle();
                break;
            }
            case R.id.setting_item_choice: {
                String[] titles = getResources().getStringArray(R.array.setting_qinxidu);
                int pos = new Settings(SettingActivity.this).getQinXiDuSp();
                new CompatDialog.Builder(this, titles)
                        .setTitle("清晰度选择")
                        .setSelectedPosition(pos)
                        .setItemClickListener(new CompatDialog.OnClickListener() {
                            @Override
                            public void onClick(Dialog dialog, int which) {
                                new Settings(SettingActivity.this).setQinXiDuSp(which);
                                dialog.dismiss();
                                updateQinxidu();//刷新
                            }
                        })
                        .create().show();
                break;
            }
            case R.id.setting_item_nextaction: {
                String[] titles = getResources().getStringArray(R.array.setting_play_action);
                int pos = new Settings(SettingActivity.this).getPlayActionSp();
                new CompatDialog.Builder(this, titles)
                        .setTitle("播放完成后动作")
                        .setSelectedPosition(pos)
                        .setItemClickListener(new CompatDialog.OnClickListener() {
                            @Override
                            public void onClick(Dialog dialog, int which) {
                                new Settings(SettingActivity.this).setPlayActionSp(which);
                                dialog.dismiss();
                                updatePlayAction();//刷新
                            }
                        })
                        .create().show();
                break;
            }

            case R.id.setting_item_clear: {
                ImageLoader.getInstance().clearMemoryCache();
                ImageLoader.getInstance().clearDiskCache();
                AppContext.getMainHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                     new Toastor(SettingActivity.this).showToast("图片缓存清理完毕");
                    }
                }, 800);
                break;
            }

            case R.id.setting_item_about:{
                AboutActivity.launch(this);
                break;
            }
            default:
                break;
        }
    }



}
