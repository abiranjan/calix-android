package com.calix.calixgigaspireapp.adapter.devices;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.calix.calixgigaspireapp.R;
import com.calix.calixgigaspireapp.output.model.DeviceEntity;
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
        Log.d("adapter data",deviceListResponse.getName());
        holder.mDeviceImg.setImageResource(ImageUtil.getInstance().deviceImg(deviceListResponse.getType()));
        holder.mDeviceNameTxt.setText(deviceListResponse.getName());
        holder.mDeviceSubTypeTxt.setText(deviceListResponse.getSubType() == 1 ? mContext.getString(R.string.android) : mContext.getString(R.string.ios));
        holder.mConnectedDeviceNameTxt.setText(deviceListResponse.getRouter().getName());

        holder.mConnectionStatusImg.setImageResource(ImageUtil.getInstance().connectedStatusViaRouterImg(isDeviceConnectedBool,deviceListResponse.getIfType()));
        holder.mConnectionStatusTxt.setText(mContext.getString(isDeviceConnectedBool ? R.string.connected_to : R.string.disconnected_from));


        holder.mSignalStrengthTxt.setText(deviceListResponse.getSignalStrength() + " " + mContext.getString(R.string.percentage_sys));


        holder.mConnectDisconnectImg.setImageResource(ImageUtil.getInstance().connectedStatusViaRouterImg(!isDeviceConnectedBool,deviceListResponse.getIfType()));
        holder.mConnectDisconnectTxt.setText(String.format(mContext.getString(isDeviceConnectedBool ? R.string.disconnect_device : R.string.connect_device), deviceListResponse.getRouter().getName()));
        holder.mConnectDisconnectSwitchCompat.setChecked(isDeviceConnectedBool);

        holder.mDownloadSpeedTxt.setText(deviceListResponse.getSpeed().getDownload());
        holder.mUploadSpeedTxt.setText(deviceListResponse.getSpeed().getUpload());


        holder.mDeviceSubTypeTxt.setVisibility(deviceListResponse.getSubType() == 0 ? View.GONE : View.VISIBLE);
        holder.mVisibleInvisibleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDetailsShowingBool = holder.mDeviceDetailsLay.getVisibility() == View.VISIBLE;
                holder.mDeviceDetailsLay.setVisibility(isDetailsShowingBool ? View.GONE : View.VISIBLE);
                holder.mVisibleInvisibleImg.setImageResource(isDetailsShowingBool ? R.drawable.ic_up_arrow : R.drawable.down_arrow);

            }
        });

        holder.mConnectDisconnectSwitchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                holder.mConnectionStatusImg.setImageResource(ImageUtil.getInstance().connectedStatusViaRouterImg(isChecked,mDeviceListResponse.get(holder.getAdapterPosition()).getIfType()));

                holder.mConnectionStatusTxt.setText(mContext.getString(isChecked ? R.string.connected_to : R.string.disconnected_from));

                holder.mConnectDisconnectImg.setImageResource(ImageUtil.getInstance().connectedStatusViaRouterImg(!isChecked,mDeviceListResponse.get(holder.getAdapterPosition()).getIfType()));
                holder.mConnectDisconnectTxt.setText(String.format(mContext.getString(isChecked ? R.string.disconnect_device : R.string.connect_device), mDeviceListResponse.get(holder.getAdapterPosition()).getRouter().getName()));

//                if (isChecked)
//                    APIRequestHandler.getInstance().deviceConnectAPICall(mDeviceListResponse.get(holder.getAdapterPosition()).getDeviceId(), ((BaseActivity) mContext));
//                else
//                    APIRequestHandler.getInstance().deviceDisconnectAPICall(mDeviceListResponse.get(holder.getAdapterPosition()).getDeviceId(), ((BaseActivity) mContext));



                mDeviceListResponse.get(holder.getAdapterPosition()).setConnected2network(isChecked);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AppConstants.DEVICE_DETAILS_ENTITY = mDeviceListResponse.get(holder.getAdapterPosition());
//                ((BaseActivity) mContext).nextScreen(DeviceDetails.class);

            }
        });
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

        @BindView(R.id.device_sub_type_txt)
        TextView mDeviceSubTypeTxt;

        @BindView(R.id.connection_status_img)
        ImageView mConnectionStatusImg;

        @BindView(R.id.connection_status_txt)
        TextView mConnectionStatusTxt;

        @BindView(R.id.connected_device_name_txt)
        TextView mConnectedDeviceNameTxt;

        @BindView(R.id.connected_device_img)
        ImageView mConnectedDeviceImg;

        @BindView(R.id.signal_strength_txt)
        TextView mSignalStrengthTxt;

        @BindView(R.id.connect_disconnect_img)
        ImageView mConnectDisconnectImg;

        @BindView(R.id.connect_disconnect_txt)
        TextView mConnectDisconnectTxt;

        @BindView(R.id.connect_disconnect_switch_compat)
        SwitchCompat mConnectDisconnectSwitchCompat;

        @BindView(R.id.download_speed_txt)
        TextView mDownloadSpeedTxt;

        @BindView(R.id.upload_speed_txt)
        TextView mUploadSpeedTxt;

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


