package com.gym.api.rest;

public class ObjectErrorResponse {

    private int status;
    private String message;
    private long timeStamp;

    public ObjectErrorResponse() {

    }

    public ObjectErrorResponse(int status, String message, long timeStamp) {

        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
