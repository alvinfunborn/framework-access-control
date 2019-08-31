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

    /**
     * name of this condition
     */
    private String name;

    public Condition(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Condition and(String name, Condition c1, Condition c2) {
        return new Condition(name) {
            @Override
            public <S extends Subject, R extends Resource> Result onCondition(S subject, R resource) {
                return c1.onCondition(subject, resource).and(c2.onCondition(subject, resource));
            }
        };
    }

    public static Condition or(String name, Condition c1, Condition c2) {
        return new Condition(name) {
            @Override
            public <S extends Subject, R extends Resource> Result onCondition(S subject, R resource) {
                return c1.onCondition(subject, resource).or(c2.onCondition(subject, resource));
            }
        };
    }

    public abstract <S extends Subject, R extends Resource> Result onCondition(S subject, R resource);

}
