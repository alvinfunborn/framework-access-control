package com.alvin.framework.access.control.hierarchy;

/**
 * datetime 2019/5/17 10:04
 *
 * @author sin5
 */
public class Hierarchy {

    /**
     * superior role
     */
    private String superior;
    /**
     * inferior role
     */
    private String inferior;

    public Hierarchy(String superior, String inferior) {
        this.superior = superior;
        this.inferior = inferior;
    }

    public String getSuperior() {
        return superior;
    }

    public void setSuperior(String superior) {
        this.superior = superior;
    }

    public String getInferior() {
        return inferior;
    }

    public void setInferior(String inferior) {
        this.inferior = inferior;
    }
}
