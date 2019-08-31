package com.alvin.framework.access.control.enforcer;

import com.alvin.framework.access.control.group.Group;
import com.alvin.framework.access.control.hierarchy.Hierarchy;
import com.alvin.framework.access.control.policy.Policy;

/**
 * datetime 2019/8/31 14:20
 *
 * @author sin5
 */
public interface Enforcer {

    /**
     * add policy
     *
     * @param policy policy
     */
    void addPolicy(Policy policy);

    /**
     * add role hierarchy
     * superior get all permission of inferior
     *
     * @param hierarchy hierarchy
     */
    void addRoleHierarchy(Hierarchy hierarchy) throws IllegalArgumentException;

    /**
     * add data group
     *
     * @param group group
     */
    void addDataGroup(Group group) throws IllegalArgumentException;

    /**
     * add action group
     *
     * @param group group
     */
    void addActionGroup(Group group) throws IllegalArgumentException;
}
