package com.betagames.response;

/**
 *
 * @author FabriniMarco
 */
public class ResponseBase {
    private Boolean rc;// response code
    private String msg;

    public Boolean getRc() {
        return rc;
    }

    public void setRc(Boolean rc) {
        this.rc = rc;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}// class
