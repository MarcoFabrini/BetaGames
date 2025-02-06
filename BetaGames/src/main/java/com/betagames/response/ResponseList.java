package com.betagames.response;

import java.util.List;

/**
 *
 * @author FabriniMarco
 */
public class ResponseList<T> extends ResponseBase {
    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

}// class
