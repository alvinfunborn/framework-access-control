package com.alvin.framework.access.control.model;

/**
 * datetime 2019/5/17 14:41
 * subject param for access enforce
 *
 * @author sin5
 */
public class Subject {

    /**
     * role name
     */
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
