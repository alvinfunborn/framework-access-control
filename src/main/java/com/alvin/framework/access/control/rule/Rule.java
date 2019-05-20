package com.alvin.framework.access.control.rule;

import com.alvin.framework.access.control.condition.Condition;

/**
 * datetime 2019/5/16 20:28
 *
 * @author sin5
 */
public class Rule {

    /**
     * name of rule
     */
    private String name;

    /**
     * role of rule
     */
    private String role;
    /**
     * data of rule
     */
    private String data;
    /**
     * action of rule
     */
    private String action;
    /**
     * condition of rule
     */
    private Condition condition;
    /**
     * if deny this combination
     */
    private boolean deny;

    public static Rule ofPermit(String name, String role, String data, String action, Condition condition) {
        return new Rule(name, role, data, action, condition, false);
    }

    public static Rule ofDeny(String name, String role, String data, String action, Condition condition) {
        return new Rule(name, role, data, action, condition, true);
    }

    private Rule(String name, String role, String data, String action, Condition condition, boolean deny) {
        this.name = name;
        this.role = role;
        this.data = data;
        this.action = action;
        this.condition = condition;
        this.deny = deny;
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
}
