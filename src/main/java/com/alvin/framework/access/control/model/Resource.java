package com.alvin.framework.access.control.model;

/**
 * datetime 2019/5/17 14:42
 * resource param for access enforce
 *
 * @author sin5
 */
public class Resource {

    /**
     * data name
     */
    private String data;

    public Resource(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
