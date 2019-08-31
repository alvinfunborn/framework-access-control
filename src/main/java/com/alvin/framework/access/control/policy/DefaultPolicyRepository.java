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

    private static final Map<String, Map<String, Map<String, Policy>>> PERMIT_DATA_ACTION_ROLE_MAP = new HashMap<>();
    private static final Map<String, Map<String, Map<String, Policy>>> DENY_DATA_ACTION_ROLE_MAP = new HashMap<>();

    @Override
    public void addPolicy(Policy policy) {
        boolean deny = policy.isDeny();
        String role = policy.getRole();
        String data = policy.getData();
        String action = policy.getAction();
        if (deny) {
            put(DENY_ROLE_DATA_ACTION_MAP, policy, role, data, action);
            put(DENY_DATA_ACTION_ROLE_MAP, policy, data, action, role);
        } else {
            put(PERMIT_ROLE_DATA_ACTION_MAP, policy, role, data, action);
            put(PERMIT_DATA_ACTION_ROLE_MAP, policy, data, action, role);
        }
    }

    private void put(Map<String, Map<String, Map<String, Policy>>> map, Policy policy, String index1, String index2, String index3) {
        if (!map.containsKey(index1)) {
            map.put(index1, new HashMap<>());
        }
        if (!map.get(index1).containsKey(index2)) {
            map.get(index1).put(index2, new HashMap<>());
        }
        map.get(index1).get(index2).put(index3, policy);
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

    @Override
    public List<Policy> findPermitByDataAndAction(String data, String action) {
        return new ArrayList<>(PERMIT_DATA_ACTION_ROLE_MAP.getOrDefault(data, Collections.emptyMap()).getOrDefault(action, Collections.emptyMap()).values());
    }

    @Override
    public List<Policy> findDenyByDataAndAction(String data, String action) {
        return new ArrayList<>(DENY_DATA_ACTION_ROLE_MAP.getOrDefault(data, Collections.emptyMap()).getOrDefault(action, Collections.emptyMap()).values());
    }
}
