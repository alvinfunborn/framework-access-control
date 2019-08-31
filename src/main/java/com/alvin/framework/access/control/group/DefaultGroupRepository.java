package com.alvin.framework.access.control.group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * datetime 2019/5/17 17:41
 *
 * @author sin5
 */
public class DefaultGroupRepository implements GroupRepository {

    private Map<String, List<String>> memberGroupMap = new HashMap<>();

    @Override
    public List<String> recursiveGroups(String member) {
        List<String> result = new ArrayList<>();
        recursiveGroups(member, result);
        return result;
    }

    private void recursiveGroups(String inferior, List<String> result) {
        List<String> directSuperiors = memberGroupMap.getOrDefault(inferior, new ArrayList<>());
        for (String directSuperior : directSuperiors) {
            result.add(directSuperior);
            recursiveGroups(directSuperior, result);
        }
    }

    @Override
    public void addGroup(Group group) throws IllegalArgumentException {
        String[] inferiors = group.getMember();
        String superior = group.getGroup();
        List<String> superiorGroups = recursiveGroups(superior);
        for (String inferior : inferiors) {
            if (superiorGroups.contains(inferior)) {
                throw new IllegalArgumentException();
            }
            if (!memberGroupMap.containsKey(inferior)) {
                memberGroupMap.put(inferior, new ArrayList<>());
            }
            memberGroupMap.get(inferior).add(superior);
        }
    }
}
