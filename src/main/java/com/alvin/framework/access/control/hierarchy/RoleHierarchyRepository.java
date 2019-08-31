package com.alvin.framework.access.control.hierarchy;

import java.util.List;

/**
 * datetime 2019/5/17 17:40
 *
 * @author sin5
 */
public interface RoleHierarchyRepository {

    /**
     * list all inferiors of superior recursively
     *
     * @param superior superior
     * @return list of inferior
     */
    List<String> recursiveInferiors(String superior);

    /**
     * add hierarchy
     *
     * @param hierarchy hierarchy
     */
    void addHierarchy(Hierarchy hierarchy) throws IllegalArgumentException;
}
