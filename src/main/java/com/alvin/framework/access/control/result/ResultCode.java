package com.alvin.framework.access.control.result;

/**
 * datetime 2019/5/16 20:11
 *
 * @author sin5
 */
public enum ResultCode {

    /**
     * permit
     */
    permit,
    /**
     * deny
     */
    deny,
    /**
     * uncertain(due to permission not found)
     */
    uncertain,
    /**
     * improper(defined by condition)
     */
    improper;
}
