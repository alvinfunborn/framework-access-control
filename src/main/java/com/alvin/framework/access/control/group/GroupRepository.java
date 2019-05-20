package com.alvin.framework.access.control.group;

import java.util.List;

/**
 * datetime 2019/5/17 17:27
 *
 * @author sin5
 */
public interface GroupRepository {

    List<String> recursiveGroups(String inferior);

    void addGroup(Group group);
}
