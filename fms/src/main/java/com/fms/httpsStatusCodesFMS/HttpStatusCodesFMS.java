package com.fms.httpsStatusCodesFMS;

public enum HttpStatusCodesFMS {
    SAME_ARRIVAL_DEPARTURE_FOUND(250),
    DUPLICATE_ENTRY_FOUND(251),
    EMPTY_FIELD_FOUND(252),
    INVALID_DEPARTURE_AND_ARRIVAL_DATE(253),
    ENTRY_NOT_FOUND(254);

    private final int statusCode;
    HttpStatusCodesFMS(int statusCode){
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public static int valueOf(HttpStatusCodesFMS httpStatusCodesFMS){
        return httpStatusCodesFMS.statusCode;
    }


}
