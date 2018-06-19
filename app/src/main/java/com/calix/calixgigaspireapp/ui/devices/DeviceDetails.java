package com.calix.calixgigaspireapp.ui.devices;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.calix.calixgigaspireapp.R;
import com.calix.calixgigaspireapp.adapter.devices.DeviceDetailsSpinnerAdapter;
import com.calix.calixgigaspireapp.main.BaseActivity;
import com.calix.calixgigaspireapp.output.model.ChartDetailsEntity;
import com.calix.calixgigaspireapp.output.model.ChartDetailsResponse;
import com.calix.calixgigaspireapp.output.model.ChartFilterEntity;
import com.calix.calixgigaspireapp.output.model.ChartFilterResponse;
import com.calix.calixgigaspireapp.output.model.CommonResponse;
import com.calix.calixgigaspireapp.output.model.DeviceEntity;
import com.calix.calixgigaspireapp.output.model.DeviceRenameResponse;
import com.calix.calixgigaspireapp.services.APIRequestHandler;
import com.calix.calixgigaspireapp.ui.dashboard.Alert;
import com.calix.calixgigaspireapp.ui.dashboard.Dashboard;
import com.calix.calixgigaspireapp.utils.AppConstants;
import com.calix.calixgigaspireapp.utils.DateUtil;
import com.calix.calixgigaspireapp.utils.DialogManager;
import com.calix.calixgigaspireapp.utils.ImageUtil;
import com.calix.calixgigaspireapp.utils.InterfaceBtnCallback;
import com.calix.calixgigaspireapp.utils.InterfaceEdtBtnCallback;
import com.calix.calixgigaspireapp.utils.NumberUtil;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;


public class DeviceDetails extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.graph_par_lay)
    RelativeLayout mGraphLay;

    @BindView(R.id.header_txt)
    TextView mHeaderTxt;

    @BindView(R.id.graph_header_bg_lay)
    RelativeLayout mGraphHeaderBgLay;

    @BindView(R.id.line_chart)
    LineChart mLineChart;

    @BindView(R.id.device_img)
    ImageView mDeviceImg;

    @BindView(R.id.device_name_txt)
    TextView mDeviceNameTxt;

    @BindView(R.id.device_name_sub_txt)
    TextView mDeviceNameSubTxt;

    @BindView(R.id.connection_status_txt)
    TextView mConnectionStatusTxt;

    @BindView(R.id.download_speed_txt)
    TextView mDownloadSpeedTxt;

    @BindView(R.id.upload_speed_txt)
    TextView mUploadSpeedTxt;

    @BindView(R.id.signal_strength_txt)
    TextView mSignalStrengthTxt;

    @BindView(R.id.connect_disconnect_img)
    ImageView mConnectDisconnectImg;

    @BindView(R.id.connect_disconnect_txt)
    TextView mConnectDisconnectTxt;

    @BindView(R.id.connect_disconnect_toggle)
    ToggleButton mToggleWifi;

    @BindView(R.id.ip_address_txt)
    TextView mIpAddressTxt;

    @BindView(R.id.band_txt)
    TextView mBandTxt;

    @BindView(R.id.channel_txt)
    TextView mChannelTxt;

    /* Footer Variables */
    @BindView(R.id.footer_right_btn)
    ImageButton mFooterRightBtn;

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

    @BindView(R.id.transaprent_bg)
    ImageView mTransaprentView;

    @BindView(R.id.device_spinner_card_view)
    CardView mDeviceSpinnerCardView;

    @BindView(R.id.device_spinner_recycler_view)
    RecyclerView mDeviceSpinnerRecyclerView;

    private ArrayList<String> mFilterTypStrArrList = new ArrayList<>();
    private float mGraphYAxisMaxFloat = 0;
    private String mFilterNameStr = "", mDeviceNameStr = "";
    private boolean mIsDeviceConnectBool = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_device_details);
        initView();
    }


    private void initView() {

        /*For error track purpose - log with class name*/
        AppConstants.TAG = this.getClass().getSimpleName();

        /*ButterKnife for variable initialization*/
        ButterKnife.bind(this);

        /*Keypad to be hidden when a touch made outside the edit text*/
        setupUI(mGraphLay);

        setHeaderView();
        setFooterVIew();

        setData();

    }

    private void setHeaderView() {
        /*Header*/
        mHeaderTxt.setVisibility(View.VISIBLE);

        /*Set header adjustment - status bar we applied transparent color so header tack full view*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mGraphHeaderBgLay.post(new Runnable() {
                public void run() {
                    int heightInt = getResources().getDimensionPixelSize(R.dimen.size170);
                    mGraphHeaderBgLay.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, heightInt + NumberUtil.getInstance().getStatusBarHeight(DeviceDetails.this)));
                    mGraphHeaderBgLay.setPadding(0, NumberUtil.getInstance().getStatusBarHeight(DeviceDetails.this), 0, 0);
                    mGraphHeaderBgLay.setBackground(getResources().getDrawable(R.drawable.header_bg));
                }

            });
        }
    }

    /*Set Footer View */
    private void setFooterVIew() {
        mFooterLeftIcon.setBackground(getResources().getDrawable(R.drawable.ic_dashboard));
        mFooterLeftTxt.setText(getString(R.string.dashboard));

        mFooterCenterIcon.setBackground(getResources().getDrawable(R.drawable.ic_notification));
        mFooterCenterTxt.setText(getString(R.string.alert));

        mFooterRightIcon.setBackground(getResources().getDrawable(R.drawable.ic_filter));
        mFooterRightTxt.setText(getString(R.string.footer_filter));
    }


    @OnClick({R.id.header_left_img_lay, R.id.device_edit_img, R.id.footer_left_btn,R.id.footer_center_btn, R.id.footer_right_btn})
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
                mTransaprentView.setVisibility(mDeviceSpinnerCardView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                mDeviceSpinnerCardView.setVisibility(mDeviceSpinnerCardView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                break;
            case R.id.device_edit_img:

                DialogManager.getInstance().showEdtDeviceNamePopup(this,
                        getString(R.string.device_edit_pheader),
                        getString(R.string.device_edit_sheader),
                        getString(R.string.device_edit_hint),
                        mDeviceNameTxt.getText().toString().trim(), new InterfaceEdtBtnCallback() {
                    @Override
                    public void onNegativeClick() {

                    }

                    @Override
                    public void onPositiveClick(String edtStr) {
                        mDeviceNameStr = edtStr;
                        edtDeviceNameAPI(mDeviceNameStr);
                    }
                });
                break;
        }
    }

    private void setData() {
        DeviceEntity deviceDetailsRes = AppConstants.DEVICE_DETAILS_ENTITY;
        boolean isDeviceConnectedBool = deviceDetailsRes.isConnected2network();

        mHeaderTxt.setText(deviceDetailsRes.getName());

        mDeviceImg.setImageResource(ImageUtil.getInstance().deviceImg(deviceDetailsRes.getType()));
        mDeviceNameTxt.setText(deviceDetailsRes.getName());
        mDeviceNameSubTxt.setText(deviceDetailsRes.getSubType() == 1 ? getString(R.string.type_android) : getString(R.string.type_ios));

        mConnectionStatusTxt.setText(String.format(getString(isDeviceConnectedBool ? R.string.connect_device : R.string.disconnect_device), AppConstants.DEVICE_DETAILS_ENTITY.getRouter().getName()));

        mSignalStrengthTxt.setText(deviceDetailsRes.getSignalStrength() + " " + getString(R.string.percentage_sys));

        mConnectDisconnectImg.setImageResource(ImageUtil.getInstance().connectedStatusViaRouterImg(!isDeviceConnectedBool, deviceDetailsRes.getIfType()));
        mConnectDisconnectTxt.setText(String.format(getString(isDeviceConnectedBool ? R.string.connect_device_list : R.string.disconnect_device_list), AppConstants.DEVICE_DETAILS_ENTITY.getRouter().getName()));
        mToggleWifi.setChecked(isDeviceConnectedBool);
        setToggleView(isDeviceConnectedBool);
        mDownloadSpeedTxt.setText(deviceDetailsRes.getSpeed().getDownload());
        mUploadSpeedTxt.setText(deviceDetailsRes.getSpeed().getUpload());

        mIpAddressTxt.setText(deviceDetailsRes.getIpAddress());
        mBandTxt.setText(String.format(getString(R.string.band_unit), deviceDetailsRes.getBand()));
        mChannelTxt.setText(String.valueOf(deviceDetailsRes.getChannel()));


        graphFilterAPI();
    }

    /*View onCheckedChanged*/
    @OnCheckedChanged({R.id.connect_disconnect_toggle})
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.connect_disconnect_toggle:
                mIsDeviceConnectBool = isChecked;
                setToggleView(mIsDeviceConnectBool);

                connectAndDisConnectDeviceAPI(mIsDeviceConnectBool);

                mConnectionStatusTxt.setText(String.format(getString(isChecked ? R.string.connect_device : R.string.disconnect_device), AppConstants.DEVICE_DETAILS_ENTITY.getRouter().getName()));
                mConnectDisconnectImg.setImageResource(ImageUtil.getInstance().connectedStatusViaRouterImg(!isChecked, AppConstants.DEVICE_DETAILS_ENTITY.getIfType()));
                mConnectDisconnectTxt.setText(String.format(getString(isChecked ? R.string.connect_device_list : R.string.disconnect_device_list), AppConstants.DEVICE_DETAILS_ENTITY.getRouter().getName()));

                break;
        }
    }

    private void setToggleView(boolean isChecked) {
        if (isChecked) {
            // The toggle is enabled
            mToggleWifi.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);// Set left padding
            int padding = getResources().getDimensionPixelSize(R.dimen.size2);
            int paddingBottom = getResources().getDimensionPixelSize(R.dimen.size3);
            int paddingLeft = getResources().getDimensionPixelSize(R.dimen.size5);
            mToggleWifi.setPadding(paddingLeft, padding, padding, paddingBottom);
        } else {
            // The toggle is disabled
            mToggleWifi.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);// Set left padding
            int padding = getResources().getDimensionPixelSize(R.dimen.size2);
            int paddingTop = getResources().getDimensionPixelSize(R.dimen.size3);
            int paddingRight = getResources().getDimensionPixelSize(R.dimen.size3);
            mToggleWifi.setPadding(padding, paddingTop, paddingRight, padding);
        }
    }

    private void connectAndDisConnectDeviceAPI(boolean isChecked) {
        if (isChecked)
            APIRequestHandler.getInstance().deviceConnectAPICall(AppConstants.DEVICE_DETAILS_ENTITY.getDeviceId(), this);
        else
            APIRequestHandler.getInstance().deviceDisconnectAPICall(AppConstants.DEVICE_DETAILS_ENTITY.getDeviceId(), this);

    }


    private void graphAPI(String filterNameStr) {
        mFilterNameStr = filterNameStr;
        APIRequestHandler.getInstance().deviceChartDetailsAPICall(AppConstants.DEVICE_DETAILS_ENTITY.getDeviceId(), filterNameStr, this);
    }

    private void graphFilterAPI() {
        APIRequestHandler.getInstance().deviceChartFilterAPICall(this);
    }

    private void edtDeviceNameAPI(String deviceNameStr) {
        APIRequestHandler.getInstance().deviceRenameAPICalll(AppConstants.DEVICE_DETAILS_ENTITY.getDeviceId(), deviceNameStr, "1234", DeviceDetails.this);
        mDeviceNameTxt.setText(deviceNameStr);
        mHeaderTxt.setText(deviceNameStr);
    }


    @Override
    public void onRequestSuccess(Object resObj) {
        super.onRequestSuccess(resObj);
        if (resObj instanceof ChartFilterResponse) {
            ChartFilterResponse chartFilterResponse = (ChartFilterResponse) resObj;
            if (chartFilterResponse.getFilters().size() > 0) {
                ArrayList<ChartFilterEntity> encryptionTypeEntity = chartFilterResponse.getFilters();
                mFilterTypStrArrList = new ArrayList<>();

                for (int i = 0; i < encryptionTypeEntity.size(); i++) {
                    mFilterTypStrArrList.add(encryptionTypeEntity.get(i).getType());
                }
                setDeviceFilterListAdapter();

            }
        } else if (resObj instanceof ChartDetailsResponse) {
            ChartDetailsResponse chartDetailsResponse = (ChartDetailsResponse) resObj;
            // TODO : Uncomment this
            GraphSection(chartDetailsResponse.getType(), chartDetailsResponse.getData());
        }
    }

    @Override
    public void onRequestFailure(final Object inputModelObj, Throwable t) {
        super.onRequestFailure(inputModelObj, t);
        if (t instanceof IOException) {
            DialogManager.getInstance().showNetworkErrorPopup(this,
                    (t instanceof java.net.ConnectException ? getString(R.string.no_internet) : getString(R.string
                            .connect_time_out)), new InterfaceBtnCallback() {
                        @Override
                        public void onPositiveClick() {
                            if (inputModelObj instanceof ChartFilterResponse) {
                                graphFilterAPI();
                            } else if (inputModelObj instanceof ChartDetailsResponse) {
                                graphAPI(mFilterNameStr);
                            } else if (inputModelObj instanceof DeviceRenameResponse) {
                                edtDeviceNameAPI(mDeviceNameStr);
                            } else if (inputModelObj instanceof CommonResponse) {
                                connectAndDisConnectDeviceAPI(mIsDeviceConnectBool);
                            }
                        }
                    });
        }
    }

    /*Set filter adapter*/
    private void setDeviceFilterListAdapter() {
        InterfaceEdtBtnCallback interfaceEdtBtnCallback;
        interfaceEdtBtnCallback = new InterfaceEdtBtnCallback() {
            @Override
            public void onPositiveClick(String editStr) {
                mTransaprentView.setVisibility(View.GONE);
            }

            @Override
            public void onNegativeClick() {

            }
        };

        mDeviceSpinnerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDeviceSpinnerRecyclerView.setAdapter(new DeviceDetailsSpinnerAdapter(mFilterTypStrArrList, interfaceEdtBtnCallback, mDeviceSpinnerCardView, this));
    }

    private void GraphSection(String filterTypeStr, ArrayList<ChartDetailsEntity> chartDetailsRes) {

        mLineChart.setDrawGridBackground(false);

        setGraphDetails(filterTypeStr, chartDetailsRes);

        // get the legend (only possible after setting data)
        Legend l = mLineChart.getLegend();

        mLineChart.getLegend().setEnabled(false);

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);

        // no description text
        mLineChart.setDescription("");
        mLineChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable touch gestures
        mLineChart.setTouchEnabled(true);

        // enable scaling and dragging
        mLineChart.setDragEnabled(true);
        mLineChart.setScaleEnabled(true);
        mLineChart.setScaleXEnabled(true);
        mLineChart.setScaleYEnabled(true);

        XAxis bottomAxis = mLineChart.getXAxis();
        bottomAxis.setTextColor(Color.WHITE);
        bottomAxis.setTextSize(5);
        bottomAxis.setAxisMinValue(0);
        bottomAxis.setLabelsToSkip(0);

        YAxis leftAxis = mLineChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines

        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setTextSize(5);
        leftAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        mLineChart.getAxisRight().setEnabled(false);

        mLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        mLineChart.getAxisLeft().setDrawGridLines(false);
        mLineChart.getXAxis().setDrawGridLines(false);


        mLineChart.animateX(700, Easing.EasingOption.EaseInOutQuart);

        //  dont forget to refresh the drawing
        mLineChart.invalidate();

    }


    private void setGraphDetails(String filterTypeStr, ArrayList<ChartDetailsEntity> chartDetailsRes) {

        ArrayList<String> xAxisDateArrList = new ArrayList<>();
        ArrayList<Entry> downloadEntryArrList = new ArrayList<>();
        ArrayList<Entry> uploadEntryArrList = new ArrayList<>();
        LineDataSet downloadLineDataSet, uploadLineDataSet;

        for (int chatDataIntPos = 0; chatDataIntPos < chartDetailsRes.size(); chatDataIntPos++) {
            float downloadFloat = (float) chartDetailsRes.get(chatDataIntPos).getDownload();
            float uploadFloat = (float) chartDetailsRes.get(chatDataIntPos).getUpload();

            if (mGraphYAxisMaxFloat == 0) {
                mGraphYAxisMaxFloat = downloadFloat;
            } else if (downloadFloat > mGraphYAxisMaxFloat) {
                mGraphYAxisMaxFloat = downloadFloat;
            }
            if (uploadFloat > mGraphYAxisMaxFloat) {
                mGraphYAxisMaxFloat = uploadFloat;
            }

            xAxisDateArrList.add(DateUtil.getTimeDifference(filterTypeStr, chartDetailsRes.get(chatDataIntPos).getTime()));
            downloadEntryArrList.add(new Entry(downloadFloat, chatDataIntPos));
            uploadEntryArrList.add(new Entry(uploadFloat, chatDataIntPos));
        }

        downloadLineDataSet = new LineDataSet(downloadEntryArrList, getString(R.string.download));

        downloadLineDataSet.setFillAlpha(110);
        downloadLineDataSet.setColor(getResources().getColor(R.color.graph_downloaded_color));
        downloadLineDataSet.setCircleColor(Color.WHITE);
//        downloadLineDataSet.setValueTextColor(Color.WHITE);
        downloadLineDataSet.setLineWidth(1f);
        downloadLineDataSet.setCircleRadius(2f);
        downloadLineDataSet.setDrawCircleHole(false);
        downloadLineDataSet.setValueTextSize(0f);
        downloadLineDataSet.setDrawFilled(false);
//        downloadLineDataSet.setDrawCubic(true);


        uploadLineDataSet = new LineDataSet(uploadEntryArrList, getString(R.string.upload));
        uploadLineDataSet.setColor(getResources().getColor(R.color.graph_uploaded_color));
        uploadLineDataSet.setCircleColor(Color.WHITE);
        uploadLineDataSet.setValueTextColor(Color.WHITE);
        uploadLineDataSet.setLineWidth(1f);
        uploadLineDataSet.setCircleRadius(3f);
        uploadLineDataSet.setDrawCircleHole(false);
        uploadLineDataSet.setValueTextSize(0f);
        uploadLineDataSet.setDrawFilled(false);
        uploadLineDataSet.setDrawCubic(true);


        ArrayList<ILineDataSet> LineDataSet = new ArrayList<>();
        LineDataSet.add(downloadLineDataSet);
        LineDataSet.add(uploadLineDataSet);


        // create a data object with the datasets
        LineData data = new LineData(xAxisDateArrList, LineDataSet);
        // set data
        mLineChart.setData(data);
    }


    @Override
    public void onBackPressed() {
        backScreen();
    }

}





