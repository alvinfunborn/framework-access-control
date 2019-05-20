package com.alvin.framework.access.control.result;

/**
 * datetime 2019/5/16 20:10
 *
 * @author sin5
 */
public class Result {

    private ResultCode code;
    private String msg;

    private Result(ResultCode code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultCode getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isPermit() {
        return this.code.equals(ResultCode.permit);
    }

    public boolean isDeny() {
        return this.code.equals(ResultCode.deny);
    }

    public boolean isUncertain() {
        return this.code.equals(ResultCode.uncertain);
    }

    public boolean isImproper() {
        return this.code.equals(ResultCode.improper);
    }

    public static Result ofPermit(String msg) {
        return new Result(ResultCode.permit, msg);
    }

    public static Result ofPermit() {
        return new Result(ResultCode.permit, null);
    }

    public static Result ofDeny(String msg) {
        return new Result(ResultCode.deny, msg);
    }

    public static Result ofDeny() {
        return new Result(ResultCode.deny, null);
    }
    public static Result ofUncertain(String msg) {
        return new Result(ResultCode.uncertain, msg);
    }

    public static Result ofUncertain() {
        return new Result(ResultCode.uncertain, null);
    }

    public static Result ofImproper(String msg) {
        return new Result(ResultCode.improper, msg);
    }

    public static Result ofImproper() {
        return new Result(ResultCode.improper, null);
    }

    public Result and(Result r) {
        if (isPermit() && !r.isPermit()) {
            return r;
        } else {
            return this;
        }
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
