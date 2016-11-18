package com.andrey.testsber.net;

/**
 * Created by Andrey Antonenko on 18.11.2016.
 */

public enum ServerError {
    NO_ERROR(200),
    UNKNOWN_ERROR(1),
    NO_CONNECTION(2);

    private int code;

    public int getCode() {
        return code;
    }

    private ServerError(int code) {
        this.code = code;
    }

    public static ServerError fromCode(int code) {
        for (ServerError er : ServerError.values()) {
            if (er.getCode() == code) {
                return er;
            }
        }
        return ServerError.UNKNOWN_ERROR;
    }
}
