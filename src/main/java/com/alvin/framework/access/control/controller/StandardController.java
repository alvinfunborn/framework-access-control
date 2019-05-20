package com.alvin.framework.access.control.controller;

import com.alvin.framework.access.control.condition.Condition;
import com.alvin.framework.access.control.group.ActionGroupRepository;
import com.alvin.framework.access.control.group.DataGroupRepository;
import com.alvin.framework.access.control.group.Group;
import com.alvin.framework.access.control.hierarchy.*;
import com.alvin.framework.access.control.model.Resource;
import com.alvin.framework.access.control.model.Subject;
import com.alvin.framework.access.control.rule.Rule;
import com.alvin.framework.access.control.rule.RuleRepository;
import com.alvin.framework.access.control.result.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * datetime 2019/5/17 14:41
 *
 * @author sin5
 */
public class StandardController implements Controller {

    private RuleRepository ruleRepository;
    private RoleHierarchyRepository roleHierarchyRepository;
    private DataGroupRepository dataGroupRepository;
    private ActionGroupRepository actionGroupRepository;

    StandardController(RuleRepository ruleRepository,
                               RoleHierarchyRepository roleHierarchyRepository,
                               DataGroupRepository dataGroupRepository,
                               ActionGroupRepository actionGroupRepository) {
        this.ruleRepository = ruleRepository;
        this.roleHierarchyRepository = roleHierarchyRepository;
        this.dataGroupRepository = dataGroupRepository;
        this.actionGroupRepository = actionGroupRepository;
    }

    @Override
    public <S extends Subject, R extends Resource> Result control(S subject, R resource, String action) {
        String role = subject.getRole();
        String data = resource.getData();
        List<String> roleList = new ArrayList<>();
        List<String> dataList = new ArrayList<>();
        List<String> actionList = new ArrayList<>();
        roleList.add(role);
        roleList.addAll(roleHierarchyRepository.recursiveInferiors(role));
        dataList.add(data);
        dataList.addAll(dataGroupRepository.recursiveGroups(data));
        actionList.add(action);
        actionList.addAll(actionGroupRepository.recursiveGroups(action));
        Result result = null;
        boolean hit = false;
        for (String thisRole : roleList) {
            if (hit) {
                break;
            }
            for (String thisObj : dataList) {
                if (hit) {
                    break;
                }
                for (String thisAct : actionList) {
                    if (hit) {
                        break;
                    }
                    Rule rule = ruleRepository.findDenyByRoleAndDataAndAction(thisRole, thisObj, thisAct);
                    if (rule != null) {
                        Condition condition = rule.getCondition();
                        if (condition != null) {
                            result = condition.onCondition(subject, resource);
                            if (result.isPermit()) {
                                result = Result.ofDeny(result.getMsg());
                                hit = true;
                            }
                        } else {
                            result = Result.ofDeny();
                            hit = true;
                        }
                    }
                }
            }
        }
        if (result != null) {
            return result;
        } else {
            for (String thisRole : roleList) {
                for (String thisObj : dataList) {
                    for (String thisAct : actionList) {
                        Rule rule = ruleRepository.findPermitByRoleAndDataAndAction(thisRole, thisObj, thisAct);
                        if (rule != null) {
                            Condition condition = rule.getCondition();
                            if (condition != null) {
                                return condition.onCondition(subject, resource);
                            } else {
                                return Result.ofPermit();
                            }
                        }
                    }
                }
            }
        }
        return Result.ofDeny("permission not found");
    }

    @Override
    public <S extends Subject, R extends Resource> List<String> permittedActions(S subject, R resource) {
        String role = subject.getRole();
        String data = resource.getData();
        List<String> roleList = new ArrayList<>();
        List<String> dataList = new ArrayList<>();
        roleList.add(role);
        roleList.addAll(roleHierarchyRepository.recursiveInferiors(role));
        dataList.add(data);
        dataList.addAll(dataGroupRepository.recursiveGroups(data));
        List<String> result = new ArrayList<>();
        for (String thisRole : roleList) {
            for (String thisData : dataList) {
                result.addAll(ruleRepository.findPermitByRoleAndData(thisRole, thisData).stream().map(Rule::getAction).collect(Collectors.toList()));
            }
        }
        return result;
    }

    @Override
    public <S extends Subject, R extends Resource> List<String> deniedActions(S subject, R resource) {
        String role = subject.getRole();
        String data = resource.getData();
        List<String> roleList = new ArrayList<>();
        List<String> dataList = new ArrayList<>();
        roleList.add(role);
        roleList.addAll(roleHierarchyRepository.recursiveInferiors(role));
        dataList.add(data);
        dataList.addAll(dataGroupRepository.recursiveGroups(data));
        List<String> result = new ArrayList<>();
        for (String thisRole : roleList) {
            for (String thisData : dataList) {
                result.addAll(ruleRepository.findDenyByRoleAndData(thisRole, thisData).stream().map(Rule::getAction).collect(Collectors.toList()));
            }
        }
        return result;
    }

    @Override
    public void addRule(Rule rule) {
        ruleRepository.addRule(rule);
    }

    @Override
    public void addRoleHierarchy(Hierarchy hierarchy) {
        roleHierarchyRepository.addHierarchy(hierarchy);
    }

    @Override
    public void addDataGroup(Group group) {
        dataGroupRepository.addGroup(group);
    }

    @Override
    public void addActionGroup(Group group) {
        actionGroupRepository.addGroup(group);
    }
}
