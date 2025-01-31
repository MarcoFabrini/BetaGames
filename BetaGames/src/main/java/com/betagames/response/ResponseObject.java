package com.betagames.response;

/**
 *
 * @author Fabrini Marco
 */
public class ResponseObject<T> extends ResponseBase {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}// class
