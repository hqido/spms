package cc.hqido.spms.core;

import cc.hqido.spms.infra.result.Result;
import cc.hqido.spms.infra.result.std.ResultBuilder;

public class ResultBuilderConsts {

    public static final ResultBuilder SUCCESS = new ResultBuilder(Result.SUCCESS, "success");

    public static final ResultBuilder USER_ALREADY_EXISTS = new ResultBuilder("USER_ALREADY_EXISTS", "user already exists");

    public static final ResultBuilder NULL = new ResultBuilder("NULL", "{} must not be null");

    public static final ResultBuilder AUTHENTICATE_FAIL = new ResultBuilder("AUTHENTICATE_FAIL", "authenticate fail");

    public static final ResultBuilder NOT_LOGIN = new ResultBuilder("NOT_LOGIN", "not login");

    public static final ResultBuilder INVALID_LOGIN = new ResultBuilder("INVALID_LOGIN", "invalid login");

    public static final ResultBuilder SYSTEM_ERROR = new ResultBuilder("SYSTEM_ERROR", "系统异常-{}");

}
