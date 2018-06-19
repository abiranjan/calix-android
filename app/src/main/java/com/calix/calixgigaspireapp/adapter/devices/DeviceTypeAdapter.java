package com.calix.calixgigaspireapp.adapter.devices;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.calix.calixgigaspireapp.output.model.CategoryEntity;
import com.calix.calixgigaspireapp.output.model.DeviceEntity;
import com.calix.calixgigaspireapp.output.model.DeviceListEntity;
import com.calix.calixgigaspireapp.services.APIRequestHandler;
import com.calix.calixgigaspireapp.ui.devices.DeviceDetails;
import com.calix.calixgigaspireapp.ui.devices.Devices;
import com.calix.calixgigaspireapp.utils.AppConstants;
import com.calix.calixgigaspireapp.utils.ImageUtil;
import com.google.android.gms.vision.text.Line;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abiranjan(SmaatApps) on 20/05/2018.
 */

public class DeviceTypeAdapter extends RecyclerView.Adapter<DeviceTypeAdapter.ControlHolder> {

    private Context mContext;
    private ArrayList<DeviceListEntity> mDeviceListResponse;

    public DeviceTypeAdapter(ArrayList<DeviceListEntity> deviceListResponse, Context context) {
        mDeviceListResponse = deviceListResponse;
        mContext = context;
    }


    @NonNull
    @Override
    public DeviceTypeAdapter.ControlHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DeviceTypeAdapter.ControlHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_device_type, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final DeviceTypeAdapter.ControlHolder holder, int position) {

        DeviceListEntity deviceListResponse = mDeviceListResponse.get(position);
        holder.mDeviceImg.setImageResource(ImageUtil.getInstance().deviceImg(mDeviceListResponse.get(position).getDeviceType()));
        holder.mDeviceNameTxt.setText(deviceList(mDeviceListResponse.get(position).getDeviceType()));

        holder.mDevicesList.setLayoutManager(new LinearLayoutManager(mContext));
        holder.mDevicesList.setNestedScrollingEnabled(false);
        holder.mDevicesList.setAdapter(new DeviceAdapter(deviceListResponse.getDevicesList(), mContext));

        holder.mVisibleInvisibleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDetailsShowingBool = holder.mDevicesList.getVisibility() == View.VISIBLE;
                holder.mDevicesList.setVisibility(isDetailsShowingBool ? View.GONE : View.VISIBLE);
                holder.mHorSeparator.setVisibility(isDetailsShowingBool ? View.GONE : View.VISIBLE);
                holder.mVisibleInvisibleImg.setImageResource(isDetailsShowingBool ? R.drawable.ic_down_arrow : R.drawable.ic_up_arrow);

                ViewGroup.MarginLayoutParams layoutParams =
                        (ViewGroup.MarginLayoutParams) holder.mListLayout.getLayoutParams();
                if (isDetailsShowingBool) {
                    int margin = mContext.getResources().getDimensionPixelSize(R.dimen.size5);
                    layoutParams.setMargins(margin, margin, margin, margin);
                    holder.mListLayout.requestLayout();
                }else{
                    int margin = mContext.getResources().getDimensionPixelSize(R.dimen.size0_25);
                    layoutParams.setMargins(margin, margin, margin, margin);
                    holder.mListLayout.requestLayout();
                }
            }
        });

//        holder.mDeviceTypeList.setOnClickListener(v -> {
//            CategoryEntity category = new CategoryEntity();
//            category.setType(mDeviceListResponse.get(position).getDeviceType());
//                AppConstants.CATEGORY_ENTITY = category;
//            ((BaseActivity) mContext).nextScreen(Devices.class);
//        });

    }

    /*find the IOT device Image*/
    private int deviceListImg(int deviceTypeInt) {
        switch (deviceTypeInt) {
            case 1:
                return R.drawable.ic_phone;
            case 2:
                return R.drawable.ic_computer;
            case 3:
                return R.drawable.ic_console;
            case 4:
                return R.drawable.ic_storage;
            case 5:
                return R.drawable.ic_printer;
            case 6:
                return R.drawable.ic_television;
            case 7:
                return R.drawable.ic_iot_device;
            case 8:
                return R.drawable.ic_camera;
            default:
                return R.drawable.ic_default_device;
        }
    }

    /*find the IOT device List*/
    private int deviceList(int deviceTypeInt) {
        switch (deviceTypeInt) {
            case 1:
                return R.string.phone;
            case 2:
                return R.string.computer;
            case 3:
                return R.string.console;
            case 4:
                return R.string.storage;
            case 5:
                return R.string.printer;
            case 6:
                return R.string.television;
            case 7:
                return R.string.iot_device;
            case 8:
                return R.string.camera;
            default:
                return R.string.unknown;
        }
    }

    @Override
    public int getItemCount() {
        return mDeviceListResponse.size();
    }

    class ControlHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.device_type_img)
        ImageView mDeviceImg;

        @BindView(R.id.device_name_txt)
        TextView mDeviceNameTxt;

        @BindView(R.id.visible_invisible_img)
        ImageView mVisibleInvisibleImg;

        @BindView(R.id.devices_recycler_view)
        RecyclerView mDevicesList;

        @BindView(R.id.separator)
        View mHorSeparator;

        @BindView(R.id.list_lay)
        CardView mListLayout;

        @BindView(R.id.device_type_lay)
        LinearLayout mDeviceTypeList;

        private ControlHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}


