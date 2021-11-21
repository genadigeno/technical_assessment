package com.task.core.http;

import java.io.Serializable;

public class StatusResponse implements Serializable {
    public StatusResponse() {}

    public StatusResponse(int status) {
        this.status = status;
    }

    public StatusResponse(int status, String result, String error) {
        this.status = status;
        this.result = result;
        this.error = error;
    }

    private int status;

    private String error;

    private String result;

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "StatusResponse{status=" + status + '}';
    }
}
