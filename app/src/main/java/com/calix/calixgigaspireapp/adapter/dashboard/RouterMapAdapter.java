package com.calix.calixgigaspireapp.adapter.dashboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.calix.calixgigaspireapp.R;
import com.calix.calixgigaspireapp.main.BaseActivity;
import com.calix.calixgigaspireapp.output.model.RouterMapEntity;
import com.calix.calixgigaspireapp.services.APIRequestHandler;
import com.calix.calixgigaspireapp.ui.devices.DeviceDetails;
import com.calix.calixgigaspireapp.utils.AppConstants;
import com.calix.calixgigaspireapp.utils.DialogManager;
import com.calix.calixgigaspireapp.utils.InterfaceEdtBtnCallback;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RouterMapAdapter extends RecyclerView.Adapter<RouterMapAdapter.Holder> {

    private Context mContext;
    private ArrayList<RouterMapEntity> mRouterMapResArrayList;
    private String mDeviceNameStr = "";


    public RouterMapAdapter(ArrayList<RouterMapEntity> dataEntryRes, Context context) {
        mContext = context;
        mRouterMapResArrayList = dataEntryRes;
    }

    @Override
    public RouterMapAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adap_router_map_view, parent, false));
    }

    @Override
    public void onBindViewHolder(final RouterMapAdapter.Holder holder, int position) {

        RouterMapEntity routerMapRes = mRouterMapResArrayList.get(position);

        holder.mDeviceNameTxt.setText(routerMapRes.getName());
        holder.mDownloadSpeedTxt.setText(routerMapRes.getSpeed().getDownload());
        holder.mUploadSpeedTxt.setText(routerMapRes.getSpeed().getUpload());

        holder.mEditDeviceName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogManager.getInstance().showEdtDeviceNamePopup(((BaseActivity) mContext), holder.mDeviceNameTxt.getText().toString().trim(), new InterfaceEdtBtnCallback() {
                    @Override
                    public void onNegativeClick() {

                    }

                    @Override
                    public void onPositiveClick(String edtStr) {
                        mDeviceNameStr = edtStr;
                        edtDeviceNameAPI(mDeviceNameStr);
                        holder.mDeviceNameTxt.setText(edtStr);
                    }
                });
            }
        });

    }

    private void edtDeviceNameAPI(String deviceNameStr) {
//        APIRequestHandler.getInstance().routerUpdateAPICall(AppConstants.DEVICE_DETAILS_ENTITY.getDeviceId(), deviceNameStr, "1234", ((BaseActivity) mContext));
//        mDeviceNameTxt.setText(deviceNameStr);
//        mDeviceNameTxt.setText(deviceNameStr);
    }



    @Override
    public int getItemCount() {
        return mRouterMapResArrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.device_name_txt)
        TextView mDeviceNameTxt;

        @BindView(R.id.download_speed_txt)
        TextView mDownloadSpeedTxt;

        @BindView(R.id.upload_speed_txt)
        TextView mUploadSpeedTxt;

        @BindView(R.id.edit_device_name)
        ImageView mEditDeviceName;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
