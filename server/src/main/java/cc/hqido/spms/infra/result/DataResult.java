package cc.hqido.spms.infra.result;

public class DataResult<T> extends Result {

    private final T data;

    public DataResult(String code, String message, T data) {
        super(code, message);
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
