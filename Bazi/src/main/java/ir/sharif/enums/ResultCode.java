package ir.sharif.enums;

public enum ResultCode {

    NOT_FOUND(404),
    INVALID_COMMAND(100),
    ACCEPT(200),
    FAILED(300);

    private final int result;

    ResultCode(int code) {
        this.result = code;
    }

    public int getResultCode() {
        return this.result;
    }

}
