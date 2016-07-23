package com.papi.player.ui;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.papi.player.AppContext;
import com.papi.player.AppStart;
import com.papi.player.R;
import com.papi.player.magicasakura.CardPickerDialog;
import com.papi.player.magicasakura.ThemeHelper;
import com.papi.player.net.NetworkUtils;
import com.papi.player.ui.common.AbsActivity;
import com.papi.player.ui.fragment.HomeFragment;
import com.papi.player.util.DoubleClickExitHelper;
import com.papi.player.util.SystemUtils;
import com.papi.player.util.Toastor;
import com.papi.player.view.NavigationView;

public class MainActivity extends AbsActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private Fragment[] fragments;

    private static final String TAG = MainActivity.class.getSimpleName();
    private NavigationView mNavigationView;
    private DoubleClickExitHelper doubleClickExitHelper;
    private FloatingActionButton mFloatActionBt;
    private TextView mUserName;
    private TextView mOtherInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragments = new Fragment[]{
                new HomeFragment()
        };

        setShowingFragment(fragments[0]);
    }

    @Override
    protected void setUpViews() {
        mDrawerLayout = $(R.id.drawer_layout);
        mNavigationView = (NavigationView) $(R.id.nav_view);
        mDrawerLayout.setDrawerListener(new DrawerListener());

        mNavigationView.setNavigationItemSelectedListener(this);
        mUserName = (TextView) mNavigationView.findViewById(R.id.user_name);
        mOtherInfo = (TextView) mNavigationView.findViewById(R.id.user_other_info);

//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setTitle(null);

        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayUseLogoEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(false);

        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                mToolbar,
                R.string.abc_action_bar_home_description,
                R.string.abc_action_bar_home_description
        );

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mFloatActionBt = (FloatingActionButton) $(R.id.fab);
        mFloatActionBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChannelActivity.launch(MainActivity.this);
            }
        });

        doubleClickExitHelper = new DoubleClickExitHelper(this);

        initData();

        if (NetworkUtils.isMobile(this)) {
            new Toastor(this).showToast(R.string.net_hint);
        }
    }

    private void initData() {
        mUserName.setText("" + SystemUtils.getModel());
        mOtherInfo.setText("" + SystemUtils.getVersionRelease());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mDrawerLayout.isDrawerOpen(mNavigationView)) {
                mDrawerLayout.closeDrawers();
                return true;
            }
            return doubleClickExitHelper.onKeyDown(keyCode, event);
        } else if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (mDrawerLayout.isDrawerOpen(mNavigationView)) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.nav_home:
                setShowingFragment(fragments[0]);
                item.setChecked(true);
                return true;
            case R.id.nav_theme:

                LocalVideoActivity.launch(MainActivity.this);
//              item.setChecked(true);
                return true;
            case R.id.nav_histories:
                HistoryActivity.launch(MainActivity.this);
//              item.setChecked(true);
                return true;
            case R.id.nav_settings:
                SettingActivity.launch(MainActivity.this);
//              item.setChecked(true);
                return true;
        }
        return false;
    }

    private CardPickerDialog.ClickListener mPickerListener = new CardPickerDialog.ClickListener() {
        @Override
        public void onConfirm(int currentTheme) {
            if (ThemeHelper.getTheme(MainActivity.this) != currentTheme) {
                ThemeHelper.setTheme(MainActivity.this, currentTheme);
                ThemeUtils.refreshUI(MainActivity.this, new ThemeUtils.ExtraRefreshable() {
                            @Override
                            public void refreshGlobal(Activity activity) {
                                //for global setting, just do once
                                if (Build.VERSION.SDK_INT >= 21) {
                                    final MainActivity context = MainActivity.this;
                                    ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(null, null, ThemeUtils.getThemeAttrColor(context, android.R.attr.colorPrimary));
                                    setTaskDescription(taskDescription);
                                    getWindow().setStatusBarColor(ThemeUtils.getColorById(context, R.color.theme_color_primary_dark));
                                }
                            }

                            @Override
                            public void refreshSpecificView(View view) {
                                //TODO: will do this for each traversal
                            }
                        }
                );
            }
        }
    };

    private void setShowingFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void onClick(View v) {
    }

    private class DrawerListener implements DrawerLayout.DrawerListener {

        @Override
        public void onDrawerOpened(View drawerView) {
            if (mDrawerToggle != null) {
                mDrawerToggle.onDrawerOpened(drawerView);
            }
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            if (mDrawerToggle != null) {
                mDrawerToggle.onDrawerClosed(drawerView);
            }
        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            if (mDrawerToggle != null) {
                mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
            }
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            if (mDrawerToggle != null) {
                mDrawerToggle.onDrawerStateChanged(newState);
            }
        }

    }

    private class ActionBarDrawerToggle extends android.support.v7.app.ActionBarDrawerToggle {

        public ActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar,
                                     int openDrawerContentDescRes, int closeDrawerContentDescRes) {
            super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
            invalidateOptionsMenu();
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            invalidateOptionsMenu();
        }

    }

}
