package com.calix.calixgigaspireapp.ui.devices;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.calix.calixgigaspireapp.R;
import com.calix.calixgigaspireapp.main.BaseActivity;
import com.calix.calixgigaspireapp.output.model.DeviceListEntity;
import com.calix.calixgigaspireapp.output.model.DeviceListResponse;
import com.calix.calixgigaspireapp.services.APIRequestHandler;
import com.calix.calixgigaspireapp.ui.dashboard.Dashboard;
import com.calix.calixgigaspireapp.ui.router.Router;
import com.calix.calixgigaspireapp.utils.AppConstants;
import com.calix.calixgigaspireapp.utils.DialogManager;
import com.calix.calixgigaspireapp.utils.InterfaceBtnCallback;
import com.calix.calixgigaspireapp.utils.NumberUtil;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Devices extends BaseActivity {

    @BindView(R.id.header_txt)
    TextView mHeaderTxt;

    @BindView(R.id.devices_header_bg_lay)
    RelativeLayout mDevicesHeaderBgLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);
        initView();

    }

    /*View initialization*/
    private void initView() {
        /*For error track purpose - log with class name*/
        AppConstants.TAG = this.getClass().getSimpleName();

        /*ButterKnife for variable initialization*/
        ButterKnife.bind(this);

        setHeaderView();

        deviceListAPICall();


    }

    private void setHeaderView() {
        /*Header*/
        mHeaderTxt.setVisibility(View.VISIBLE);
        mHeaderTxt.setText(getString(R.string.devices));

        /*Set header adjustment - status bar we applied transparent color so header tack full view*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mDevicesHeaderBgLay.post(new Runnable() {
                public void run() {
                    int heightInt = getResources().getDimensionPixelSize(R.dimen.size45);
                    mDevicesHeaderBgLay.setLayoutParams(new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, heightInt + NumberUtil.getInstance().getStatusBarHeight(Devices.this)));
                    mDevicesHeaderBgLay.setPadding(0, NumberUtil.getInstance().getStatusBarHeight(Devices.this), 0, 0);
                }

            });
        }
    }

    /*View onClick*/
    @OnClick({R.id.header_left_img_lay, R.id.footer_dashboard_btn, R.id.footer_router_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_left_img_lay:
                onBackPressed();
                break;
            case R.id.footer_dashboard_btn:
                previousScreen(Dashboard.class);
                break;
            case R.id.footer_router_btn:
                nextScreen(Router.class);
                break;
        }
    }

    /*Device List API calls*/
    private void deviceListAPICall(){
        APIRequestHandler.getInstance().deviceListAPICall(this,"");
    }

    /*API request success and failure*/
    @Override
    public void onRequestSuccess(Object resObj) {
        super.onRequestSuccess(resObj);
        if (resObj instanceof DeviceListResponse) {
            DeviceListResponse deviceListResponse = (DeviceListResponse) resObj;
            setData(deviceListResponse.getDevices());
        }
    }

    private void setData(ArrayList<DeviceListEntity> deviceList) {
        Log.d("Device lis",deviceList.get(0).getIpAddress());
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
                            if (resObj instanceof DeviceListResponse)
                                deviceListAPICall();
                        }
                    });
        }
    }


    /*Default back button action*/
    @Override
    public void onBackPressed() {
        backScreen();
    }
}
