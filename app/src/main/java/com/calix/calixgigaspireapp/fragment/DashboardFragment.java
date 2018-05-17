package com.calix.calixgigaspireapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.calix.calixgigaspireapp.R;
import com.calix.calixgigaspireapp.adapter.DashboardAdapter;
import com.calix.calixgigaspireapp.main.BaseFragment;
import com.calix.calixgigaspireapp.output.model.CategoryEntity;
import com.calix.calixgigaspireapp.output.model.CommonResponse;
import com.calix.calixgigaspireapp.output.model.DashboardResponse;
import com.calix.calixgigaspireapp.services.APIRequestHandler;
import com.calix.calixgigaspireapp.services.NodeDetector;
import com.calix.calixgigaspireapp.ui.loginregconfig.HomeActivity;
import com.calix.calixgigaspireapp.utils.DialogManager;
import com.calix.calixgigaspireapp.utils.InterfaceBtnCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DashboardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class DashboardFragment extends BaseFragment {

    private OnFragmentInteractionListener mListener;


    @BindView(R.id.download)
    TextView download;

    @BindView(R.id.userName)
    TextView userName;

    @BindView(R.id.upload)
    TextView upload;

    @BindView(R.id.devices_viewpager)
    ViewPager pager;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this,rootView);
        dashboardAPICall();

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Optional
    @OnClick(R.id.img_fixed_center_device)
    public void devicesClicked() {
        ((HomeActivity) getActivity()).setSelectedItemBottomNav(R.id.navigation_devices);
    }

    /*API calls*/
    private void dashboardAPICall() {
        APIRequestHandler.getInstance().dashboardTypeAPICall(getActivity(), new NodeDetector() {
            @Override
            public void onRequestSuccess(Object resObj) {
                if (resObj instanceof DashboardResponse) {
                    DashboardResponse dashboardResponse = (DashboardResponse) resObj;
                    String fullName = dashboardResponse.getUser().getFirstName()+" "+dashboardResponse.getUser().getLastName();
                    userName.setText(fullName);
                    upload.setText(dashboardResponse.getSpeed().getUpload() + "Mbytes");
                    download.setText(dashboardResponse.getSpeed().getDownload() + "Mbytes");

                    ArrayList<CategoryEntity> categories = dashboardResponse.getCategories();

                    ArrayList<List<CategoryEntity>> splittedArray = new ArrayList<>();//This list will contain all the splitted arrays.
                    int lengthToSplit = 8;

                    for (int i = 0; i < categories.size(); i += 8) {
                        int start = i;
                        int end = 0;
                        if (i+8 < categories.size()) {
                            end = (start + 8);
                            Log.d("start", String.valueOf(start));
                            Log.d("end", String.valueOf(end));
                        }
                        else {
                            end = categories.size();
                            Log.d("start1", String.valueOf(start));
                            Log.d("end1", String.valueOf(end));
                        }
                        splittedArray.add(categories.subList(start, end));
                    }

                    pager.setAdapter(new DashboardAdapter(getActivity(),splittedArray,dashboardResponse.getDeviceCount()));
                    tabLayout.setupWithViewPager(pager, true);

                } else if (resObj instanceof CommonResponse) {
//                    DialogManager.getInstance().showAlertPopup(getActivity(), "Success", this);
                }
            }

            @Override
            public void onRequestFailure(Object resObj, Throwable t) {
                if (t instanceof IOException) {

                    if (resObj instanceof DashboardResponse) {
                        DialogManager.getInstance().showAlertPopup(getActivity(),
                                (t instanceof java.net.ConnectException ? getString(R.string.no_internet) : getString(R.string
                                        .connect_time_out)), new InterfaceBtnCallback() {
                                    @Override
                                    public void onPositiveClick() {

                                    }
                                });
                    }

                }
            }
        });
    }

}
