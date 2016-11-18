package com.andrey.testsber.net;

/**
 * Created by Andrey Antonenko on 18.11.2016.
 */

public class Response {
    private boolean success;
    private int statusCode;
    private String errorString;
    private String successString;

    public Response(ServerError error) {
        success = error == ServerError.NO_ERROR;
        errorString = error.toString();
    }

    public Response(int statusCode, String responseString) {
        this.statusCode = statusCode;
        this.success = statusCode == 200;
        if (!success) {
            this.errorString = responseString;
        } else {
            this.successString = responseString;
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getErrorString() {
        return errorString;
    }

    public String getSuccessString() {
        return successString;
    }

}
