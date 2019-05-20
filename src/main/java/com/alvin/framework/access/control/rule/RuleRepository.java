package com.alvin.framework.access.control.rule;

import java.util.List;

/**
 * datetime 2019/5/17 16:08
 *
 * @author sin5
 */
public interface RuleRepository {

    void addRule(Rule rule);

    Rule findPermitByRoleAndDataAndAction(String role, String data, String action);

    Rule findDenyByRoleAndDataAndAction(String role, String data, String action);

    List<Rule> findPermitByRoleAndData(String role, String data);

    List<Rule> findDenyByRoleAndData(String role, String data);
}
