package com.calix.calixgigaspireapp.ui.loginregconfig;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.calix.calixgigaspireapp.R;
import com.calix.calixgigaspireapp.fragment.DashboardFragment;
import com.calix.calixgigaspireapp.fragment.DevicesFragment;
import com.calix.calixgigaspireapp.fragment.RouterMapFragment;
import com.calix.calixgigaspireapp.main.BaseActivity;
import com.calix.calixgigaspireapp.utils.DialogManager;
import com.calix.calixgigaspireapp.utils.InterfaceEdtBtnCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.calix.calixgigaspireapp.utils.PreferenceUtil.deleteValuesFromPreference;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, DashboardFragment.OnFragmentInteractionListener, DevicesFragment.OnFragmentInteractionListener, RouterMapFragment.OnFragmentInteractionListener {

    Toolbar toolbar;
    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Dashboard");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);

        replaceFragment(new DashboardFragment());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_devices:
                    toolbar.setTitle("Devices");
                    fragment = new DevicesFragment();
                    replaceFragment(fragment);
                    return true;
                case R.id.navigation_home:
                    toolbar.setTitle("Dashboard");
                    fragment = new DashboardFragment();
                    replaceFragment(fragment);
                    return true;
                case R.id.navigation_router:
                    toolbar.setTitle("Router Map");
                    fragment = new RouterMapFragment();
                    replaceFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void replaceFragment(Fragment fragment) {
        // remove and load new fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @OnClick(R.id.logoutBtn)
    public void logoutBtnClicked() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        DialogManager.getInstance().showLogoutPopup(this, new InterfaceEdtBtnCallback() {
            @Override
            public void onPositiveClick(String emailStr) {
                deleteValuesFromPreference(HomeActivity.this);
                nextScreen(Login.class);
            }

            @Override
            public void onNegativeClick() {

            }
        });
    }

    @Nullable
    public void setSelectedItemBottomNav(int resource){
        navigation.setSelectedItemId(resource);
    }
}
