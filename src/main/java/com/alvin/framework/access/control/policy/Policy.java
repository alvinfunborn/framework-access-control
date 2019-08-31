package com.alvin.framework.access.control.policy;

import com.alvin.framework.access.control.condition.Condition;
import com.alvin.framework.access.control.result.ResultCode;

import java.util.HashMap;
import java.util.Map;

/**
 * datetime 2019/5/16 20:28
 *
 * @author sin5
 */
public class Policy {

    /**
     * name of policy
     */
    private String name;

    /**
     * role of policy
     */
    private String role;
    /**
     * data of policy
     */
    private String data;
    /**
     * action of policy
     */
    private String action;
    /**
     * condition of policy
     */
    private Condition condition;
    /**
     * if DENY this combination
     */
    private boolean deny;

    private Map<ResultCode, String> defaultResultMsg;

    public static Policy ofPermit(String name, String role, String data, String action) {
        return new Policy(name, role, data, action, null, false, null);
    }

    public static Policy ofPermit(String name, String role, String data, String action, Map<ResultCode, String> defaultResultMsg) {
        return new Policy(name, role, data, action, null, false, defaultResultMsg);
    }

    public static Policy ofPermit(String name, String role, String data, String action, Condition condition) {
        return new Policy(name, role, data, action, condition, false, null);
    }

    public static Policy ofPermit(String name, String role, String data, String action, Condition condition, Map<ResultCode, String> defaultResultMsg) {
        return new Policy(name, role, data, action, condition, false, defaultResultMsg);
    }

    public static Policy ofDeny(String name, String role, String data, String action) {
        return new Policy(name, role, data, action, null, true, null);
    }

    public static Policy ofDeny(String name, String role, String data, String action, Map<ResultCode, String> defaultResultMsg) {
        return new Policy(name, role, data, action, null, true, defaultResultMsg);
    }

    public static Policy ofDeny(String name, String role, String data, String action, Condition condition) {
        return new Policy(name, role, data, action, condition, true, null);
    }

    public static Policy ofDeny(String name, String role, String data, String action, Condition condition, Map<ResultCode, String> defaultResultMsg) {
        return new Policy(name, role, data, action, condition, true, defaultResultMsg);
    }

    private Policy(String name, String role, String data, String action, Condition condition, boolean deny, Map<ResultCode, String> defaultResultMsg) {
        this.name = name;
        this.role = role;
        this.data = data;
        this.action = action;
        this.condition = condition;
        this.deny = deny;
        if (defaultResultMsg != null) {
            this.defaultResultMsg = new HashMap<>(defaultResultMsg.size());
            this.defaultResultMsg.putAll(defaultResultMsg);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public boolean isDeny() {
        return deny;
    }

    public void setDeny(boolean deny) {
        this.deny = deny;
    }

    public Map<ResultCode, String> getDefaultResultMsg() {
        return defaultResultMsg;
    }

    public void setDefaultResultMsg(Map<ResultCode, String> defaultResultMsg) {
        if (defaultResultMsg != null) {
            this.defaultResultMsg = new HashMap<>(defaultResultMsg.size());
            this.defaultResultMsg.putAll(defaultResultMsg);
        }
    }
}
