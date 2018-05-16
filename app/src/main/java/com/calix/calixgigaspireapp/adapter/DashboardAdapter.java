package com.calix.calixgigaspireapp.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

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
    public Object instantiateItem(View collection, int position) {
        LayoutInflater inflater = (LayoutInflater) collection.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View hcRow =inflater.inflate(R.layout.adapter_dashboard,null);
        CircularLayout circularLayout = hcRow.findViewById(R.id.circular_layout);

        TextView totalDeviceCount = hcRow.findViewById(R.id.deviceCount);
        totalDeviceCount.setText(String.valueOf(totalDevicesCount));
        circularLayout.setCapacity(8);
        for (int i = 0; i < categories.get(position).size(); i++) {
            LayoutInflater factory = LayoutInflater.from(context);
            View myView = factory.inflate(R.layout.honey_comb_view, null);

            TextView deviceLabel = myView.findViewById(R.id.deviceLabel);
            deviceLabel.setText(categories.get(position).get(i).getName());

            TextView deviceCount = myView.findViewById(R.id.deviceCount);
            deviceCount.setText(String.valueOf(categories.get(position).get(i).getCount()));

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