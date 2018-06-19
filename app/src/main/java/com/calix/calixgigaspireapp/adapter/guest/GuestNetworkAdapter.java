package com.calix.calixgigaspireapp.adapter.guest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.calix.calixgigaspireapp.R;
import com.calix.calixgigaspireapp.main.BaseActivity;
import com.calix.calixgigaspireapp.output.model.DurationEntity;
import com.calix.calixgigaspireapp.output.model.GuestWifiEntity;
import com.calix.calixgigaspireapp.ui.guest.SetupGuestNetwork;
import com.calix.calixgigaspireapp.utils.AppConstants;
import com.calix.calixgigaspireapp.utils.DateUtil;
import com.calix.calixgigaspireapp.utils.DialogManager;
import com.calix.calixgigaspireapp.utils.InterfaceEdtBtnCallback;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GuestNetworkAdapter extends RecyclerView.Adapter<GuestNetworkAdapter.ControlHolder> {

    private Context mContext;
    private ArrayList<GuestWifiEntity> mGuestWifiRes;
    private String mEventNameStr = "";

    public GuestNetworkAdapter(ArrayList<GuestWifiEntity> guestWifiRes, Context context) {
        mContext = context;
        mGuestWifiRes = guestWifiRes;

    }


    @NonNull
    @Override
    public GuestNetworkAdapter.ControlHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GuestNetworkAdapter.ControlHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_guest_network, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final GuestNetworkAdapter.ControlHolder holder, int position) {

        GuestWifiEntity guestWifiEntityRes = mGuestWifiRes.get(position);

        holder.mEventNameTxt.setText(guestWifiEntityRes.getEventName().isEmpty() ? guestWifiEntityRes.getGuestWifiName() : guestWifiEntityRes.getEventName());
        if (!guestWifiEntityRes.isIndefinite()) {
            try {
                Glide.with(mContext)
                        .load(guestWifiEntityRes.getGuestAvatarUrl())
                        .apply(new RequestOptions().placeholder(R.drawable.default_profile_white).error(R.drawable.default_profile_white))
                        .into(holder.mGuestUserImg);
            } catch (Exception ex) {
                holder.mGuestUserImg.setImageResource(R.drawable.default_profile_white);
                Log.e(AppConstants.TAG, ex.getMessage());
            }
            holder.mEventTimeTxt.setText(String.format(mContext.getString(R.string.activated_time),
                    (DateUtil.getCustomDateAndTimeFormat(mGuestWifiRes.get(position).getDuration().getStartTime(), AppConstants.CUSTOM_DATE_TIME_FORMAT) + " " + mContext.getString(R.string.to) + " " + DateUtil.getCustomDateAndTimeFormat(mGuestWifiRes.get(position).getDuration().getEndTime(), AppConstants.CUSTOM_DATE_TIME_FORMAT))));
            holder.mEventTimeTxt.setVisibility(View.GONE);
            holder.mStartDateText.setText((DateUtil.getCustomDateAndTimeFormat(mGuestWifiRes.get(position).getDuration().getStartTime(), AppConstants.CUSTOM_DATE_FORMAT)));
            holder.mEndDateText.setText((DateUtil.getCustomDateAndTimeFormat(mGuestWifiRes.get(position).getDuration().getEndTime(), AppConstants.CUSTOM_DATE_FORMAT)));
            holder.mStartTimeText.setText((DateUtil.getCustomDateAndTimeFormat(mGuestWifiRes.get(position).getDuration().getStartTime(), AppConstants.CUSTOM_12_HRS_TIME_FORMAT)));
            holder.mEndTimeText.setText((DateUtil.getCustomDateAndTimeFormat(mGuestWifiRes.get(position).getDuration().getEndTime(), AppConstants.CUSTOM_12_HRS_TIME_FORMAT)));
        } else {
            holder.mEventTimeTxt.setText(mContext.getText(R.string.no_time_limit));
            holder.mVisibleInvisibleImg.setVisibility(View.GONE);
            holder.mCollapsedView.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGuestWifiRes.get(holder.getAdapterPosition()).isIndefinite()) {
                    DurationEntity durationEntity = new DurationEntity();
                    durationEntity.setStartTime(System.currentTimeMillis());
                    durationEntity.setEndTime(System.currentTimeMillis());
                    mGuestWifiRes.get(holder.getAdapterPosition()).setDuration(durationEntity);
                }

                AppConstants.GUEST_WIFI_DETAILS = mGuestWifiRes.get(holder.getAdapterPosition());
                ((BaseActivity) mContext).nextScreen(SetupGuestNetwork.class);
            }
        });

        holder.mVisibleInvisibleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDetailsShowingBool = holder.mCollapsedView.getVisibility() == View.VISIBLE;
                holder.mCollapsedView.setVisibility(isDetailsShowingBool ? View.GONE : View.VISIBLE);
                holder.mVisibleInvisibleImg.setImageResource(isDetailsShowingBool ? R.drawable.ic_guest_up_arrow : R.drawable.ic_guest_down_arrow);

            }
        });

        holder.mEditGuestName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogManager.getInstance().showEdtDeviceNamePopup(((BaseActivity) mContext),
                        mContext.getString(R.string.guest_edit_pheader),
                        mContext.getString(R.string.guest_edit_sheader),
                        mContext.getString(R.string.guest_edit_hint),
                        holder.mEventNameTxt.getText().toString().trim(), new InterfaceEdtBtnCallback() {
                            @Override
                            public void onNegativeClick() {

                            }

                            @Override
                            public void onPositiveClick(String edtStr) {
                                mEventNameStr = edtStr;
                                editEventName(mEventNameStr);
                                holder.mEventNameTxt.setText(edtStr);
                            }
                        });
            }
        });

    }


    private void editEventName(String eventNameStr) {
//        APIRequestHandler.getInstance().guestUpdateAPICall(AppConstants.DEVICE_DETAILS_ENTITY.getDeviceId(), eventNameStr, "1234", ((BaseActivity) mContext));
//        mEventNameTxt.setText(eventNameStr);
//        mEventNameTxt.setText(eventNameStr);
    }

    @Override
    public int getItemCount() {
        return mGuestWifiRes.size();
    }

    class ControlHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.event_name_txt)
        TextView mEventNameTxt;

        @BindView(R.id.event_time_txt)
        TextView mEventTimeTxt;

        @BindView(R.id.start_date_txt)
        TextView mStartDateText;

        @BindView(R.id.end_date_txt)
        TextView mEndDateText;

        @BindView(R.id.start_time_txt)
        TextView mStartTimeText;

        @BindView(R.id.end_time_txt)
        TextView mEndTimeText;

        @BindView(R.id.adapter_guest_network_arrow_right_img)
        ImageView mVisibleInvisibleImg;

        @BindView(R.id.collapsedView)
        RelativeLayout mCollapsedView;

        @BindView(R.id.guest_user_img)
        ImageView mGuestUserImg;

        @BindView(R.id.guest_edit_img)
        ImageView mEditGuestName;


        private ControlHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

