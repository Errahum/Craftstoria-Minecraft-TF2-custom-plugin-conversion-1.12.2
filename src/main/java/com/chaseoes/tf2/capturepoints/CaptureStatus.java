package com.chaseoes.tf2.capturepoints;

public enum CaptureStatus {

    CAPTURED("captured"), UNCAPTURED("uncaptured"), CAPTURING("capturing"), CAPTURED2("captured2"), UNCAPTURED2("uncaptured2"), CAPTURING2("capturing2");

    private String status;

    private CaptureStatus(String s) {
        status = s;
    }

    public String string() {
        return status;
    }

}
