package com.calix.calixgigaspireapp.adapter.dashboard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.calix.calixgigaspireapp.R;
import com.calix.calixgigaspireapp.output.model.DatasEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AlertAdapter extends RecyclerView.Adapter<AlertAdapter.Holder> {

    private Context mContext;
    private ArrayList<DatasEntity> mDataEntryRes;

    public AlertAdapter(ArrayList<DatasEntity> dataEntryRes, Context context) {
        mContext = context;
        mDataEntryRes = dataEntryRes;
    }

    @NonNull
    @Override
    public AlertAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adap_alert, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlertAdapter.Holder holder, int position) {

        DatasEntity dataEntryRes = mDataEntryRes.get(position);
        // holder.mIssueTxt.setBackground(mContext.getResources().getDrawable(dataEntryRes.getRead()? R.drawable.box_textview_green:R.drawable.box_textview_shift));
        holder.mIssueTxt.setText(dataEntryRes.getText());
        holder.itemView.setBackgroundColor(dataEntryRes.getRead() ? ContextCompat.getColor(mContext, R.color.white) : ContextCompat.getColor(mContext, R.color.white));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataEntryRes.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.issue_txt)
        TextView mIssueTxt;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
