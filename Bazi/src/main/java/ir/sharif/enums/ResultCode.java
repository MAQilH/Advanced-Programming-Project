package ir.sharif.enums;

public enum ResultCode {

    NotFound(404),
    InvalidCommand(100);

    private final int result;

    ResultCode(int code) {
        this.result = code;
    }

    public int getResultCode() {
        return this.result;
    }

}
