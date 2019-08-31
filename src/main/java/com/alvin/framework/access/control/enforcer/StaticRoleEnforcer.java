package com.alvin.framework.access.control.enforcer;

import com.alvin.framework.access.control.model.Resource;
import com.alvin.framework.access.control.model.StaticRoleSubject;
import com.alvin.framework.access.control.result.Result;

import java.util.List;

/**
 * datetime 2019/5/16 20:04
 *
 * @author sin5
 */
public interface StaticRoleEnforcer extends Enforcer {

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
    <S extends StaticRoleSubject, R extends Resource> Result enforce(S subject, R resource, String action);

    /**
     * list all permitted actions
     *
     * @param subject subject
     * @param resource resource
     * @param <S> class extends StaticRoleSubject
     * @param <R> class extends Resource
     * @return list of action
     */
    <S extends StaticRoleSubject, R extends Resource> List<String> permittedActions(S subject, R resource);

    /**
     * list all denied actions
     *
     * @param subject subject
     * @param resource resource
     * @param <S> class extends StaticRoleSubject
     * @param <R> class extends Resource
     * @return list of action
     */
    <S extends StaticRoleSubject, R extends Resource> List<String> deniedActions(S subject, R resource);

}
