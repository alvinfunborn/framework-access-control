package com.alvin.framework.access.control.result;

/**
 * datetime 2019/5/16 20:11
 *
 * @author sin5
 */
public enum ResultCode {

    /**
     * PERMIT
     */
    PERMIT,
    /**
     * DENY
     */
    DENY,
    /**
     * UNCERTAIN(due to permission not found)
     */
    UNCERTAIN,
    /**
     * IMPROPER(defined by condition)
     */
    IMPROPER,

    ;
}
