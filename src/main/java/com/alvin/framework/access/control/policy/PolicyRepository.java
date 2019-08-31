package com.alvin.framework.access.control.policy;

import java.util.List;

/**
 * datetime 2019/5/17 16:08
 *
 * @author sin5
 */
public interface PolicyRepository {

    /**
     * save policy
     *
     * @param policy policy
     */
    void addPolicy(Policy policy);

    /**
     * find PERMIT policy by role and data and action
     *
     * @param role role
     * @param data data
     * @param action action
     * @return policy
     */
    Policy findPermitByRoleAndDataAndAction(String role, String data, String action);

    /**
     * find DENY policy by role and data and action
     *
     * @param role role
     * @param data data
     * @param action action
     * @return policy
     */
    Policy findDenyByRoleAndDataAndAction(String role, String data, String action);

    /**
     * find PERMIT policy by role and data
     *
     * @param role role
     * @param data data
     * @return policy
     */
    List<Policy> findPermitByRoleAndData(String role, String data);

    /**
     * find DENY policy by role and data
     *
     * @param role role
     * @param data data
     * @return policy
     */
    List<Policy> findDenyByRoleAndData(String role, String data);

    /**
     * find PERMIT policy by data and action
     *
     * @param data data
     * @param action action
     * @return policy
     */
    List<Policy> findPermitByDataAndAction(String data, String action);

    /**
     * find DENY policy by data and action
     *
     * @param data data
     * @param action action
     * @return policy
     */
    List<Policy> findDenyByDataAndAction(String data, String action);
}
