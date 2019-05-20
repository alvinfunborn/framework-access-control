package com.alvin.framework.access.control.condition;

import com.alvin.framework.access.control.model.Resource;
import com.alvin.framework.access.control.model.Subject;
import com.alvin.framework.access.control.result.Result;

/**
 * datetime 2019/5/17 10:21
 *
 * @author sin5
 */
public abstract class Condition {

    /**
     * always on condition
     */
    public static final Condition ALWAYS_ON_CONDITION = new Condition("ALWAYS_ON_CONDITION") {
        @Override
        public <S extends Subject, R extends Resource> Result onCondition(S subject, R resource) {
            return Result.ofPermit();
        }
    };

    private String name;

    public Condition(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract <S extends Subject, R extends Resource> Result onCondition(S subject, R resource);
}
