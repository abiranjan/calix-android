package com.calix.calixgigaspireapp.ui.devices;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.calix.calixgigaspireapp.R;
import com.calix.calixgigaspireapp.adapter.devices.DeviceSpinnerAdapter;
import com.calix.calixgigaspireapp.adapter.devices.DeviceTypeAdapter;
import com.calix.calixgigaspireapp.main.BaseActivity;
import com.calix.calixgigaspireapp.output.model.DeviceEntity;
import com.calix.calixgigaspireapp.output.model.DeviceFilterEntity;
import com.calix.calixgigaspireapp.output.model.DeviceFilterListResponse;
import com.calix.calixgigaspireapp.output.model.DeviceListEntity;
import com.calix.calixgigaspireapp.output.model.DeviceListResponse;
import com.calix.calixgigaspireapp.output.model.FilterDeviceListResponse;
import com.calix.calixgigaspireapp.services.APIRequestHandler;
import com.calix.calixgigaspireapp.ui.dashboard.Alert;
import com.calix.calixgigaspireapp.ui.dashboard.Dashboard;
import com.calix.calixgigaspireapp.utils.AppConstants;
import com.calix.calixgigaspireapp.utils.DialogManager;
import com.calix.calixgigaspireapp.utils.InterfaceBtnCallback;
import com.calix.calixgigaspireapp.utils.InterfaceEdtBtnCallback;
import com.calix.calixgigaspireapp.utils.NumberUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DevicesList extends BaseActivity {

    @BindView(R.id.header_txt)
    TextView mHeaderTxt;

    @BindView(R.id.devices_header_bg_lay)
    RelativeLayout mDevicesHeaderBgLay;

    @BindView(R.id.devices_list_recycler_view)
    RecyclerView mDevicesListRecyclerView;


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

    @BindView(R.id.header_right_img)
    ImageView mHeaderRightImg;

    @BindView(R.id.header_right_img_lay)
    RelativeLayout mHeaderRightImgLay;

    @BindView(R.id.device_spinner_card_view)
    CardView mDeviceSpinnerCardView;

    @BindView(R.id.device_spinner_recycler_view)
    RecyclerView mDeviceSpinnerRecyclerView;

    @BindView(R.id.arrow_img)
    ImageView mArrowImg;

    @BindView(R.id.spinner_filter_name_txt)
    TextView mSpinnerFilterNameTxt;

    @BindView(R.id.device_lay)
    RelativeLayout mDeviceLayout;

    @BindView(R.id.transaprent_bg)
    ImageView mTransaprentView;

    @BindView(R.id.device_spinner_lay)
    RelativeLayout mSpinnerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_devices_list);
        initView();

    }

    /*View initialization*/
    private void initView() {
        /*For error track purpose - log with class name*/
        AppConstants.TAG = this.getClass().getSimpleName();

        /*ButterKnife for variable initialization*/
        ButterKnife.bind(this);

        setHeaderView();
        setFooterVIew();

        deviceListAPICall();


    }

    private void setHeaderView() {
        /*Header*/
        mHeaderTxt.setVisibility(View.VISIBLE);
        mHeaderTxt.setText(getString(R.string.devices));
        mHeaderRightImgLay.setVisibility(View.VISIBLE);
        mHeaderRightImg.setImageResource(R.drawable.ic_filter);

        /*Set header adjustment - status bar we applied transparent color so header tack full view*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mDevicesHeaderBgLay.post(new Runnable() {
                public void run() {
                    int heightInt = getResources().getDimensionPixelSize(R.dimen.size45);
                    int dropDownHeightInt = heightInt + getResources().getDimensionPixelSize(R.dimen.size35) + NumberUtil.getInstance().getStatusBarHeight(DevicesList.this);

                    mDevicesHeaderBgLay.setLayoutParams(new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, heightInt + NumberUtil.getInstance().getStatusBarHeight(DevicesList.this)));
                    mDevicesHeaderBgLay.setPadding(0, NumberUtil.getInstance().getStatusBarHeight(DevicesList.this), 0, 0);

                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, dropDownHeightInt, 0, 0);
                    layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    mDeviceSpinnerCardView.setLayoutParams(layoutParams);
                }

            });
        }
    }

    /*Set Footer View */
    private void setFooterVIew() {
        if (AppConstants.ALERT_COUNT > 0) {
            mNotificationCount.setVisibility(View.VISIBLE);
            mNotificationCount.setText(String.valueOf(AppConstants.ALERT_COUNT));
        } else {
            mNotificationCount.setVisibility(View.GONE);
        }

        mFooterLeftIcon.setBackground(getResources().getDrawable(R.drawable.ic_dashboard));
        mFooterLeftTxt.setText(getString(R.string.dashboard));

        mFooterCenterIcon.setBackground(getResources().getDrawable(R.drawable.ic_notification));
        mFooterCenterTxt.setText(getString(R.string.alert));

        mFooterRightIcon.setBackground(getResources().getDrawable(R.drawable.ic_search));
        mFooterRightTxt.setText(getString(R.string.footer_search));
    }

    /*View onClick*/
    @OnClick({R.id.header_left_img_lay, R.id.footer_left_btn, R.id.footer_center_btn, R.id.footer_right_btn,
            R.id.device_spinner_lay, R.id.device_arrow_up_lay, R.id.arrow_img, R.id.device_lay, R.id.header_right_img})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_left_img_lay:
                onBackPressed();
                break;
            case R.id.footer_left_btn:
                previousScreen(Dashboard.class);
                break;
            case R.id.footer_center_btn:
                nextScreen(Alert.class);
                break;
            case R.id.footer_right_btn:
//                nextScreen(Router.class);
                break;
            case R.id.header_right_img:
                mSpinnerLayout.setVisibility(mSpinnerLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                break;

            case R.id.device_lay:
                mDeviceSpinnerCardView.setVisibility(View.GONE);
                mTransaprentView.setVisibility(View.GONE);
                mArrowImg.setRotation(0);
                break;
            case R.id.device_spinner_lay:
            case R.id.device_arrow_up_lay:
            case R.id.arrow_img:
                mTransaprentView.setVisibility(mDeviceSpinnerCardView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                mDeviceSpinnerCardView.setVisibility(mDeviceSpinnerCardView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                mArrowImg.setRotation(mDeviceSpinnerCardView.getVisibility() == View.VISIBLE ? 180 : 0);
                break;
            default:

        }
    }

    /*Device List API calls*/
    private void deviceListAPICall() {
        APIRequestHandler.getInstance().deviceListAPICall("", this);
    }

    /*API request success and failure*/
    @Override
    public void onRequestSuccess(Object resObj) {
        super.onRequestSuccess(resObj);
        if (resObj instanceof DeviceListResponse) {
            deviceFilterListAPI();
            DeviceListResponse deviceListResponse = (DeviceListResponse) resObj;
            setDeviceListDetails(deviceListResponse.getDevices());
        } else if (resObj instanceof FilterDeviceListResponse) {
            FilterDeviceListResponse deviceFilterListRes = (FilterDeviceListResponse) resObj;
            setDeviceFilterListData(deviceFilterListRes);
        } else if (resObj instanceof DeviceFilterListResponse) {
            DeviceFilterListResponse deviceFilterListResponse = (DeviceFilterListResponse) resObj;
            setDeviceFilterListAdapter(deviceFilterListResponse.getFilters());
        }
    }

    private void deviceFilterListAPI() {
        APIRequestHandler.getInstance().deviceFilterListAPICall(this);
    }


    /*Populate the values*/
    private void setDeviceListData(DeviceListResponse deviceListResponse) {
        setDeviceListDetails(deviceListResponse.getDevices());
    }

    private void setDeviceFilterListData(FilterDeviceListResponse deviceListResponse) {
        setDeviceListDetails(deviceListResponse.getDevices());
    }

    /*Set device list adapter*/
    private void setDeviceListDetails(ArrayList<DeviceEntity> deviceListResponse) {


        ArrayList<DeviceListEntity> positiveDeviceTypesList = new ArrayList<>();
        ArrayList<DeviceListEntity> otherDeviceTypesList = new ArrayList<>();
        TreeSet<Integer> positiveSortedDevices = new TreeSet<>();
        TreeSet<Integer> otherSortedDevices = new TreeSet<>();

        //Get list of unique device types
        for (DeviceEntity deviceList : deviceListResponse) {
            if (deviceList.getType() > 0)
                positiveSortedDevices.add(deviceList.getType());
            else
                otherSortedDevices.add(deviceList.getType());

        }
        int counter = 0;

        //Group devices with positive type numbers
        for (Integer deviceType : positiveSortedDevices) {
            DeviceListEntity devicesList = new DeviceListEntity();
            devicesList.setDeviceType(deviceType);

            ArrayList<DeviceEntity> filteredDevicesList = new ArrayList<>();
            for (DeviceEntity device : deviceListResponse) {
                if (device.getType() == deviceType) {
                    filteredDevicesList.add(device);
                }
            }
            devicesList.setDevicesList(filteredDevicesList);
            positiveDeviceTypesList.add(devicesList);
            ++counter;
        }

        //Group devices with negative or 0 type number
        for (Integer deviceType : otherSortedDevices) {
            DeviceListEntity devicesList = new DeviceListEntity();
            devicesList.setDeviceType(deviceType);

            ArrayList<DeviceEntity> filteredDevicesList = new ArrayList<>();
            for (DeviceEntity device : deviceListResponse) {
                if (device.getType() == deviceType) {
                    filteredDevicesList.add(device);
                }
            }
            devicesList.setDevicesList(filteredDevicesList);
            otherDeviceTypesList.add(devicesList);
            ++counter;
        }

        ArrayList<DeviceListEntity> deviceTypesList = new ArrayList<>();
        if (positiveDeviceTypesList.size() > 0)
            deviceTypesList.addAll(positiveDeviceTypesList);
        if (otherDeviceTypesList.size() > 0)
            deviceTypesList.addAll(otherDeviceTypesList);


        setData(deviceTypesList);

    }

    /*Set filter adapter*/
    private void setDeviceFilterListAdapter(ArrayList<DeviceFilterEntity> deviceFilterListResponse) {
        InterfaceEdtBtnCallback interfaceEdtBtnCallback;
        interfaceEdtBtnCallback = new InterfaceEdtBtnCallback() {
            @Override
            public void onPositiveClick(String editStr) {
                mSpinnerFilterNameTxt.setText(editStr);
                mTransaprentView.setVisibility(View.GONE);
            }

            @Override
            public void onNegativeClick() {

            }
        };

        /*Manually we add All value to adapter first pos*/
        DeviceFilterEntity deviceFilterEntity = new DeviceFilterEntity();
        deviceFilterEntity.setName(getString(R.string.all));

        ArrayList<DeviceFilterEntity> deviceFilterRes = new ArrayList<>();
        deviceFilterRes.add(deviceFilterEntity);
        deviceFilterRes.addAll(deviceFilterListResponse);

        mDeviceSpinnerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDeviceSpinnerRecyclerView.setAdapter(new DeviceSpinnerAdapter(deviceFilterRes, interfaceEdtBtnCallback, mDeviceSpinnerCardView, mArrowImg, this));


    }


    private void setData(ArrayList<DeviceListEntity> deviceList) {
        mDevicesListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDevicesListRecyclerView.setNestedScrollingEnabled(false);
        mDevicesListRecyclerView.setAdapter(new DeviceTypeAdapter(deviceList, this));
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
                            else deviceFilterListAPI();
                        }
                    });
        }
    }


    /*Default back button action*/
    @Override
    public void onBackPressed() {
        Log.d("backscreen", "- pressed");
        backScreen();
    }
}
