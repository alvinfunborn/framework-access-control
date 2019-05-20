package com.alvin.framework.access.control.rule;

import java.util.*;

/**
 * datetime 2019/5/17 14:43
 *
 * @author sin5
 */
public class DefaultRuleRepository implements RuleRepository {

    private static final Map<String, Map<String, Map<String, Rule>>> PERMIT_ROLE_DATA_ACTION_MAP = new HashMap<>();
    private static final Map<String, Map<String, Map<String, Rule>>> DENY_ROLE_DATA_ACTION_MAP = new HashMap<>();

    @Override
    public void addRule(Rule rule) {
        boolean deny = rule.isDeny();
        if (deny) {
            put(DENY_ROLE_DATA_ACTION_MAP, rule);
        } else {
            put(PERMIT_ROLE_DATA_ACTION_MAP, rule);
        }
    }

    private void put(Map<String, Map<String, Map<String, Rule>>> map, Rule rule) {
        String role = rule.getRole();
        String data = rule.getData();
        String action = rule.getAction();
        if (!map.containsKey(role)) {
            map.put(role, new HashMap<>());
        }
        if (!map.get(role).containsKey(data)) {
            map.get(role).put(data, new HashMap<>());
        }
        map.get(role).get(data).put(action, rule);
    }

    @Override
    public Rule findPermitByRoleAndDataAndAction(String role, String data, String action) {
        return PERMIT_ROLE_DATA_ACTION_MAP.getOrDefault(role, Collections.emptyMap()).getOrDefault(data, Collections.emptyMap()).getOrDefault(action, null);
    }

    @Override
    public Rule findDenyByRoleAndDataAndAction(String role, String data, String action) {
        return DENY_ROLE_DATA_ACTION_MAP.getOrDefault(role, Collections.emptyMap()).getOrDefault(data, Collections.emptyMap()).getOrDefault(action, null);
    }

    @Override
    public List<Rule> findPermitByRoleAndData(String role, String data) {
        return new ArrayList<>(PERMIT_ROLE_DATA_ACTION_MAP.getOrDefault(role, Collections.emptyMap()).getOrDefault(data, Collections.emptyMap()).values());
    }

    @Override
    public List<Rule> findDenyByRoleAndData(String role, String data) {
        return new ArrayList<>(DENY_ROLE_DATA_ACTION_MAP.getOrDefault(role, Collections.emptyMap()).getOrDefault(data, Collections.emptyMap()).values());
    }
}
