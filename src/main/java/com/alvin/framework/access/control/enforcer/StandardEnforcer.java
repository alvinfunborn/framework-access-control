package com.alvin.framework.access.control.enforcer;

import com.alvin.framework.access.control.condition.Condition;
import com.alvin.framework.access.control.definer.DynamicRoleDefiner;
import com.alvin.framework.access.control.group.ActionGroupRepository;
import com.alvin.framework.access.control.group.DataGroupRepository;
import com.alvin.framework.access.control.group.Group;
import com.alvin.framework.access.control.hierarchy.Hierarchy;
import com.alvin.framework.access.control.hierarchy.RoleHierarchyRepository;
import com.alvin.framework.access.control.model.DynamicRoleSubject;
import com.alvin.framework.access.control.model.Resource;
import com.alvin.framework.access.control.model.StaticRoleSubject;
import com.alvin.framework.access.control.model.Subject;
import com.alvin.framework.access.control.policy.Policy;
import com.alvin.framework.access.control.policy.PolicyRepository;
import com.alvin.framework.access.control.result.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * datetime 2019/8/31 14:58
 *
 * @author sin5
 */
public class StandardEnforcer implements StaticRoleEnforcer, DynamicRoleEnforcer {
    private PolicyRepository policyRepository;
    private RoleHierarchyRepository roleHierarchyRepository;
    private DataGroupRepository dataGroupRepository;
    private ActionGroupRepository actionGroupRepository;
    private DynamicRoleDefiner dynamicRoleDefiner;

    StandardEnforcer(PolicyRepository policyRepository,
                               RoleHierarchyRepository roleHierarchyRepository,
                               DataGroupRepository dataGroupRepository,
                               ActionGroupRepository actionGroupRepository) {
        this.policyRepository = policyRepository;
        this.roleHierarchyRepository = roleHierarchyRepository;
        this.dataGroupRepository = dataGroupRepository;
        this.actionGroupRepository = actionGroupRepository;
    }

    StandardEnforcer(PolicyRepository policyRepository,
                     RoleHierarchyRepository roleHierarchyRepository,
                     DataGroupRepository dataGroupRepository,
                     ActionGroupRepository actionGroupRepository,
                     DynamicRoleDefiner dynamicRoleDefiner) {
        this.policyRepository = policyRepository;
        this.roleHierarchyRepository = roleHierarchyRepository;
        this.dataGroupRepository = dataGroupRepository;
        this.actionGroupRepository = actionGroupRepository;
        this.dynamicRoleDefiner = dynamicRoleDefiner;
    }

    @Override
    public <S extends StaticRoleSubject, R extends Resource> Result enforce(S subject, R resource, String action) {
        return doEnforce(subject, resource, action);
    }

    @Override
    public <S extends DynamicRoleSubject, R extends Resource> Result enforce(S subject, R resource, String action) {
        return doEnforce(dynamicRoleDefiner.define(subject, resource), resource, action);
    }

    private <S extends Subject, R extends Resource> Result doEnforce(S subject, R resource, String action) {
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
                    Policy policy = policyRepository.findDenyByRoleAndDataAndAction(thisRole, thisObj, thisAct);
                    if (policy != null) {
                        Condition condition = policy.getCondition();
                        if (condition != null) {
                            result = condition.onCondition(subject, resource);
                            if (result.isPermit()) {
                                result = Result.ofDeny(result.getMsg());
                                result.replaceMsg(policy.getDefaultResultMsg());
                                hit = true;
                            }
                        } else {
                            result = Result.ofDeny();
                            result.replaceMsg(policy.getDefaultResultMsg());
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
                        Policy policy = policyRepository.findPermitByRoleAndDataAndAction(thisRole, thisObj, thisAct);
                        if (policy != null) {
                            Condition condition = policy.getCondition();
                            if (condition != null) {
                                result = condition.onCondition(subject, resource);
                            } else {
                                result = Result.ofPermit();
                            }
                            result.replaceMsg(policy.getDefaultResultMsg());
                        }
                    }
                }
            }
        }
        if (result != null) {
            return result;
        }
        return Result.ofUncertain("policy not found");
    }

    @Override
    public <S extends StaticRoleSubject, R extends Resource> List<String> permittedActions(S subject, R resource) {
        return doPermittedActions(subject, resource);
    }

    @Override
    public <S extends DynamicRoleSubject, R extends Resource> List<String> permittedActions(S subject, R resource) {
        return doPermittedActions(dynamicRoleDefiner.define(subject, resource), resource);
    }

    private <S extends Subject, R extends Resource> List<String> doPermittedActions(S subject, R resource) {
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
                result.addAll(policyRepository.findPermitByRoleAndData(thisRole, thisData).stream().map(Policy::getAction).collect(Collectors.toList()));
            }
        }
        return result;
    }

    @Override
    public <S extends StaticRoleSubject, R extends Resource> List<String> deniedActions(S subject, R resource) {
        return doDeniedActions(subject, resource);
    }

    @Override
    public <S extends DynamicRoleSubject, R extends Resource> List<String> deniedActions(S subject, R resource) {
        return doDeniedActions(dynamicRoleDefiner.define(subject, resource), resource);
    }

    private <S extends Subject, R extends Resource> List<String> doDeniedActions(S subject, R resource) {
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
                result.addAll(policyRepository.findDenyByRoleAndData(thisRole, thisData).stream().map(Policy::getAction).collect(Collectors.toList()));
            }
        }
        return result;
    }

    @Override
    public void addPolicy(Policy policy) {
        policyRepository.addPolicy(policy);
    }

    @Override
    public void addRoleHierarchy(Hierarchy hierarchy) throws IllegalArgumentException {
        roleHierarchyRepository.addHierarchy(hierarchy);
    }

    @Override
    public void addDataGroup(Group group) throws IllegalArgumentException {
        dataGroupRepository.addGroup(group);
    }

    @Override
    public void addActionGroup(Group group) throws IllegalArgumentException {
        actionGroupRepository.addGroup(group);
    }
}
