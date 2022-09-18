package cc.hqido.spms.infra.exception;

public class BizException extends RuntimeException {

    private final String errorCode;

    public BizException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BizException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
