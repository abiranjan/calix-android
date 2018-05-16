package com.calix.calixgigaspireapp.ui.loginregconfig;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.calix.calixgigaspireapp.R;
import com.calix.calixgigaspireapp.adapter.DashboardAdapter;
import com.calix.calixgigaspireapp.main.BaseActivity;
import com.calix.calixgigaspireapp.output.model.CategoryEntity;
import com.calix.calixgigaspireapp.output.model.CommonResponse;
import com.calix.calixgigaspireapp.output.model.DashboardResponse;
import com.calix.calixgigaspireapp.services.APIRequestHandler;
import com.calix.calixgigaspireapp.utils.CircularLayout;
import com.calix.calixgigaspireapp.utils.DialogManager;
import com.calix.calixgigaspireapp.utils.InterfaceBtnCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Dashboard extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

//    @BindView(R.id.circular_layout)
//    CircularLayout circularLayout;

    @BindView(R.id.download)
    TextView download;

    @BindView(R.id.upload)
    TextView upload;

    @BindView(R.id.devices_viewpager)
    ViewPager pager;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*ButterKnife for variable initialization*/
        ButterKnife.bind(this);

//        circularLayout.setCapacity(24);
        dashboardAPICall();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
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

    /*API calls*/
    private void dashboardAPICall() {
        APIRequestHandler.getInstance().dashboardTypeAPICall(this);
    }

    /*API request success and failure*/
    @Override
    public void onRequestSuccess(Object resObj) {
        super.onRequestSuccess(resObj);
        if (resObj instanceof DashboardResponse) {
            DashboardResponse dashboardResponse = (DashboardResponse) resObj;
            upload.setText(dashboardResponse.getSpeed().getUpload() + "mbps");
            download.setText(dashboardResponse.getSpeed().getDownload() + "mbps");

            ArrayList<CategoryEntity> categories = dashboardResponse.getCategories();

            ArrayList<List<CategoryEntity>> splittedArray = new ArrayList<>();//This list will contain all the splitted arrays.
            int lengthToSplit = 8;

            for (int i = 0; i < categories.size(); i += 8) {
                int start = i;
                int end = 0;
                if (i+8 < categories.size()) {
                    end = (start + 8);
                    Log.d("start", String.valueOf(start));
                    Log.d("end", String.valueOf(end));
                }
                else {
                    end = categories.size();
                    Log.d("start1", String.valueOf(start));
                    Log.d("end1", String.valueOf(end));
                }
                splittedArray.add(categories.subList(start, end));
            }

            pager.setAdapter(new DashboardAdapter(this,splittedArray,dashboardResponse.getDeviceCount()));
            tabLayout.setupWithViewPager(pager, true);
//
//            circularLayout.setCapacity(categories.size());
//            for (int i = 0; i < categories.size(); i++) {
//                LayoutInflater factory = LayoutInflater.from(this);
//                View myView = factory.inflate(R.layout.honey_comb_view, null);
//
//                TextView deviceLabel = myView.findViewById(R.id.deviceLabel);
//                deviceLabel.setText(categories.get(i).getName());
//
//                TextView deviceCount = myView.findViewById(R.id.deviceCount);
//                deviceCount.setText(String.valueOf(categories.get(i).getCount()));
//
//
//                circularLayout.addView(myView);
//            }
//

        } else if (resObj instanceof CommonResponse) {
            DialogManager.getInstance().showAlertPopup(this, "Success", this);
        }
    }

    @Override
    public void onRequestFailure(final Object resObj, Throwable t) {
        super.onRequestFailure(resObj, t);
        if (t instanceof IOException) {

            if (resObj instanceof DashboardResponse) {
                DialogManager.getInstance().showAlertPopup(this,
                        (t instanceof java.net.ConnectException ? getString(R.string.no_internet) : getString(R.string
                                .connect_time_out)), new InterfaceBtnCallback() {
                            @Override
                            public void onPositiveClick() {

                            }
                        });
            }

        }
    }
}
