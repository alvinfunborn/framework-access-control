package com.alvin.framework.access.control.enforcer;

import com.alvin.framework.access.control.condition.Condition;
import com.alvin.framework.access.control.model.Resource;
import com.alvin.framework.access.control.model.Subject;
import com.alvin.framework.access.control.result.Result;
import com.alvin.framework.access.control.result.ResultCode;
import com.alvin.framework.access.control.policy.Policy;

import java.util.Collections;

class StandardEnforcerTest {

    @org.junit.jupiter.api.Test
    void enforce() {
        StandardEnforcer enforcer = new StandardEnforcerBuilder().build();
        enforcer.addPolicy(Policy.ofPermit("root may create admin", "root", "mac", "add_admin",
                new Condition("root owns mac", Collections.singletonMap(ResultCode.DENY, "permission denied")) {
                    @Override
                    protected <S extends Subject, R extends Resource> Result doOnCondition(S subject, R resource) {
                        return Result.ofDeny();
                    }
                }));

        Result result = enforcer.enforce(new Subject("root"), new Resource("mac"), "add_admin");
        System.out.println(result.getMsg());
    }
}