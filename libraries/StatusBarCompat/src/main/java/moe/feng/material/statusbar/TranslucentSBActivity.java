package moe.feng.material.statusbar;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

public abstract class TranslucentSBActivity extends AppCompatActivity {

    protected boolean needSetTranslucent = true, isCreated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createTranslucentStatusBar();

    }

    protected void createTranslucentStatusBar() {
        if (needSetTranslucent) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                setTranslucentStatus(true);
            }
        }
        isCreated = true;
    }

    protected void setStatusBarTintColor(int color) {
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(color);
    }


    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public void setStatusBarTranslucent(boolean should) throws ShouldSetBeforeActivityCreatedException {
        if (isCreated) {
            throw new ShouldSetBeforeActivityCreatedException();
        } else {
            this.needSetTranslucent = should;
        }
    }

    public class ShouldSetBeforeActivityCreatedException extends Exception {
    }

}
