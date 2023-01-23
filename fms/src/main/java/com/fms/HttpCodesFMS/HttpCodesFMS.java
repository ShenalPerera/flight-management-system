package com.fms.HttpCodesFMS;

public enum HttpCodesFMS  {
    SAME_ARRIVAL_DEPARTURE_FOUND(250),
    DUPLICATE_ENTRY_FOUND(251),

    EMPTY_FILED_FOUND(252),
    INVALID_DEPARTURE_AND_ARRIVAL_DATE(253);
    private final int statusCode;
    HttpCodesFMS(int statusCode){
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public static int valueOf(HttpCodesFMS httpCodesFMS){
        return httpCodesFMS.statusCode;
    }


}
