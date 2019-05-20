package com.alvin.framework.access.control.model;

/**
 * datetime 2019/5/17 14:41
 *
 * @author sin5
 */
public class Subject {

    private String role;

    public Subject(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
