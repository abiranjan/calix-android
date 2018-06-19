package com.calix.calixgigaspireapp.adapter.devices;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;


import com.calix.calixgigaspireapp.R;
import com.calix.calixgigaspireapp.main.BaseActivity;
import com.calix.calixgigaspireapp.output.model.DeviceEntity;
import com.calix.calixgigaspireapp.services.APIRequestHandler;
import com.calix.calixgigaspireapp.ui.devices.DeviceDetails;
import com.calix.calixgigaspireapp.utils.AppConstants;
import com.calix.calixgigaspireapp.utils.ImageUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abiranjan(SmaatApps) on 20/05/2018.
 */

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ControlHolder> {

    private Context mContext;
    private ArrayList<DeviceEntity> mDeviceListResponse;

    public DeviceAdapter(ArrayList<DeviceEntity> deviceListResponse, Context context) {
        mDeviceListResponse = deviceListResponse;
        mContext = context;
    }


    @NonNull
    @Override
    public DeviceAdapter.ControlHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DeviceAdapter.ControlHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_device, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final DeviceAdapter.ControlHolder holder, int position) {

        DeviceEntity deviceListResponse = mDeviceListResponse.get(position);
        boolean isDeviceConnectedBool = deviceListResponse.isConnected2network();
        Log.d("adapter data", deviceListResponse.getName());
        holder.mDeviceImg.setImageResource(ImageUtil.getInstance().deviceImg(deviceListResponse.getType()));

        holder.mDeviceNameTxt.setText(deviceListResponse.getName());
        holder.mDeviceSubTypeTxt.setText(mContext.getString(setTypes(deviceListResponse.getType(),deviceListResponse.getSubType())));

        holder.mConnectedDeviceSignalTxt.setText(deviceListResponse.getSignalStrength() + mContext.getString(R.string.percentage_sys));

        holder.mConnectDisconnectImg.setImageResource(ImageUtil.getInstance().connectedStatusViaRouterImg(!isDeviceConnectedBool, deviceListResponse.getIfType()));
        holder.mConnectDisconnectTxt.setText(String.format(mContext.getString(isDeviceConnectedBool ? R.string.connect_device_list : R.string.disconnect_device_list), deviceListResponse.getRouter().getName()));
        holder.mToggleWifi.setChecked(isDeviceConnectedBool);
        if (isDeviceConnectedBool) {
            // The toggle is enabled
            holder.mToggleWifi.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);// Set left padding
            int padding = mContext.getResources().getDimensionPixelSize(R.dimen.size2);
            int paddingBottom = mContext.getResources().getDimensionPixelSize(R.dimen.size3);
            int paddingLeft = mContext.getResources().getDimensionPixelSize(R.dimen.size5);
            holder.mToggleWifi.setPadding(paddingLeft, padding, padding, paddingBottom);
        } else {
            // The toggle is disabled
            holder.mToggleWifi.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);// Set left padding
            int padding = mContext.getResources().getDimensionPixelSize(R.dimen.size2);
            int paddingTop = mContext.getResources().getDimensionPixelSize(R.dimen.size5);
            int paddingRight = mContext.getResources().getDimensionPixelSize(R.dimen.size3);
            holder.mToggleWifi.setPadding(padding, paddingTop, paddingRight, padding);
        }

        holder.mVisibleInvisibleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDetailsShowingBool = holder.mDeviceDetailsLay.getVisibility() == View.VISIBLE;
                holder.mDeviceDetailsLay.setVisibility(isDetailsShowingBool ? View.GONE : View.VISIBLE);
                holder.mVisibleInvisibleImg.setImageResource(isDetailsShowingBool ? R.drawable.ic_down_arrow : R.drawable.ic_up_arrow);

            }
        });

        holder.mToggleWifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                holder.mConnectDisconnectImg.setImageResource(ImageUtil.getInstance().connectedStatusViaRouterImg(!isChecked, mDeviceListResponse.get(holder.getAdapterPosition()).getIfType()));
                holder.mConnectDisconnectTxt.setText(String.format(mContext.getString(isChecked ? R.string.connect_device_list : R.string.disconnect_device_list), mDeviceListResponse.get(holder.getAdapterPosition()).getRouter().getName()));
                if (isChecked) {
                    // The toggle is enabled
                    holder.mToggleWifi.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);// Set left padding
                    int padding = mContext.getResources().getDimensionPixelSize(R.dimen.size2);
                    int paddingBottom = mContext.getResources().getDimensionPixelSize(R.dimen.size3);
                    int paddingLeft = mContext.getResources().getDimensionPixelSize(R.dimen.size5);
                    holder.mToggleWifi.setPadding(paddingLeft, padding, padding, paddingBottom);

                    APIRequestHandler.getInstance().deviceConnectAPICall(mDeviceListResponse.get(holder.getAdapterPosition()).getDeviceId(), ((BaseActivity) mContext));

                } else {
                    // The toggle is disabled
                    holder.mToggleWifi.setGravity(Gravity.END);// Set left padding
                    int padding = mContext.getResources().getDimensionPixelSize(R.dimen.size2);
                    int paddingTop = mContext.getResources().getDimensionPixelSize(R.dimen.size5);
                    int paddingRight = mContext.getResources().getDimensionPixelSize(R.dimen.size3);
                    holder.mToggleWifi.setPadding(padding, paddingTop, paddingRight, padding);

                    APIRequestHandler.getInstance().deviceDisconnectAPICall(mDeviceListResponse.get(holder.getAdapterPosition()).getDeviceId(), ((BaseActivity) mContext));

                }

                mDeviceListResponse.get(holder.getAdapterPosition()).setConnected2network(isChecked);

            }
        });

        holder.itemView.setOnClickListener(view -> {
            AppConstants.DEVICE_DETAILS_ENTITY = mDeviceListResponse.get(holder.getAdapterPosition());
            ((BaseActivity) mContext).nextScreen(DeviceDetails.class);
        });

    }

    private int setTypes(int deviceType, int subType) {
        switch (deviceType) {
            case 1:
                switch (subType) {
                    case 1:
                        return R.string.android;
                    case 2:
                        return R.string.ios;
                }
            case 2:
                switch (subType) {
                    case 0:
                        return R.string.linux;
                    case 1:
                        return R.string.windows;
                    case 2:
                        return R.string.apple;
                }
        }
        return R.string.noType;
    }


    @Override
    public int getItemCount() {
        return mDeviceListResponse.size();
    }

    class ControlHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.device_img)
        ImageView mDeviceImg;

        @BindView(R.id.device_name_txt)
        TextView mDeviceNameTxt;

        @BindView(R.id.device_subtype_txt)
        TextView mDeviceSubTypeTxt;

        @BindView(R.id.connected_device_signal_txt)
        TextView mConnectedDeviceSignalTxt;

        @BindView(R.id.connected_device_img)
        ImageView mConnectedDeviceImg;

        @BindView(R.id.connect_disconnect_img)
        ImageView mConnectDisconnectImg;

        @BindView(R.id.connect_disconnect_txt)
        TextView mConnectDisconnectTxt;

        @BindView(R.id.connect_disconnect_toggle)
        ToggleButton mToggleWifi;

        @BindView(R.id.device_details_lay)
        LinearLayout mDeviceDetailsLay;

        @BindView(R.id.visible_invisible_img)
        ImageView mVisibleInvisibleImg;


        private ControlHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}


