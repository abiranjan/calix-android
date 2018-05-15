package com.calix.calixgigaspireapp.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.calix.calixgigaspireapp.ui.loginregconfig.Splash;
import com.calix.calixgigaspireapp.utils.AppConstants;
import com.crashlytics.android.Crashlytics;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class CalixApplication extends android.app.Application {

    private static boolean activityVisible;
    private static CalixApplication mInstance;

    public static synchronized CalixApplication getInstance() {
        return mInstance;
    }

    public static Context getContext() {
        return mInstance;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityStopped() {
        activityVisible = false;
    }

    public static void activityFinished() {
        activityVisible = false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        final Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(true)
                .build();
        Fabric.with(fabric);
        mInstance = this;

        /*init UncaughtException*/
        Thread.setDefaultUncaughtExceptionHandler(new unCaughtException());

    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    /*unCaughtException*/
    private class unCaughtException implements UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            Crashlytics.logException(ex);

            /*Restart application*/
            if (activityVisible) {
//                Class<?> nextScreenClass = Login.class;
                Class<?> nextScreenClass = Splash.class;
//
//                if (PreferenceUtil.getBoolPreferenceValue(mInstance, AppConstants.PASS_CODE_ENABLE_STATUS)) {
//                    nextScreenClass = PinCodeFingerPrintLogin.class;
//                } else if (PreferenceUtil.getBoolPreferenceValue(mInstance, AppConstants.LOGIN_STATUS)) {
//                    nextScreenClass = Dashboard.class;
//                }

                /*for back screen process*/
                AppConstants.PREVIOUS_SCREEN = new ArrayList<>();
                AppConstants.PREVIOUS_SCREEN.add(nextScreenClass.getCanonicalName());

                Intent intent = new Intent(mInstance, nextScreenClass);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);

                if (getContext() instanceof Activity) {
                    ((Activity) getContext()).finish();
                }

                Runtime.getRuntime().exit(0);
            }
        }
    }


    @Override
    public void registerActivityLifecycleCallbacks(
            ActivityLifecycleCallbacks callback) {
        super.registerActivityLifecycleCallbacks(callback);
    }
}
