package com.papi.player;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.papi.player.ui.MainActivity;
import com.papi.player.util.UiHelper;

public class AppStart extends Activity {

    private ImageView iv_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        redirectTo();
    }


    /**
     * 跳转到...
     */
    private void redirectTo() {
        AppContext.getMainHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                UiHelper.intentTo(AppStart.this, MainActivity.class);
                finish();
            }
        }, 1200);
    }
}
