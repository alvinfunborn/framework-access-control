package com.alvin.framework.access.control.controller;

import com.alvin.framework.access.control.group.Group;
import com.alvin.framework.access.control.hierarchy.Hierarchy;
import com.alvin.framework.access.control.model.Resource;
import com.alvin.framework.access.control.model.Subject;
import com.alvin.framework.access.control.rule.Rule;
import com.alvin.framework.access.control.result.Result;

import java.util.List;

/**
 * datetime 2019/5/16 20:04
 *
 * @author sin5
 */
public interface Controller {

    <S extends Subject, R extends Resource> Result control(S subject, R resource, String action);

    <S extends Subject, R extends Resource> List<String> permittedActions(S subject, R resource);

    <S extends Subject, R extends Resource> List<String> deniedActions(S subject, R resource);

    void addRule(Rule rule);

    void addRoleHierarchy(Hierarchy hierarchy);

    void addDataGroup(Group group);

    void addActionGroup(Group group);
}
