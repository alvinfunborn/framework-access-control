package com.alvin.framework.access.control.policy;

import java.util.*;

/**
 * datetime 2019/5/17 14:43
 *
 * @author sin5
 */
public class DefaultPolicyRepository implements PolicyRepository {

    private static final Map<String, Map<String, Map<String, Policy>>> PERMIT_ROLE_DATA_ACTION_MAP = new HashMap<>();
    private static final Map<String, Map<String, Map<String, Policy>>> DENY_ROLE_DATA_ACTION_MAP = new HashMap<>();

    @Override
    public void addPolicy(Policy policy) {
        boolean deny = policy.isDeny();
        if (deny) {
            put(DENY_ROLE_DATA_ACTION_MAP, policy);
        } else {
            put(PERMIT_ROLE_DATA_ACTION_MAP, policy);
        }
    }

    private void put(Map<String, Map<String, Map<String, Policy>>> map, Policy policy) {
        String role = policy.getRole();
        String data = policy.getData();
        String action = policy.getAction();
        if (!map.containsKey(role)) {
            map.put(role, new HashMap<>());
        }
        if (!map.get(role).containsKey(data)) {
            map.get(role).put(data, new HashMap<>());
        }
        map.get(role).get(data).put(action, policy);
    }

    @Override
    public Policy findPermitByRoleAndDataAndAction(String role, String data, String action) {
        return PERMIT_ROLE_DATA_ACTION_MAP.getOrDefault(role, Collections.emptyMap()).getOrDefault(data, Collections.emptyMap()).getOrDefault(action, null);
    }

    @Override
    public Policy findDenyByRoleAndDataAndAction(String role, String data, String action) {
        return DENY_ROLE_DATA_ACTION_MAP.getOrDefault(role, Collections.emptyMap()).getOrDefault(data, Collections.emptyMap()).getOrDefault(action, null);
    }

    @Override
    public List<Policy> findPermitByRoleAndData(String role, String data) {
        return new ArrayList<>(PERMIT_ROLE_DATA_ACTION_MAP.getOrDefault(role, Collections.emptyMap()).getOrDefault(data, Collections.emptyMap()).values());
    }

    @Override
    public List<Policy> findDenyByRoleAndData(String role, String data) {
        return new ArrayList<>(DENY_ROLE_DATA_ACTION_MAP.getOrDefault(role, Collections.emptyMap()).getOrDefault(data, Collections.emptyMap()).values());
    }
}
