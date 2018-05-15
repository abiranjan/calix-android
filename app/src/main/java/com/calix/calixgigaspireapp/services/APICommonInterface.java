package com.calix.calixgigaspireapp.services;


import com.calix.calixgigaspireapp.input.model.LoginRegistrationInputModel;
import com.calix.calixgigaspireapp.output.model.CommonResponse;
import com.calix.calixgigaspireapp.output.model.EncryptionTypeResponse;
import com.calix.calixgigaspireapp.output.model.LoginResponse;
import com.calix.calixgigaspireapp.output.model.RegistrationResponse;
import com.calix.calixgigaspireapp.output.model.RouterMapResponse;
import com.calix.calixgigaspireapp.output.model.RouterSetupResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

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

}
