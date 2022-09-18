package cc.hqido.spms.infra.result;

public class Result {

    public static final String SUCCESS = "SUCCESS";

    private final String code;

    private final String message;

    public Result(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
