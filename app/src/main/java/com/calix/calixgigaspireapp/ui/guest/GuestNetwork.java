package com.calix.calixgigaspireapp.ui.guest;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.calix.calixgigaspireapp.R;
import com.calix.calixgigaspireapp.adapter.guest.GuestNetworkAdapter;
import com.calix.calixgigaspireapp.main.BaseActivity;
import com.calix.calixgigaspireapp.output.model.DurationEntity;
import com.calix.calixgigaspireapp.output.model.GuestWifiEntity;
import com.calix.calixgigaspireapp.output.model.GuestWifiResponse;
import com.calix.calixgigaspireapp.services.APIRequestHandler;
import com.calix.calixgigaspireapp.ui.dashboard.Alert;
import com.calix.calixgigaspireapp.ui.dashboard.Dashboard;
import com.calix.calixgigaspireapp.utils.AppConstants;
import com.calix.calixgigaspireapp.utils.DialogManager;
import com.calix.calixgigaspireapp.utils.InterfaceBtnCallback;
import com.calix.calixgigaspireapp.utils.NumberUtil;
import com.calix.calixgigaspireapp.utils.PreferenceUtil;


import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuestNetwork extends BaseActivity {


    @BindView(R.id.guest_network_par_lay)
    RelativeLayout mGuestNetworkLay;

    @BindView(R.id.header_txt)
    TextView mHeaderTxt;

    @BindView(R.id.guest_network_header_bg_lay)
    RelativeLayout mGuestNetworkHeaderBgLay;

    @BindView(R.id.guest_network_recycler_view)
    RecyclerView mGuestNetworkRecyclerView;

    @BindView(R.id.header_right_img_lay)
    RelativeLayout mHeaderRightImgLay;

    @BindView(R.id.header_right_img)
    ImageView mHeaderRightImg;

    /* Footer Variables */
    @BindView(R.id.footer_right_btn)
    ImageButton mFooterRightBtn;

    @BindView(R.id.notification_count_footer)
    TextView mNotificationCount;

    @BindView(R.id.footer_right_ic)
    ImageView mFooterRightIcon;

    @BindView(R.id.footer_right_txt)
    TextView mFooterRightTxt;

    @BindView(R.id.footer_center_btn)
    ImageButton mFooterCenterBtn;

    @BindView(R.id.footer_center_ic)
    ImageView mFooterCenterIcon;

    @BindView(R.id.footer_center_txt)
    TextView mFooterCenterTxt;

    @BindView(R.id.footer_left_btn)
    ImageButton mFooterLeftBtn;

    @BindView(R.id.footer_left_ic)
    ImageView mFooterLeftIcon;

    @BindView(R.id.footer_left_txt)
    TextView mFooterLeftTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_guest_network);
        initView();
    }

    /*View initialization*/
    private void initView() {
        /*For error track purpose - log with class name*/
        AppConstants.TAG = this.getClass().getSimpleName();

        /*ButterKnife for variable initialization*/
        ButterKnife.bind(this);

        /*Keypad to be hidden when a touch made outside the edit text*/
        setupUI(mGuestNetworkLay);

        setHeaderView();
        setFooterVIew();


        /*API call*/
        guestAPICall();

    }


    private void setHeaderView() {

        /*Header*/
        mHeaderTxt.setVisibility(View.VISIBLE);
        mHeaderTxt.setText(getString(R.string.guest_network));
        mHeaderRightImgLay.setVisibility(View.VISIBLE);
        mHeaderRightImg.setImageResource(R.drawable.ic_notification);

        if (AppConstants.ALERT_COUNT > 0) {
            mNotificationCount.setVisibility(View.VISIBLE);
            mNotificationCount.setText(String.valueOf(AppConstants.ALERT_COUNT));
        } else
            mNotificationCount.setVisibility(View.GONE);


        /*Set header adjustment - status bar we applied transparent color so header tack full view*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mGuestNetworkHeaderBgLay.post(new Runnable() {
                public void run() {
                    int heightInt = getResources().getDimensionPixelSize(R.dimen.size45);
                    mGuestNetworkHeaderBgLay.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, heightInt + NumberUtil.getInstance().getStatusBarHeight(GuestNetwork.this)));
                    mGuestNetworkHeaderBgLay.setPadding(0, NumberUtil.getInstance().getStatusBarHeight(GuestNetwork.this), 0, 0);
                }

            });
        }
    }

    /*Set Footer View */
    private void setFooterVIew(){

        mNotificationCount.setVisibility(View.GONE);

        mFooterLeftIcon.setBackground(getResources().getDrawable(R.drawable.ic_dashboard));
        mFooterLeftTxt.setText(getString(R.string.dashboard));

        mFooterCenterIcon.setBackground(getResources().getDrawable(R.drawable.ic_guest_home));
        mFooterCenterBtn.setBackground(getResources().getDrawable(R.drawable.footer_selection));
        mFooterCenterTxt.setText(getString(R.string.guest_home));

        mFooterRightIcon.setBackground(getResources().getDrawable(R.drawable.ic_guest_settings));
        mFooterRightTxt.setText(getString(R.string.guest_Settings));
    }


    /*View onClick*/
    @OnClick({R.id.header_left_img_lay, R.id.header_right_img_lay, R.id.add_guest_network_lay,
            R.id.footer_left_btn,R.id.footer_center_btn,R.id.footer_right_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_left_img_lay:
                onBackPressed();
                break;
            case R.id.header_right_img_lay:
                nextScreen(Alert.class);
                break;
            case R.id.footer_left_btn:
                previousScreen(Dashboard.class);
                break;
            case R.id.footer_center_btn:
//                nextScreen(Alert.class);
                break;
            case R.id.footer_right_btn:
//                nextScreen(Router.class);
                break;
            case R.id.add_guest_network_lay:
                GuestWifiEntity guestWifiEntity = new GuestWifiEntity();
                DurationEntity durationEntity = new DurationEntity();
                durationEntity.setStartTime(System.currentTimeMillis());
                durationEntity.setEndTime(System.currentTimeMillis());
                guestWifiEntity.setDuration(durationEntity);

                AppConstants.GUEST_WIFI_DETAILS = guestWifiEntity;
                nextScreen(SetupGuestNetwork.class);
                break;
        }
    }

    /*API calls*/
    private void guestAPICall() {
        sysOut(PreferenceUtil.getAuthorization(this));
        APIRequestHandler.getInstance().guestWifiListAPICall(this);
    }

    /*Set adapter*/
    private void setAdapter(ArrayList<GuestWifiEntity> guestWifiResponses) {
        mGuestNetworkRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mGuestNetworkRecyclerView.setAdapter(new GuestNetworkAdapter(guestWifiResponses, this));
        mGuestNetworkRecyclerView.setNestedScrollingEnabled(false);
    }

    /*API request success and failure*/
    @Override
    public void onRequestSuccess(Object resObj) {
        super.onRequestSuccess(resObj);
        if (resObj instanceof GuestWifiResponse) {
            GuestWifiResponse guestWifiResponse = (GuestWifiResponse) resObj;
            setAdapter(guestWifiResponse.getWifis());
        }
    }

    @Override
    public void onRequestFailure(Object resObj, Throwable t) {
        super.onRequestFailure(resObj, t);

        if (t instanceof IOException) {
            DialogManager.getInstance().showNetworkErrorPopup(this,
                    (t instanceof java.net.ConnectException ? getString(R.string.no_internet) : getString(R.string
                            .connect_time_out)), new InterfaceBtnCallback() {
                        @Override
                        public void onPositiveClick() {
                            guestAPICall();
                        }
                    });
        }
    }


    @Override
    public void onBackPressed() {
        previousScreen(Dashboard.class);
    }
}
