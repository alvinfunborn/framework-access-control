package com.alvin.framework.access.control.group;

import java.util.List;

/**
 * datetime 2019/5/17 17:27
 *
 * @author sin5
 */
public interface GroupRepository {

    /**
     * list all groups of member recursively
     *
     * @param member member
     * @return list of group
     */
    List<String> recursiveGroups(String member);

    /**
     * add group
     *
     * @param group group
     */
    void addGroup(Group group);
}
