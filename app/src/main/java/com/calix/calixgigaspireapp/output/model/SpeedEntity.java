package com.calix.calixgigaspireapp.output.model;

/**
 * Created by user on 2/6/2018.
 */

public class SpeedEntity {

    private double download;
    private double upload;

    public String getDownload() {
        return String.valueOf(download);
    }

    public void setDownload(int download) {
        this.download = download;
    }

    public String getUpload() {
        return String.valueOf(upload);
    }

    public void setUpload(int upload) {
        this.upload = upload;
    }

}
