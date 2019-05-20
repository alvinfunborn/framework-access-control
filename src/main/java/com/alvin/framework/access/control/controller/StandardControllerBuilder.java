package com.alvin.framework.access.control.controller;

import com.alvin.framework.access.control.group.ActionGroupRepository;
import com.alvin.framework.access.control.group.DataGroupRepository;
import com.alvin.framework.access.control.group.DefaultActionGroupRepository;
import com.alvin.framework.access.control.group.DefaultDataGroupRepository;
import com.alvin.framework.access.control.hierarchy.*;
import com.alvin.framework.access.control.rule.DefaultRuleRepository;
import com.alvin.framework.access.control.rule.RuleRepository;

/**
 * datetime 2019/5/17 18:05
 *
 * @author sin5
 */
public class StandardControllerBuilder {

    private RuleRepository ruleRepository;
    private RoleHierarchyRepository roleHierarchyRepository;
    private DataGroupRepository dataHierarchyRepository;
    private ActionGroupRepository actionHierarchyRepository;

    public StandardControllerBuilder withRuleRepository(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
        return this;
    }

    public StandardControllerBuilder withRoleHierarchyRepository(RoleHierarchyRepository roleHierarchyRepository) {
        this.roleHierarchyRepository = roleHierarchyRepository;
        return this;
    }

    public StandardControllerBuilder withDataHierarchyRepository(DataGroupRepository dataHierarchyRepository) {
        this.dataHierarchyRepository = dataHierarchyRepository;
        return this;
    }

    public StandardControllerBuilder withActionHierarchyRepository(ActionGroupRepository actionHierarchyRepository) {
        this.actionHierarchyRepository = actionHierarchyRepository;
        return this;
    }

    public StandardController build() {
        if (ruleRepository == null) {
            ruleRepository = new DefaultRuleRepository();
        }
        if (roleHierarchyRepository == null) {
            roleHierarchyRepository = new DefaultRoleHierarchyRepository();
        }
        if (dataHierarchyRepository == null) {
            dataHierarchyRepository = new DefaultDataGroupRepository();
        }
        if (actionHierarchyRepository == null) {
            actionHierarchyRepository = new DefaultActionGroupRepository();
        }
        return new StandardController(ruleRepository, roleHierarchyRepository, dataHierarchyRepository, actionHierarchyRepository);
    }

}
