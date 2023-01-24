package com.fms.exceptions;


import com.fms.httpsStatusCodesFMS.HttpStatusCodesFMS;

public class FMSException extends  RuntimeException{
    private final HttpStatusCodesFMS statusCode;

    public FMSException(HttpStatusCodesFMS statusCode){
        this.statusCode = statusCode;
    }

    public HttpStatusCodesFMS getStatusCode() {
        return statusCode;
    }

    public int getStatusCodeValue(){
        return HttpStatusCodesFMS.valueOf(statusCode);
    }
}
