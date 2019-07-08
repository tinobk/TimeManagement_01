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
import pomobox.ui.eishenhower_box.EishenhowerBoxFragment;
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
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.color_red_fire_brick));
        }
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle actionBarDrawerToggle =
                new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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
        setTitle(mOptionTitles[OPTION_SETTINGS]);
        Fragment fragment = mFragmentManager.findFragmentById(R.id.content_frame);
        if (!(fragment instanceof SettingsFragment))
            fragment = SettingsFragment.newInstance();
        mFragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack(null).commit();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = mFragmentManager.findFragmentById(R.id.content_frame);
        switch (id) {
            case R.id.nav_aishenhower:
                //Handle the aishenhower box action
                setTitle(mOptionTitles[OPTION_AISHENHOWER_BOX]);
                if (!(fragment instanceof EishenhowerBoxFragment))
                    fragment = EishenhowerBoxFragment.newInstance();
                break;
            case R.id.nav_pomodoro:
                setTitle(mOptionTitles[OPTION_POMODORO]);
                if (!(fragment instanceof PomodoroFragment))
                    fragment = PomodoroFragment.newInstance();
                break;
            case R.id.nav_mini_tasks:
                setTitle(mOptionTitles[OPTION_MINI_TASKS]);
                if (!(fragment instanceof MiniTaskFragment))
                    fragment = MiniTaskFragment.newInstance();
                break;
            case R.id.nav_settings:
                setTitle(mOptionTitles[OPTION_SETTINGS]);
                if (!(fragment instanceof SettingsFragment))
                    fragment = SettingsFragment.newInstance();
                break;
            case R.id.nav_help:
                setTitle(mOptionTitles[OPTION_HELPS]);
                if (!(fragment instanceof HelpsFragment))
                    fragment = HelpsFragment.newInstance();
                break;
            case R.id.nav_about:
                setTitle(mOptionTitles[OPTION_ABOUT]);
                if (!(fragment instanceof AboutFragment))
                    fragment = AboutFragment.newInstance();
                break;
            default:
                break;
        }
        if (fragment != null) {
            mFragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .addToBackStack(null).commit();
        }
        if (mDrawerLayout != null) mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
