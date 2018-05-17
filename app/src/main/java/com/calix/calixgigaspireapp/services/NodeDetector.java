package com.calix.calixgigaspireapp.services;

public interface NodeDetector {

    void onRequestSuccess(Object resObj);

    void onRequestFailure(Object resObj, Throwable t);


}