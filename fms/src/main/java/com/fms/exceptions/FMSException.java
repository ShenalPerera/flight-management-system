package com.fms.exceptions;


import com.fms.HttpStatusCodesFMS.HttpCodesFMS;

public class FMSException extends  RuntimeException{
    private final HttpCodesFMS statusCode;

    public FMSException(HttpCodesFMS statusCode){
        this.statusCode = statusCode;
    }

    public HttpCodesFMS getStatusCode() {
        return statusCode;
    }

    public int getStatusCodeValue(){
        return HttpCodesFMS.valueOf(statusCode);
    }
}
