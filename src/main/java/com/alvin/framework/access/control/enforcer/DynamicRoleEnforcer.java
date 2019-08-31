package com.alvin.framework.access.control.enforcer;

import com.alvin.framework.access.control.model.DynamicRoleSubject;
import com.alvin.framework.access.control.model.Resource;
import com.alvin.framework.access.control.result.Result;

import java.util.List;

/**
 * datetime 2019/8/31 14:24
 *
 * @author sin5
 */
public interface DynamicRoleEnforcer extends Enforcer {

    /**
     * enforce access
     *
     * @param subject subject
     * @param resource resource
     * @param action action
     * @param <S> class extends StaticRoleSubject
     * @param <R> class extends Resource
     * @return Result
     */
    <S extends DynamicRoleSubject, R extends Resource> Result enforce(S subject, R resource, String action);

    /**
     * list all permitted actions
     *
     * @param subject subject
     * @param resource resource
     * @param <S> class extends StaticRoleSubject
     * @param <R> class extends Resource
     * @return list of action
     */
    <S extends DynamicRoleSubject, R extends Resource> List<String> permittedActions(S subject, R resource);

    /**
     * list all denied actions
     *
     * @param subject subject
     * @param resource resource
     * @param <S> class extends StaticRoleSubject
     * @param <R> class extends Resource
     * @return list of action
     */
    <S extends DynamicRoleSubject, R extends Resource> List<String> deniedActions(S subject, R resource);
}
