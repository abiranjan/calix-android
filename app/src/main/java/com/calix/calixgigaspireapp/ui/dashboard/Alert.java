package com.calix.calixgigaspireapp.ui.dashboard;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.calix.calixgigaspireapp.R;
import com.calix.calixgigaspireapp.adapter.dashboard.AlertAdapter;
import com.calix.calixgigaspireapp.main.BaseActivity;
import com.calix.calixgigaspireapp.output.model.AlertResponse;
import com.calix.calixgigaspireapp.output.model.DatasEntity;
import com.calix.calixgigaspireapp.services.APIRequestHandler;
import com.calix.calixgigaspireapp.utils.DialogManager;
import com.calix.calixgigaspireapp.utils.InterfaceBtnCallback;
import com.calix.calixgigaspireapp.utils.NumberUtil;
import com.calix.calixgigaspireapp.utils.PreferenceUtil;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Alert extends BaseActivity {

    @BindView(R.id.alert_parent_lay)
    RelativeLayout mAlertViewGroup;

    @BindView(R.id.alert_header_bg_lay)
    RelativeLayout mAlertHeaderBgLay;

    @BindView(R.id.header_txt)
    TextView mHeaderTxt;

    @BindView(R.id.header_right_img)
    ImageView mHeaderRightImg;

    @BindView(R.id.header_right_img_lay)
    RelativeLayout mHeaderRightImgLay;

    @BindView(R.id.alert_recycler_view)
    RecyclerView mAlertRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_alert);
        initView();
    }


    /*View initialization*/
    private void initView() {
        /*ButterKnife for variable initialization*/
        ButterKnife.bind(this);

        /*Keypad to be hidden when a touch made outside the edit text*/
//        setupUI(mAlertViewGroup);


        setHeaderView();

        alertAPICall();

    }

    /*Populate the alert values*/
    private void setData(AlertResponse alertResponse) {

        setAdapter(alertResponse.getDatas());

    }

    /*set Adapter*/
    private void setAdapter(ArrayList<DatasEntity> notifications) {
        //AlertAdapter alertAdapter = new AlertAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mAlertRecyclerView.setLayoutManager(linearLayoutManager);
        mAlertRecyclerView.setAdapter(new AlertAdapter(notifications, this));

    }

    /*Alert API calls*/
    private void alertAPICall() {
        sysOut(PreferenceUtil.getAuthorization(this));
        APIRequestHandler.getInstance().alertAPICall(this);
    }

    /*Alert API request success and failure*/
    @Override
    public void onRequestSuccess(Object resObj) {
        super.onRequestSuccess(resObj);
        if (resObj instanceof AlertResponse) {
            AlertResponse alertResponse = (AlertResponse) resObj;
            setData(alertResponse);
        }
    }

    @Override
    public void onRequestFailure(Object resObj, Throwable t) {
        super.onRequestFailure(resObj,t);
        if (t instanceof IOException) {
            DialogManager.getInstance().showNetworkErrorPopup(this,
                    (t instanceof java.net.ConnectException ? getString(R.string.no_internet) : getString(R.string
                            .connect_time_out)), new InterfaceBtnCallback() {
                        @Override
                        public void onPositiveClick() {
                    alertAPICall();
                }
            });
        }
    }


    private void setHeaderView() {

        /*set header changes*/
        mHeaderTxt.setVisibility(View.VISIBLE);
        mHeaderRightImgLay.setVisibility(View.VISIBLE);
        mHeaderRightImg.setImageResource(R.drawable.ic_close);
        mHeaderTxt.setText(getString(R.string.notify));

         /*Set header adjustment - status bar we applied transparent color so header tack full view*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mAlertHeaderBgLay.post(new Runnable() {
                public void run() {
                    int heightInt = getResources().getDimensionPixelSize(R.dimen.size45);
                    mAlertHeaderBgLay.setLayoutParams(new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, heightInt + NumberUtil.getInstance().getStatusBarHeight(Alert.this)));
                    mAlertHeaderBgLay.setPadding(0, NumberUtil.getInstance().getStatusBarHeight(Alert.this), 0, 0);
                }

            });
        }

    }

    /*View onClick*/
    @OnClick({R.id.header_right_img_lay,R.id.header_left_img_lay})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_right_img_lay:
                onBackPressed();
                break;
            case R.id.header_left_img_lay:
                onBackPressed();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        backScreen();
    }
}