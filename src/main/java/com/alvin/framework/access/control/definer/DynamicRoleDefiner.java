package com.alvin.framework.access.control.definer;

import com.alvin.framework.access.control.model.DynamicRoleSubject;
import com.alvin.framework.access.control.model.Resource;

/**
 * datetime 2019/8/31 15:02
 *
 * @author sin5
 */
public interface DynamicRoleDefiner {

    <S extends DynamicRoleSubject, R extends Resource> S define(S subject, R resource);
}
