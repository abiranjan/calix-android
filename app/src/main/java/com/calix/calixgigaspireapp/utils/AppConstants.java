package com.calix.calixgigaspireapp.utils;


import com.calix.calixgigaspireapp.input.model.ScannerInputModel;
import com.calix.calixgigaspireapp.output.model.RouterMapEntity;

import java.util.ArrayList;

public class AppConstants {

    static final String SHARE_PREFERENCE = "SHARE_PREFERENCE";
    public static String TAG = "TAG";

    /*Screen name for clear the hole activity*/
    public static final String SUCCESS_CODE = "200";

    /*Flags*/
    public static String LOGIN_STATUS = "LOGIN_STATUS";

    /*PREVIOUS_SCREEN*/
    public static ArrayList<String> PREVIOUS_SCREEN = new ArrayList<>();

    /*Screen name for clear the hole activity*/
    public static final String LOGIN_SCREEN = "Login";

    /*User details*/
    public static String AUTHORIZATION = "AUTHORIZATION";
    static String BEARER = "Bearer";

    /*App URL's*/
    final public static String BASE_URL = "https://dev.rgw.calix.ai/map/v1/mobile/";

    /*Pin code with finger print*/
    public static final String ANDROID = "android";

    /*Registration Screen Flag*/
    public static boolean ROUTER_ON_BOARD_FROM_SETTINGS = false;
    public static RouterMapEntity ROUTER_DETAILS_ENTITY = new RouterMapEntity();

    /*Router Config Screen*/
    public static ScannerInputModel SCANNED_DETAILS_RES = new ScannerInputModel();
    public static boolean ROUTER_ON_BOARD_FROM_WELCOME = false;


}

