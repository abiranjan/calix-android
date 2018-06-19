package com.calix.calixgigaspireapp.adapter.dashboard;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.calix.calixgigaspireapp.R;
import com.calix.calixgigaspireapp.main.BaseActivity;
import com.calix.calixgigaspireapp.output.model.CategoryEntity;
import com.calix.calixgigaspireapp.ui.devices.Devices;
import com.calix.calixgigaspireapp.ui.devices.DevicesList;
import com.calix.calixgigaspireapp.utils.AppConstants;
import com.calix.calixgigaspireapp.utils.CircularLayout;

import java.util.ArrayList;
import java.util.List;

public class DashboardAdapter extends PagerAdapter {

    private ArrayList<List<CategoryEntity>> categories;
    private Context mContext;
    int totalDevicesCount;

    public DashboardAdapter(Context mContext, ArrayList<List<CategoryEntity>> categories, int totalDevicesCount) {
        this.mContext = mContext;
        this.categories = categories;
        this.totalDevicesCount = totalDevicesCount;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object instantiateItem(ViewGroup collection, final int position) {
        LayoutInflater inflater = (LayoutInflater) collection.getContext()
                .getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View centerNode = inflater.inflate(R.layout.adapter_dashboard, null);
        CircularLayout circularLayout = centerNode.findViewById(R.id.circular_layout);
        ImageView mCenterHexIcon = centerNode.findViewById(R.id.center_hex_icon);
        TextView totalDeviceCount = centerNode.findViewById(R.id.deviceCount);

        if (totalDevicesCount > 0) {
            mCenterHexIcon.setColorFilter(mContext.getResources().getColor(R.color.sky_blue));
            totalDeviceCount.setVisibility(View.VISIBLE);
            totalDeviceCount.setText(String.valueOf(totalDevicesCount));

            mCenterHexIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((BaseActivity) mContext).nextScreen(DevicesList.class);
                }
            });
        } else {
            totalDeviceCount.setVisibility(View.GONE);
        }

        circularLayout.setCapacity(8);
        for (int i = 0; i < categories.get(position).size(); i++) {
            LayoutInflater factory = LayoutInflater.from(mContext);
            View myView = factory.inflate(R.layout.honey_comb_view, null);

            ImageView deviceIcon = myView.findViewById(R.id.deviceIcon);
            deviceIcon.setBackground(mContext.getResources().getDrawable(deviceListImg(categories.get(position).get(i).getType())));

            final int finalI = i;
            /* Click listener for devices list in the honeycomb outer */
            if (categories.get(position).get(finalI).getCount() > 0) {
                deviceIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppConstants.CATEGORY_ENTITY = categories.get(position).get(finalI);
                        ((BaseActivity) mContext).nextScreen(Devices.class);
                    }
                });
            }

            TextView deviceLabel = myView.findViewById(R.id.deviceLabel);
            String deviceName = Character.toUpperCase(categories.get(position).get(i).getName().charAt(0)) +
                    categories.get(position).get(i).getName().substring(1).toLowerCase();
            deviceLabel.setText(deviceName);


            TextView deviceCountTxtView = myView.findViewById(R.id.deviceCount);
            if (categories.get(position).get(i).getCount() > 0) {
                ImageView hexagonBg = myView.findViewById(R.id.hexagonal_icon_devices);
                hexagonBg.setColorFilter(mContext.getResources().getColor(R.color.sky_blue));
                deviceCountTxtView.setText(String.valueOf(categories.get(position).get(i).getCount()));
            } else {
                //Hide Device count if 0
                deviceCountTxtView.setVisibility(View.INVISIBLE);
            }

            circularLayout.addView(myView);
        }
        collection.addView(centerNode, 0);
        return centerNode;
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

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

}