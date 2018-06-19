package com.calix.calixgigaspireapp.services;


import com.calix.calixgigaspireapp.input.model.LoginRegistrationInputModel;
import com.calix.calixgigaspireapp.output.model.AlertResponse;
import com.calix.calixgigaspireapp.output.model.ChartDetailsResponse;
import com.calix.calixgigaspireapp.output.model.ChartFilterResponse;
import com.calix.calixgigaspireapp.output.model.CommonResponse;
import com.calix.calixgigaspireapp.output.model.DashboardResponse;
import com.calix.calixgigaspireapp.output.model.DeviceFilterListResponse;
import com.calix.calixgigaspireapp.output.model.DeviceListResponse;
import com.calix.calixgigaspireapp.output.model.DeviceRenameResponse;
import com.calix.calixgigaspireapp.output.model.EncryptionTypeResponse;
import com.calix.calixgigaspireapp.output.model.FilterDeviceListResponse;
import com.calix.calixgigaspireapp.output.model.GuestWifiEntity;
import com.calix.calixgigaspireapp.output.model.GuestWifiResponse;
import com.calix.calixgigaspireapp.output.model.LoginResponse;
import com.calix.calixgigaspireapp.output.model.RegistrationResponse;
import com.calix.calixgigaspireapp.output.model.RouterMapResponse;
import com.calix.calixgigaspireapp.output.model.RouterSetupResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface APICommonInterface {

    /*Login API*/
    @POST("account/login")
    Call<LoginResponse> loginAPI(@Body LoginRegistrationInputModel loginInputModel);

    /*Registration API*/
    @PUT("account/add")
    Call<RegistrationResponse> registrationAPI(@Body LoginRegistrationInputModel registrationInputModel);

    /*Reset API*/
    @FormUrlEncoded
    @POST("account/reset")
    Call<CommonResponse> restPasswordAPI(@Field("email") String emailStr);

    /*Router Setup API*/
    @FormUrlEncoded
    @POST("router/factoryinfo/send")
    Call<RouterSetupResponse> routerSetupAPI(@Header("Authorization") String authorizationStr, @Field("macAddress") String macAddressStr, @Field("serialNumber") String serialNumberStr);

    /*EncryptionType API*/
    @GET("router/wifi/encryptionType")
    Call<EncryptionTypeResponse> encryptionTypeAPI(@Header("Authorization") String authorizationStr);

    /*Router Map API*/
    @GET("router/map")
    Call<RouterMapResponse> routerMapAPI(@Header("Authorization") String authorizationStr);

    /*Update Router Details API*/
    @FormUrlEncoded
    @POST("router/update")
    Call<CommonResponse> routerUpdateAPI(@Header("Authorization") String authorizationStr, @Field("routerId") String routerIdStr, @Field("routerName") String routerNameStr, @Field("ssid") String ssidStr, @Field("password") String passwordStr, @Field("encryptionType") String encryptionTypeStr);

    /*Dashboard API*/
    @GET("dashboard")
    Call<DashboardResponse> dashboardAPI(@Header("Authorization") String authorizationStr);

    /*Dashboard API*/
    @GET("device/list")
    Call<DeviceListResponse> deviceListAPI(@Header("Authorization") String authorizationStr, @Query("type") String deviceType);

    /*Disconnect the DeviceAPI*/
    @FormUrlEncoded
    @POST("device/disconnect")
    Call<CommonResponse> deviceDisconnectAPI(@Header("Authorization") String authorizationStr, @Field("deviceId") String deviceIdStr);

    /*Disconnect the DeviceAPI*/
    @FormUrlEncoded
    @POST("device/connect")
    Call<CommonResponse> deviceConnectAPI(@Header("Authorization") String authorizationStr, @Field("deviceId") String deviceIdStr);

    /*Rename the Device Name*/
    @FormUrlEncoded
    @POST("device/rename")
    Call<DeviceRenameResponse> deviceRenameAPI(@Header("Authorization") String authorizationStr, @Field("deviceId") String deviceIdStr, @Field("name") String nameStr, @Field("locationId") String locationIdStr);

    /*Chart API*/
    @GET("device/usage/filter")
    Call<ChartFilterResponse> deviceChartFilterAPI(@Header("Authorization") String authorizationStr);

    /*Chart Details API*/
    @GET
    Call<ChartDetailsResponse> deviceChartDetailsAPI(@Header("Authorization") String authorizationStr, @Url String urlStr);

    /*Alert API*/
    @GET("notification")
    Call<AlertResponse> alertAPI(@Header("Authorization") String authorizationStr);

    /*Guest List API*/
    @GET("router/guest")
    Call<GuestWifiResponse> guestWifiListAPI(@Header("Authorization") String authorizationStr);

    /*Add Guest List API*/
    @PUT("router/guest/add")
    Call<GuestWifiEntity> addGuestNetworkAPI(@Header("Authorization") String authorizationStr, @Body GuestWifiEntity guestWifiEntityInputModel);

    /*Update Guest List API*/
    @POST("router/guest/update")
    Call<GuestWifiEntity> updateGuestNetworkAPI(@Header("Authorization") String authorizationStr, @Body GuestWifiEntity guestWifiEntityInputModel);

    /*Delete Guest List API*/
    @DELETE("router/guest/delete?")
    Call<CommonResponse> deleteGuestNetworkAPI(@Header("Authorization") String authorizationStr, @Query("eventId") String eventId);

    /*Device Filter List API*/
    @GET("device/filter")
    Call<DeviceFilterListResponse> deviceFilterListAPI(@Header("Authorization") String authorizationStr);

    /*Device List By Filter API*/
    @GET
    Call<FilterDeviceListResponse> deviceListByFilterAPI(@Header("Authorization") String authorizationStr, @Url String urlStr);



}
