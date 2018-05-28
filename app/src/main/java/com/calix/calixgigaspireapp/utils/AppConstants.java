package com.calix.calixgigaspireapp.utils;


import com.calix.calixgigaspireapp.input.model.ScannerInputModel;
import com.calix.calixgigaspireapp.output.model.CategoryEntity;
import com.calix.calixgigaspireapp.output.model.DeviceEntity;
import com.calix.calixgigaspireapp.output.model.GuestWifiEntity;
import com.calix.calixgigaspireapp.output.model.RouterMapEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;

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

    /*Device Details*/
    public static CategoryEntity CATEGORY_ENTITY = new CategoryEntity();
    public static DeviceEntity DEVICE_DETAILS_ENTITY = new DeviceEntity();

    /*Date formats*/
    static final SimpleDateFormat SERVER_DATE_TIME_FORMAT = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.US);
    public static final SimpleDateFormat CUSTOM_DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
    public static final SimpleDateFormat GRAPH_DATE_FORMAT = new SimpleDateFormat("MMM-dd", Locale.US);
    public static final SimpleDateFormat CUSTOM_12_HRS_TIME_FORMAT = new SimpleDateFormat("hh:mm aa", Locale.US);
    public static final SimpleDateFormat CUSTOM_24_HRS_TIME_FORMAT = new SimpleDateFormat("HH:mm", Locale.US);
    public static final SimpleDateFormat CUSTOM_DATE_TIME_FORMAT = new SimpleDateFormat("MM-dd-yyyy hh:mm aa", Locale.US);

    public static final String API_DEVICE_USAGE = "https://dev.rgw.calix.ai/map/v1/mobile/device/usage/history?deviceId=%1$s&filter=%2$s";

    public static GuestWifiEntity GUEST_WIFI_DETAILS = new GuestWifiEntity();

    /*Guest router_map*/
    public static LinkedHashMap<String, String> SELECTED_CONTACT_LINKED_HASH_MAP = new LinkedHashMap<>();

    public static final String QR_CODE_GENERATOR ="WIFI:S:%1$s;T:%2$s;P:%3$s;";





}

