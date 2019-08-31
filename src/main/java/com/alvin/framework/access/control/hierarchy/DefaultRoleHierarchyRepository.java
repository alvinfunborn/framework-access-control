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
    private Map<String, List<String>> inferiorSuperiorMap = new HashMap<>();

    @Override
    public List<String> recursiveInferiors(String superior) {
        List<String> result = new ArrayList<>();
        recursiveInferiors(superior, result);
        return result;
    }

    @Override
    public List<String> recursiveSuperiors(String inferior) {
        List<String> result = new ArrayList<>();
        recursiveSuperiors(inferior, result);
        return result;
    }

    @Override
    public void addHierarchy(Hierarchy hierarchy) throws IllegalArgumentException {
        String inferior = hierarchy.getInferior();
        String superior = hierarchy.getSuperior();
        if (recursiveInferiors(inferior).contains(superior)) {
            throw new IllegalArgumentException();
        }
        if (!superiorInferiorMap.containsKey(superior)) {
            superiorInferiorMap.put(superior, new ArrayList<>());
        }
        superiorInferiorMap.get(superior).add(inferior);
        if (!inferiorSuperiorMap.containsKey(inferior)) {
            inferiorSuperiorMap.put(inferior, new ArrayList<>());
        }
        inferiorSuperiorMap.get(inferior).add(superior);
    }

    private void recursiveInferiors(String superior, List<String> result) {
        List<String> directSuperiors = superiorInferiorMap.getOrDefault(superior, new ArrayList<>());
        for (String directSuperior : directSuperiors) {
            result.add(directSuperior);
            recursiveInferiors(directSuperior, result);
        }
    }

    private void recursiveSuperiors(String inferior, List<String> result) {
        List<String> directInferiors = inferiorSuperiorMap.getOrDefault(inferior, new ArrayList<>());
        for (String directInferior : directInferiors) {
            result.add(directInferior);
            recursiveSuperiors(directInferior, result);
        }
    }
}
