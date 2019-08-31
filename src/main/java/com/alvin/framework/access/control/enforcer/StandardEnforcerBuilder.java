package com.alvin.framework.access.control.enforcer;

import com.alvin.framework.access.control.group.ActionGroupRepository;
import com.alvin.framework.access.control.group.DataGroupRepository;
import com.alvin.framework.access.control.group.DefaultActionGroupRepository;
import com.alvin.framework.access.control.group.DefaultDataGroupRepository;
import com.alvin.framework.access.control.hierarchy.*;
import com.alvin.framework.access.control.policy.DefaultPolicyRepository;
import com.alvin.framework.access.control.policy.PolicyRepository;

/**
 * datetime 2019/5/17 18:05
 *
 * @author sin5
 */
public class StandardEnforcerBuilder {

    private PolicyRepository policyRepository;
    private RoleHierarchyRepository roleHierarchyRepository;
    private DataGroupRepository dataHierarchyRepository;
    private ActionGroupRepository actionHierarchyRepository;

    public StandardEnforcerBuilder withRuleRepository(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
        return this;
    }

    public StandardEnforcerBuilder withRoleHierarchyRepository(RoleHierarchyRepository roleHierarchyRepository) {
        this.roleHierarchyRepository = roleHierarchyRepository;
        return this;
    }

    public StandardEnforcerBuilder withDataHierarchyRepository(DataGroupRepository dataHierarchyRepository) {
        this.dataHierarchyRepository = dataHierarchyRepository;
        return this;
    }

    public StandardEnforcerBuilder withActionHierarchyRepository(ActionGroupRepository actionHierarchyRepository) {
        this.actionHierarchyRepository = actionHierarchyRepository;
        return this;
    }

    public StandardEnforcer build() {
        if (policyRepository == null) {
            policyRepository = new DefaultPolicyRepository();
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
        return new StandardEnforcer(policyRepository, roleHierarchyRepository, dataHierarchyRepository, actionHierarchyRepository);
    }

}
