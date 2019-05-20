package com.alvin.framework.access.control.hierarchy;

import java.util.List;

/**
 * datetime 2019/5/17 17:40
 *
 * @author sin5
 */
public interface RoleHierarchyRepository {

    List<String> recursiveInferiors(String superior);

    void addHierarchy(Hierarchy hierarchy);
}
