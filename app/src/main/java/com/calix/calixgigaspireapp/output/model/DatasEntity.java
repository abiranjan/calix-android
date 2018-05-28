package com.calix.calixgigaspireapp.output.model;

import java.io.Serializable;

/**
 * Created by sibaprasad on 2/7/2018.
 */

public class DatasEntity implements Serializable {



    private String notifyId;
    private String type;
    private String text;
    private boolean isRead;

    public String getNotifyId() {
        return notifyId==null ? "" : notifyId;
    }

    public void setNotifyId(String notifyId) {
        this.notifyId = notifyId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }




}
