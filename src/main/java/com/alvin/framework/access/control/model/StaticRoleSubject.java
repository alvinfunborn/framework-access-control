package com.alvin.framework.access.control.model;

/**
 * datetime 2019/5/17 14:41
 * static-role subject param for access enforce
 *
 * @author sin5
 */
public class StaticRoleSubject extends Subject {

    public StaticRoleSubject(String role) {
        this.role = role;
    }
}
