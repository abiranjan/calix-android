package com.calix.calixgigaspireapp.services;


import android.support.annotation.NonNull;

import com.calix.calixgigaspireapp.input.model.LoginRegistrationInputModel;
import com.calix.calixgigaspireapp.main.BaseActivity;
import com.calix.calixgigaspireapp.output.model.CommonResponse;
import com.calix.calixgigaspireapp.output.model.EncryptionTypeResponse;
import com.calix.calixgigaspireapp.output.model.ErrorResponse;
import com.calix.calixgigaspireapp.output.model.LoginResponse;
import com.calix.calixgigaspireapp.output.model.RegistrationResponse;
import com.calix.calixgigaspireapp.output.model.RouterMapResponse;
import com.calix.calixgigaspireapp.output.model.RouterSetupResponse;
import com.calix.calixgigaspireapp.utils.AppConstants;
import com.calix.calixgigaspireapp.utils.DialogManager;
import com.calix.calixgigaspireapp.utils.PreferenceUtil;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIRequestHandler {

    private static APIRequestHandler sInstance = new APIRequestHandler();

    /*Init retrofit for API call*/
    private Retrofit retrofit = new Retrofit.Builder().baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private  APICommonInterface mServiceInterface = retrofit.create( APICommonInterface.class);


    public static APIRequestHandler getInstance() {
        return sInstance;
    }


    /*Login API*/
    public void loginAPICall(LoginRegistrationInputModel loginInputModel, final BaseActivity baseActivity) {
        DialogManager.getInstance().showProgress(baseActivity);
        mServiceInterface.loginAPI(loginInputModel).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                DialogManager.getInstance().hideProgress();
                if (response.isSuccessful() && response.body() != null) {
                    baseActivity.onRequestSuccess(response.body());
                } else {
                    String errorMsgStr = response.raw().message();
                    try {
                        ErrorResponse errorBodyRes = new Gson().fromJson(Objects.requireNonNull(response.errorBody()).string(), ErrorResponse.class);
                        errorMsgStr = errorBodyRes.getErrorDesc().isEmpty() ? errorMsgStr : errorBodyRes.getErrorDesc();
                    } catch (IOException | JsonParseException e)  {
                        e.printStackTrace();
                    }
                    baseActivity.onRequestFailure(new LoginResponse(), new Throwable(errorMsgStr));
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                DialogManager.getInstance().hideProgress();
                baseActivity.onRequestFailure(new LoginResponse(), t);
            }
        });
    }


    /*Registration API*/
    public void registrationAPICall(LoginRegistrationInputModel registrationInputModel, final BaseActivity baseActivity) {
        DialogManager.getInstance().showProgress(baseActivity);
        mServiceInterface.registrationAPI(registrationInputModel).enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(@NonNull Call<RegistrationResponse> call, @NonNull Response<RegistrationResponse> response) {
                DialogManager.getInstance().hideProgress();
                if (response.isSuccessful() && response.body() != null) {
                    baseActivity.onRequestSuccess(response.body());
                } else {
                    String errorMsgStr = response.raw().message();
                    try {
                        ErrorResponse errorBodyRes = new Gson().fromJson(Objects.requireNonNull(response.errorBody()).string(), ErrorResponse.class);
                        errorMsgStr = errorBodyRes.getErrorDesc().isEmpty() ? errorMsgStr : errorBodyRes.getErrorDesc();
                    } catch (IOException | JsonParseException e)  {
                        e.printStackTrace();
                    }
                    baseActivity.onRequestFailure(new RegistrationResponse(), new Throwable(errorMsgStr));
                }
            }

            @Override
            public void onFailure(@NonNull Call<RegistrationResponse> call, @NonNull Throwable t) {
                DialogManager.getInstance().hideProgress();
                baseActivity.onRequestFailure(new RegistrationResponse(), t);
            }
        });
    }


    /*Reset Password API*/
    public void resetAPICall(String emailStr, final BaseActivity baseActivity) {
        DialogManager.getInstance().showProgress(baseActivity);
        mServiceInterface.restPasswordAPI(emailStr).enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                DialogManager.getInstance().hideProgress();
                if (response.isSuccessful()) {
                    CommonResponse commonResponse = new CommonResponse();
                    commonResponse.setErrorType(AppConstants.SUCCESS_CODE);
                    baseActivity.onRequestSuccess(commonResponse);
                } else {
                    String errorMsgStr = response.raw().message();
                    try {
                        ErrorResponse errorBodyRes = new Gson().fromJson(Objects.requireNonNull(response.errorBody()).string(), ErrorResponse.class);
                        errorMsgStr = errorBodyRes.getErrorDesc().isEmpty() ? errorMsgStr : errorBodyRes.getErrorDesc();
                    } catch (IOException | JsonParseException e)  {
                        e.printStackTrace();
                    }
                    baseActivity.onRequestFailure(new CommonResponse(), new Throwable(errorMsgStr));
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                DialogManager.getInstance().hideProgress();
                baseActivity.onRequestFailure(new CommonResponse(), t);
            }
        });
    }

    /*Router Setup API*/
    public void routerSetupAPICall(String macAddressStr, String serialNumberStr, final BaseActivity baseActivity) {
        DialogManager.getInstance().showProgress(baseActivity);
        mServiceInterface.routerSetupAPI(PreferenceUtil.getAuthorization(baseActivity), macAddressStr, serialNumberStr).enqueue(new Callback<RouterSetupResponse>() {
            @Override
            public void onResponse(@NonNull Call<RouterSetupResponse> call, @NonNull Response<RouterSetupResponse> response) {
                DialogManager.getInstance().hideProgress();
                if (response.isSuccessful() && response.body() != null) {
                    baseActivity.onRequestSuccess(response.body());
                } else {
                    String errorMsgStr = response.raw().message();
                    try {
                        ErrorResponse errorBodyRes = new Gson().fromJson(Objects.requireNonNull(response.errorBody()).string(), ErrorResponse.class);
                        errorMsgStr = errorBodyRes.getErrorDesc().isEmpty() ? errorMsgStr : errorBodyRes.getErrorDesc();
                    } catch (IOException | JsonParseException e) {
                        e.printStackTrace();
                    }

                    baseActivity.onRequestFailure(new RouterSetupResponse(), new Throwable(errorMsgStr));
                }
            }

            @Override
            public void onFailure(@NonNull Call<RouterSetupResponse> call, @NonNull Throwable t) {
                DialogManager.getInstance().hideProgress();
                baseActivity.onRequestFailure(new RouterSetupResponse(), t);
            }
        });
    }


    /*EncryptionType API*/
    public void encryptionTypeAPICall(final BaseActivity baseActivity) {
        DialogManager.getInstance().showProgress(baseActivity);
        mServiceInterface.encryptionTypeAPI(PreferenceUtil.getAuthorization(baseActivity)).enqueue(new Callback<EncryptionTypeResponse>() {
            @Override
            public void onResponse(@NonNull Call<EncryptionTypeResponse> call, @NonNull Response<EncryptionTypeResponse> response) {
                DialogManager.getInstance().hideProgress();
                if (response.isSuccessful() && response.body() != null) {
                    baseActivity.onRequestSuccess(response.body());
                } else {
                    String errorMsgStr = response.raw().message();
                    try {
                        ErrorResponse errorBodyRes = new Gson().fromJson(Objects.requireNonNull(response.errorBody()).string(), ErrorResponse.class);
                        errorMsgStr = errorBodyRes.getErrorDesc().isEmpty() ? errorMsgStr : errorBodyRes.getErrorDesc();
                    } catch (IOException | JsonParseException e) {
                        e.printStackTrace();
                    }
                    baseActivity.onRequestFailure(new EncryptionTypeResponse(), new Throwable(errorMsgStr));
                }
            }

            @Override
            public void onFailure(@NonNull Call<EncryptionTypeResponse> call, @NonNull Throwable t) {
                DialogManager.getInstance().hideProgress();
                baseActivity.onRequestFailure(new EncryptionTypeResponse(), t);
            }
        });
    }

    /*Router Map API*/
    public void routerMapAPICall(final BaseActivity baseActivity) {
        DialogManager.getInstance().showProgress(baseActivity);
        mServiceInterface.routerMapAPI(PreferenceUtil.getAuthorization(baseActivity)).enqueue(new Callback<RouterMapResponse>() {
            @Override
            public void onResponse(@NonNull Call<RouterMapResponse> call, @NonNull Response<RouterMapResponse> response) {
                DialogManager.getInstance().hideProgress();
                if (response.isSuccessful() && response.body() != null) {
                    baseActivity.onRequestSuccess(response.body());
                } else {
                    String errorMsgStr = response.raw().message();
                    try {
                        ErrorResponse errorBodyRes = new Gson().fromJson(Objects.requireNonNull(response.errorBody()).string(), ErrorResponse.class);
                        errorMsgStr = errorBodyRes.getErrorDesc().isEmpty() ? errorMsgStr : errorBodyRes.getErrorDesc();
                    } catch (IOException | JsonParseException e) {
                        e.printStackTrace();
                    }
                    baseActivity.onRequestFailure(new RouterMapResponse(), new Throwable(errorMsgStr));
                }
            }

            @Override
            public void onFailure(@NonNull Call<RouterMapResponse> call, @NonNull Throwable t) {
                DialogManager.getInstance().hideProgress();
                baseActivity.onRequestFailure(new RouterMapResponse(), t);
            }
        });
    }


    /*Update Router Details API*/
    public void routerUpdateAPICall(String routerIdStr, String routerNameStr, String ssidStr, String passwordStr, String encryptionTypeStr, final BaseActivity baseActivity) {
        DialogManager.getInstance().showProgress(baseActivity);
        mServiceInterface.routerUpdateAPI(PreferenceUtil.getAuthorization(baseActivity), routerIdStr, routerNameStr, ssidStr, passwordStr, encryptionTypeStr).enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                DialogManager.getInstance().hideProgress();
                if (response.isSuccessful()) {
                    CommonResponse commonResponse = new CommonResponse();
                    commonResponse.setErrorType(AppConstants.SUCCESS_CODE);
                    baseActivity.onRequestSuccess(commonResponse);
                } else {
                    String errorMsgStr = response.raw().message();
                    try {
                        ErrorResponse errorBodyRes = new Gson().fromJson(Objects.requireNonNull(response.errorBody()).string(), ErrorResponse.class);
                        errorMsgStr = errorBodyRes.getErrorDesc().isEmpty() ? errorMsgStr : errorBodyRes.getErrorDesc();
                    } catch (IOException | JsonParseException e) {
                        e.printStackTrace();
                    }
                    baseActivity.onRequestFailure(new CommonResponse(), new Throwable(errorMsgStr));
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                DialogManager.getInstance().hideProgress();
                baseActivity.onRequestFailure(new CommonResponse(), t);
            }
        });
    }


}


