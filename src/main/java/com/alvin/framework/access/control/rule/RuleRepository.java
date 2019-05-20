package com.alvin.framework.access.control.rule;

import java.util.List;

/**
 * datetime 2019/5/17 16:08
 *
 * @author sin5
 */
public interface RuleRepository {

    /**
     * save rule
     *
     * @param rule rule
     */
    void addRule(Rule rule);

    /**
     * find permit rule by role and data and action
     *
     * @param role role
     * @param data data
     * @param action action
     * @return rule
     */
    Rule findPermitByRoleAndDataAndAction(String role, String data, String action);

    /**
     * find deny rule by role and data and action
     *
     * @param role role
     * @param data data
     * @param action action
     * @return rule
     */
    Rule findDenyByRoleAndDataAndAction(String role, String data, String action);

    /**
     * find permit rule by role and data
     *
     * @param role role
     * @param data data
     * @return rule
     */
    List<Rule> findPermitByRoleAndData(String role, String data);

    /**
     * find deny rule by role and data
     *
     * @param role role
     * @param data data
     * @return rule
     */
    List<Rule> findDenyByRoleAndData(String role, String data);
}
