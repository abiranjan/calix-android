package com.calix.calixgigaspireapp.ui.dashboard;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.calix.calixgigaspireapp.R;
import com.calix.calixgigaspireapp.adapter.DashboardAdapter;
import com.calix.calixgigaspireapp.main.BaseActivity;
import com.calix.calixgigaspireapp.output.model.CategoryEntity;
import com.calix.calixgigaspireapp.output.model.DashboardResponse;
import com.calix.calixgigaspireapp.services.APIRequestHandler;
import com.calix.calixgigaspireapp.ui.devices.Devices;
import com.calix.calixgigaspireapp.ui.loginregconfig.Login;
import com.calix.calixgigaspireapp.ui.router.Router;
import com.calix.calixgigaspireapp.utils.AppConstants;
import com.calix.calixgigaspireapp.utils.DialogManager;
import com.calix.calixgigaspireapp.utils.InterfaceBtnCallback;
import com.calix.calixgigaspireapp.utils.InterfaceTwoBtnCallback;
import com.calix.calixgigaspireapp.utils.NumberUtil;
import com.calix.calixgigaspireapp.utils.PreferenceUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Dashboard extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.header_left_img)
    ImageView mHeaderLeftImg;

    @BindView(R.id.header_txt)
    TextView mHeaderTxt;

    @BindView(R.id.dashboard_header_bg_lay)
    RelativeLayout mDashboardHeaderBgLay;

    @BindView(R.id.upload_speed_txt)
    TextView mUploadSpeedTxt;

    @BindView(R.id.download_speed_txt)
    TextView mDownloadSpeedTxt;

    @BindView(R.id.user_profile_img)
    ImageView mUserProfileImg;

    @BindView(R.id.name_txt)
    TextView mNameTxt;

    @BindView(R.id.devices_viewpager)
    ViewPager pager;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_dash_board);
        initView();
    }


    /*View initialization*/
    private void initView() {
        /*For error track purpose - log with class name*/
        AppConstants.TAG = this.getClass().getSimpleName();

        /*ButterKnife for variable initialization*/
        ButterKnife.bind(this);

        /*Keypad to be hidden when a touch made outside the edit text*/
        setupUI(mDrawerLayout);


        setHeaderView();

        dashboardAPICall();

    }


    /*Set header*/
    private void setHeaderView() {

        /*Header*/
        mHeaderTxt.setVisibility(View.VISIBLE);
        mHeaderLeftImg.setImageResource(R.drawable.menu_icon);
        mHeaderTxt.setText(getString(R.string.dashboard));

        /*Set header adjustment - status bar we applied transparent color so header tack full view*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mDashboardHeaderBgLay.post(new Runnable() {
                public void run() {
                    int heightInt = getResources().getDimensionPixelSize(R.dimen.size140);
                    mDashboardHeaderBgLay.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, heightInt + NumberUtil.getInstance().getStatusBarHeight(Dashboard.this)));
                    mDashboardHeaderBgLay.setPadding(0, NumberUtil.getInstance().getStatusBarHeight(Dashboard.this), 0, 0);
                    mDashboardHeaderBgLay.setBackground(getResources().getDrawable(R.drawable.header_bg));
                }

            });
        }

    }


    /*View onClick*/
    @OnClick({R.id.header_left_img_lay, R.id.dashboard_lay, R.id.iot_device_lay, R.id.device_list_lay,
            R.id.my_media_lay, R.id.speed_test_lay, R.id.network_usage_lay, R.id.parental_control_lay,
            R.id.guest_network_lay, R.id.settings_lay, R.id.alexa_lay, R.id.logout_lay, R.id.footer_devices_btn, R.id.footer_router_btn})
    public void onClick(final View v) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (v.getId()) {
                    case R.id.header_left_img_lay:
                        mDrawerLayout.openDrawer(GravityCompat.START);
                        break;
                    case R.id.dashboard_lay:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.iot_device_lay:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
//                        nextScreen(IOTDeviceList.class);
                        break;
                    case R.id.device_list_lay:
                    case R.id.my_media_lay:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
//                        nextScreenWithFinish(MyMedia.class);
                        break;
                    case R.id.speed_test_lay:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
//                        nextScreenWithFinish(SpeedTest.class);
                        break;
                    case R.id.network_usage_lay:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
//                        nextScreen(NetworkUsage.class);
                        break;
                    case R.id.parental_control_lay:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
//                        nextScreen(ParentalControl.class);
                        break;
                    case R.id.guest_network_lay:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
//                        nextScreen(GuestNetwork.class);
                        break;
                    case R.id.settings_lay:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
//                        nextScreen(Settings.class);
                        break;
                    case R.id.alexa_lay:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
//                        routerMapAPICall();
                        break;
                    case R.id.logout_lay:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        logoutFromApp();
                        break;
                    case R.id.footer_devices_btn:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        nextScreen(Devices.class);
                        break;
                    case R.id.footer_router_btn:
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        nextScreen(Router.class);
                        break;

                }
            }
        });

    }

    /*Populate the values*/
    private void setData(DashboardResponse dashboardResponse) {

        mNameTxt.setText(dashboardResponse.getUser().getFirstName()+" "+dashboardResponse.getUser().getLastName());
        mUploadSpeedTxt.setText(dashboardResponse.getSpeed().getUpload()+" "+getString(R.string.speed_unit));
        mDownloadSpeedTxt.setText(dashboardResponse.getSpeed().getDownload()+" "+getString(R.string.speed_unit));

        if (dashboardResponse.getUser().getAvatarURL().isEmpty()) {
            mUserProfileImg.setImageResource(R.drawable.default_profile_white);
        } else {
            try {
                Glide.with(this)
                        .load(dashboardResponse.getUser().getAvatarURL())
                        .apply(new RequestOptions().placeholder(R.drawable.default_profile_white).error(R.drawable.default_profile_white))
                        .into(mUserProfileImg);
            } catch (Exception ex) {
                mUserProfileImg.setImageResource(R.drawable.default_profile_white);
                Log.e(AppConstants.TAG, ex.getMessage());
            }
        }

        setAdapter(dashboardResponse.getCategories(),dashboardResponse.getDeviceCount());

    }

    /*Set adapter*/
    private void setAdapter(ArrayList<CategoryEntity> categories,final int deviceCount) {
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

        pager.setAdapter(new DashboardAdapter(this,splittedArray,deviceCount));
        tabLayout.setupWithViewPager(pager, true);
    }

    /*Dashboard API call*/
    private void dashboardAPICall() {
        APIRequestHandler.getInstance().dashboardTypeAPICall(this);
    }

    /*API request success and failure*/
    @Override
    public void onRequestSuccess(Object resObj) {
        super.onRequestSuccess(resObj);
        if (resObj instanceof DashboardResponse) {
            DashboardResponse dashboardResponse = (DashboardResponse) resObj;
            setData(dashboardResponse);
        }
    }

    @Override
    public void onRequestFailure(final Object resObj, Throwable t) {
        super.onRequestFailure(resObj, t);
        if (t instanceof IOException) {
            DialogManager.getInstance().showNetworkErrorPopup(this,
                    (t instanceof java.net.ConnectException ? getString(R.string.no_internet) : getString(R.string
                            .connect_time_out)), new InterfaceBtnCallback() {
                        @Override
                        public void onPositiveClick() {
                            if (resObj instanceof DashboardResponse)
                                dashboardAPICall();
                        }
                    });
        }
    }


    /*Default back button action*/
    @Override
    public void onBackPressed() {
        hideSoftKeyboard(Dashboard.this);
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            exitFromApp();
        }
    }

    /*App exit popup*/
    private void exitFromApp() {
        DialogManager.getInstance().showOptionPopup(this, getString(R.string.exit_msg), getString(R.string.yes), getString(R.string.no), new InterfaceTwoBtnCallback() {
            @Override
            public void onPositiveClick() {
                ActivityCompat.finishAffinity(Dashboard.this);
            }

            @Override
            public void onNegativeClick() {

            }
        });

    }

    /*App logout popup*/
    private void logoutFromApp() {
        DialogManager.getInstance().showLogoutPopup(this, new InterfaceTwoBtnCallback() {
            @Override
            public void onNegativeClick() {

            }

            @Override
            public void onPositiveClick() {
                PreferenceUtil.storeBoolPreferenceValue(Dashboard.this, AppConstants.LOGIN_STATUS, false);
                previousScreen(Login.class);
            }
        });

    }


}
