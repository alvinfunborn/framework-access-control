package com.alvin.framework.access.control.hierarchy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * datetime 2019/5/17 17:52
 *
 * @author sin5
 */
public class DefaultRoleHierarchyRepository implements RoleHierarchyRepository {

    private Map<String, List<String>> superiorInferiorMap = new HashMap<>();

    @Override
    public List<String> recursiveInferiors(String superior) {
        List<String> result = new ArrayList<>();
        recursiveInferiors(superior, result);
        return result;
    }

    private void recursiveInferiors(String superior, List<String> result) {
        List<String> directSuperiors = superiorInferiorMap.getOrDefault(superior, new ArrayList<>());
        for (String directSuperior : directSuperiors) {
            result.add(directSuperior);
            recursiveInferiors(directSuperior, result);
        }
    }

    @Override
    public void addHierarchy(Hierarchy hierarchy) {
        String inferior = hierarchy.getInferior();
        String superior = hierarchy.getSuperior();
        if (!superiorInferiorMap.containsKey(superior)) {
            superiorInferiorMap.put(superior, new ArrayList<>());
        }
        superiorInferiorMap.get(superior).add(inferior);
    }
}
