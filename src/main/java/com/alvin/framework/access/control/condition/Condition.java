package com.alvin.framework.access.control.condition;

import com.alvin.framework.access.control.model.Resource;
import com.alvin.framework.access.control.model.Subject;
import com.alvin.framework.access.control.result.Result;
import com.alvin.framework.access.control.result.ResultCode;

import java.util.Map;

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
        public <S extends Subject, R extends Resource> Result doOnCondition(S subject, R resource) {
            return Result.ofPermit();
        }
    };

    /**
     * name of this condition
     */
    private String name;

    /**
     * result msg of each ResultCode
     */
    private Map<ResultCode, String> resultMsgMap;

    public Condition(String name) {
        this.name = name;
    }

    public Condition(String name, Map<ResultCode, String> resultMsgMap) {
        this.name = name;
        this.resultMsgMap = resultMsgMap;
    }

    public String getName() {
        return name;
    }

    protected abstract <S extends Subject, R extends Resource> Result doOnCondition(S subject, R resource);

    public <S extends Subject, R extends Resource> Result onCondition(S subject, R resource) {
        Result result = doOnCondition(subject, resource);
        fillMsg(result);
        return result;
    }

    private void fillMsg(Result result) {
        if (result != null && (result.getMsg() == null || "".equals(result.getMsg().trim()))) {
            result.replaceMsg(resultMsgMap == null ? result.getMsg() : resultMsgMap.getOrDefault(result.getCode(), result.getMsg()));
        }
    }
}
