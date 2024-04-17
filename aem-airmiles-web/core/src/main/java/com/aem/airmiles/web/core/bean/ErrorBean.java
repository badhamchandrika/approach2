package com.aem.airmiles.web.core.bean;

import org.apache.commons.lang.StringUtils;


public class ErrorBean {
    private  String errorCode = StringUtils.EMPTY;
    private String errorMessage = StringUtils.EMPTY;

    public String getErrorCode() {
        return errorCode;
    }
    public String getErrorMessage() {
        return errorMessage;
    }

    public ErrorBean(){
    }
    public ErrorBean(String errorCode, String errorMessage) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
