package com.papi.player.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.papi.player.R;
import com.papi.player.base.Constants;
import com.papi.player.ui.common.AbsActivity;

/**
 * Author   Shone
 * Date     30/06/16.
 * Github   https://github.com/shonegg
 */
public class AboutActivity extends AbsActivity {

    private TextView mVersionTv;

    public static void launch(AppCompatActivity activity) {
        Intent intent = new Intent(activity, AboutActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarTintColor(Constants.color_primary);
        setContentView(R.layout.activity_about);
    }

    @Override
    protected void setUpViews() {
        /***toolbar setting***/
        mToolbar.setTitle("关于");
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
        /*********************/

        mVersionTv = (TextView) $(R.id.version);
        mVersionTv.setText("版本:" + getAppVersionName(this));
    }

    public String getAppVersionName(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
