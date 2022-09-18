package cc.hqido.spms.infra.result.std;

import cc.hqido.spms.infra.exception.BizException;
import cc.hqido.spms.infra.result.DataResult;
import cc.hqido.spms.infra.result.Result;
import cc.hqido.spms.infra.util.MessageUtils;
import org.apache.commons.lang3.ArrayUtils;

public class ResultBuilder {

    private final String code;

    private final String message;

    public ResultBuilder(String code, String patternMsg) {
        this.code = code;
        this.message = patternMsg;
    }

    public Result r() {
        return new Result(code, formatMsg());
    }

    public <T> DataResult<T> r(T data) {
        return new DataResult<>(code, formatMsg(), data);
    }

    public Result re(Object... params) {
        return new Result(code, formatMsg(params));
    }

    public BizException e(Object... params) {
        return e(null, params);
    }

    public BizException e(Throwable e, Object... params) {
        String msg = formatMsg();

        if (e == null) {
            return new BizException(msg, code);
        } else {
            return new BizException(msg, e, msg);
        }
    }

    private String formatMsg(Object... params) {
        if (ArrayUtils.isNotEmpty(params)) {
            return MessageUtils.formatWithDefault(this.message, "", params);
        }

        return this.message;
    }

}
