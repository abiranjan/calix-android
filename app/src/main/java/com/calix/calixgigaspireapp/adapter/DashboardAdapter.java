package com.calix.calixgigaspireapp.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.calix.calixgigaspireapp.R;
import com.calix.calixgigaspireapp.output.model.CategoryEntity;
import com.calix.calixgigaspireapp.utils.CircularLayout;

import java.util.ArrayList;
import java.util.List;

public class DashboardAdapter extends PagerAdapter {

    private ArrayList<List<CategoryEntity>> categories;
    private Context context;
    int totalDevicesCount;

    public DashboardAdapter(Context context, ArrayList<List<CategoryEntity>> categories, int totalDevicesCount) {
        this.context = context;
        this.categories = categories;
        this.totalDevicesCount = totalDevicesCount;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object instantiateItem(View collection, final int position) {
        LayoutInflater inflater = (LayoutInflater) collection.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View hcRow = inflater.inflate(R.layout.adapter_dashboard, null);
        CircularLayout circularLayout = hcRow.findViewById(R.id.circular_layout);

        TextView totalDeviceCount = hcRow.findViewById(R.id.deviceCount);
        totalDeviceCount.setText(String.valueOf(totalDevicesCount));
        circularLayout.setCapacity(8);
        for (int i = 0; i < categories.get(position).size(); i++) {
            LayoutInflater factory = LayoutInflater.from(context);
            View myView = factory.inflate(R.layout.honey_comb_view, null);

            ImageView deviceIcon = myView.findViewById(R.id.deviceIcon);
            deviceIcon.setBackground(null);
            switch (categories.get(position).get(i).getType()) {
                case 1:
                    deviceIcon.setBackground(context.getResources().getDrawable(R.drawable.ic_phone));
                    break;
                case 2:
                    deviceIcon.setBackground(context.getResources().getDrawable(R.drawable.ic_computer));
                    break;
                case 3:
                    deviceIcon.setBackground(context.getResources().getDrawable(R.drawable.ic_console));
                    break;
                case 4:
                    deviceIcon.setBackground(context.getResources().getDrawable(R.drawable.ic_storage));
                    break;
                case 5:
                    deviceIcon.setBackground(context.getResources().getDrawable(R.drawable.ic_printer));
                    break;
                case 6:
                    deviceIcon.setBackground(context.getResources().getDrawable(R.drawable.ic_television));
                    break;
                case 7:
                    deviceIcon.setBackground(context.getResources().getDrawable(R.drawable.ic_iot_device));
                    break;
                case 8:
                    deviceIcon.setBackground(context.getResources().getDrawable(R.drawable.ic_camera));
                    break;
                default:
                    deviceIcon.setBackground(context.getResources().getDrawable(R.drawable.ic_default_device));

            }

            final int finalI = i;
            deviceIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, categories.get(position).get(finalI).getName(), Toast.LENGTH_SHORT).show();
                }
            });

            TextView deviceLabel = myView.findViewById(R.id.deviceLabel);
            deviceLabel.setText(categories.get(position).get(i).getName());

            TextView deviceCountTxtView = myView.findViewById(R.id.deviceCount);
            if (categories.get(position).get(i).getCount() > 0) {
                ImageView hexagonBg = myView.findViewById(R.id.hexagonal_icon_devices);
                hexagonBg.setColorFilter(context.getResources().getColor(R.color.sky_blue));
                deviceCountTxtView.setText(String.valueOf(categories.get(position).get(i).getCount()));
            } else {
                //Hide Device count if 0
                deviceCountTxtView.setVisibility(View.INVISIBLE);
            }

            circularLayout.addView(myView);
        }
        ((ViewPager) collection).addView(hcRow, 0);
        return hcRow;
    }

    @Override
    public void destroyItem(View collection, int position, Object view) {
        ((ViewPager) collection).removeView((View) view);
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

    @Override
    public void startUpdate(View arg0) {
    }

    @Override
    public void finishUpdate(View arg0) {
    }
}