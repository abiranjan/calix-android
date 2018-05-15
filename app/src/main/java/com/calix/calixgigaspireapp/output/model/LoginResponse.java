package com.calix.calixgigaspireapp.output.model;

import java.io.Serializable;

/**
 * Created by user on 1/22/2018.
 */

public class LoginResponse implements Serializable {


    private String token = "";
    private String email = "";
    private int routerOnboardedCounter;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRouterOnboardedCounter() {
        return routerOnboardedCounter;
    }

    public void setRouterOnboardedCounter(int routerOnboardedCounter) {
        this.routerOnboardedCounter = routerOnboardedCounter;
    }
}
