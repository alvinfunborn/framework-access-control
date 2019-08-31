package com.alvin.framework.access.control.result;

/**
 * datetime 2019/5/16 20:10
 * access enforce result
 *
 * @author sin5
 */
public class Result {

    /**
     * result code
     * @see ResultCode
     */
    private ResultCode code;
    /**
     * message
     */
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
        return this.code.equals(ResultCode.PERMIT);
    }

    public boolean isDeny() {
        return this.code.equals(ResultCode.DENY);
    }

    public static Result ofPermit(String msg) {
        return new Result(ResultCode.PERMIT, msg);
    }

    public static Result ofPermit() {
        return new Result(ResultCode.PERMIT, null);
    }

    public static Result ofDeny(String msg) {
        return new Result(ResultCode.DENY, msg);
    }

    public static Result ofDeny() {
        return new Result(ResultCode.DENY, null);
    }

    public static Result ofUncertain(String msg) {
        return new Result(ResultCode.UNCERTAIN, msg);
    }

    public static Result ofUncertain() {
        return new Result(ResultCode.UNCERTAIN, null);
    }

    public Result and(Result r) {
        if (isPermit() && !r.isPermit()) {
            return r;
        } else {
            return this;
        }
    }

    public void replaceMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
