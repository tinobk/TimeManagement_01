package pomobox.ui.main;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import pomobox.ui.mini_tasks.fragment.MiniTaskFragment;
import pomobox.ui.pomodoro.PomodoroFragment;
import pomobox.R;
import pomobox.ui.about.AboutFragment;
import pomobox.ui.aishenhower_box.AishenhowerBoxFragment;
import pomobox.utils.IOptions;
import pomobox.ui.helps.HelpsFragment;
import pomobox.ui.settings.SettingsFragment;

import static pomobox.utils.Constants.APP_BAR_DEFAULT_TITLE;
import static pomobox.utils.Constants.OPTION_ABOUT;
import static pomobox.utils.Constants.OPTION_AISHENHOWER_BOX;
import static pomobox.utils.Constants.OPTION_HELPS;
import static pomobox.utils.Constants.OPTION_MINI_TASKS;
import static pomobox.utils.Constants.OPTION_POMODORO;
import static pomobox.utils.Constants.OPTION_SETTINGS;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private FragmentManager mFragmentManager;
    private String[] mOptionTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.color_dark_red));
        }
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        mFragmentManager = getSupportFragmentManager();
        mOptionTitles = getResources().getStringArray(R.array.options_array);
        setTitle(mOptionTitles[APP_BAR_DEFAULT_TITLE]);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout != null) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
        setTitle(mOptionTitles[APP_BAR_DEFAULT_TITLE]);
    }

    //Create button setting on app bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Action when click on setting button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        selectItem(OPTION_SETTINGS);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_aishenhower:
                //Handle the aishenhower box action
                selectItem(OPTION_AISHENHOWER_BOX);
                break;
            case R.id.nav_pomodoro:
                selectItem(OPTION_POMODORO);
                break;
            case R.id.nav_mini_tasks:
                selectItem(OPTION_MINI_TASKS);
                break;
            case R.id.nav_settings:
                selectItem(OPTION_SETTINGS);
                break;
            case R.id.nav_help:
                selectItem(OPTION_HELPS);
                break;
            case R.id.nav_about:
                selectItem(OPTION_ABOUT);
                break;
            default:
                break;
        }
        if (mDrawerLayout != null) mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void selectItem(int position) {
        //update the no_items content by replacing fragments
        Fragment fragment = null;
        @IOptions int i = position;
        switch (i) {
            case OPTION_AISHENHOWER_BOX:
                fragment = AishenhowerBoxFragment.newInstance();
                break;

            case OPTION_POMODORO:
                fragment = PomodoroFragment.newInstance();
                break;

            case OPTION_MINI_TASKS:
                fragment = MiniTaskFragment.newInstance();
                break;

            case OPTION_SETTINGS:
                fragment = SettingsFragment.newInstance();
                break;

            case OPTION_HELPS:
                fragment = HelpsFragment.newInstance();
                break;

            case OPTION_ABOUT:
                fragment = AboutFragment.newInstance();
                break;

            default:
                break;
        }

        mFragmentManager.popBackStack();
        if (fragment != null) {
            mFragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .addToBackStack(null).commit();
        }
        setTitle(mOptionTitles[position]);
    }
}
